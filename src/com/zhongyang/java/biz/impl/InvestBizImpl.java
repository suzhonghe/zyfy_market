package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.biz.InvestBiz;
import com.zhongyang.java.pojo.ExperienceAmount;
import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.LoanOrder;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.service.ExperienceAmountService;
import com.zhongyang.java.service.FreshAmountService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.LoanOrderService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UmpTenderTransferRecordService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.service.VirtualLoanService;
import com.zhongyang.java.service.VirtualRecordService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.log.LogInterface;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.AppInvestPage;
import com.zhongyang.java.vo.InvestDetail;
import com.zhongyang.java.vo.InvestVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UmpInvestVo;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.loan.InvestStatus;
import com.zhongyang.java.vo.loan.LoanStatus;

/**
 * 
* @Title: InvestBiz.java 
* @Package InvestBiz 
* @Description: 投资业务处理 
* @author 苏忠贺   
* @date 2015年12月9日 下午3:23:07 
* @version V1.0
 */
@Service
public class InvestBizImpl implements InvestBiz {
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
	    zycfurlIp = (String) sysMap.get("ZYCFMARKET_IP");
	}
	
	private static String zycfurlIp;
	
	private static Logger logger=Logger.getLogger(InvestBizImpl.class);

	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private ExperienceAmountService experienceAmountService;
	
	@Autowired
	private UmpAccountService umpAccountService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private InvestService investService;
	
	@Autowired
	private UmpTenderTransferRecordService uttrService;
	
	@Autowired
	private UmpTenderService umpTenderService;
	
	@Autowired
	private LoanOrderService loanOrderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VirtualLoanService virtualLoanService;
	
	@Autowired
	private VirtualRecordService virtualRecordService;
	
	@Autowired
	private FreshAmountService freshAmountService;
	
	@LogInterface(description="用户投资记录信息，主要涉及用户资金、标的信息、联动优势标的账户的更新，联动个人账户的划账")
	@Transactional
	public  String invest(InvestVo investVo,Integer investAmount,String sourceV,String freshAmountId) {
		try{
			logger.info("投资业务");
			if(investVo==null||investVo.getUser()==null||investVo.getLoan()==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"数据接收出现问题，请重试！");
			}
			
			//创建联动信息对象
			UmpAccount ump=new UmpAccount();
			ump.setUserId(investVo.getUser().getId());
			
			//根据userId查询umpAccount TB_UMP_ACCOUNT
			UmpAccount umpAccount = umpAccountService.byUserIdUmpAccount(ump);
			if(umpAccount==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"用户未开通联动账户");
			}
			logger.info("投资标的ID："+investVo.getLoan().getId());
			//获得所投标的信息
			Loan loan = loanService.queryLoanById(investVo.getLoan().getId());
			if(loan==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"所投标的信息获取失败");
			}
			
			UserFund fund = new UserFund();
			fund.setUserId(investVo.getUser().getId());
			logger.info("投资人用户ID:"+investVo.getUser().getId());
			if(investVo.getUser().getId().equals(loan.getLoanUserId())){
				logger.info("借款人不可以投资");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"借款人不可以投资");
			}
			if(investAmount==0||investAmount%loan.getStepAmount().intValue()!=0){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"投资金额必须是"+loan.getStepAmount()+"的整数倍并且不可以为0");
			}
			if(investAmount>loan.getMaxAmount().intValue()){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"投资金额超出最大投资额("+loan.getMaxAmount()+"元)");
			}
			if(investAmount<loan.getMinAmount().intValue()){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"投资金额小于最小投资额("+loan.getMinAmount()+"元)");
			}
			if(!loan.getStatus().equals(LoanStatus.OPENED)){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"标的已是非开标状态，不允许继续投资");
			}
			//获得用户资金信息
			UserFund userFund = userFundService.byUserID(fund);
			if(userFund==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"用户账户信息获取失败");
			}
			
			//获得可用余额
			BigDecimal availableAmount = userFund.getAvailableAmount();
			logger.info("投资人账户余额："+availableAmount);
			logger.info("投资金额："+investAmount);
			logger.info("标的剩余可投金额："+BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()));
			//判断投资金额是否超过标的剩余可投金额
			if(investAmount>BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()).doubleValue()){
				logger.info("投资金额超出标的剩余可投金额");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"投资金额超出剩余可投金额");
			}
			//判断账户余额是否充足
			if((availableAmount.doubleValue()-investAmount)<0){	
				logger.info("账户余额不足（投资金额超出账户余额）");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"账户余额不足，请先进行充值");
			}
			
			//添加投资信息TB_INVEST
			Invest invest=new Invest();
			invest.setId(UUID.randomUUID().toString().toUpperCase());
			invest.setOrderId(ApplicationBean.order());
			invest.setAmount(new BigDecimal(investAmount));//投资额
			invest.setBidmethod("MANUAL");//投资方式
			invest.setLoanid(loan.getId());//标的Id
			invest.setOriginalamount(new BigDecimal(investAmount));//原始投资金额
			invest.setPurpose("个人投资");//用途
			invest.setRate(loan.getRate().add(new BigDecimal(loan.getAddRate())));//利率
			invest.setRepaymethod(loan.getMethod());//还款方式
			invest.setStatus(InvestStatus.INITIALIZED);//处理中
			invest.setSubmittime(new Date());//提交时间
			invest.setUserid(investVo.getUser().getId());
			invest.setMonths(loan.getMonths());
			invest.setFreshAmountId(freshAmountId);
			investService.addInvest(invest);
			logger.info("平台添加投资记录，记录ID："+invest.getId());
			
			
			//构造订单
			LoanOrder loanOrder=new LoanOrder();
			
			loanOrder.setId(UUID.randomUUID().toString().toUpperCase());
			//投资记录ID
			loanOrder.setInvestId(invest.getId());
			//订单生成日期
			loanOrder.setOrderDate(new Date());
			//生成订单号
			loanOrder.setOrderId(invest.getOrderId());
			//记录时间
			loanOrder.setTimeRecorded(new Date());
			//订单金额
			loanOrder.setAmount(new BigDecimal(investAmount));
			//订单状态
			loanOrder.setStatus(FundRecordStatus.PROCESSING);//处理中
			//标的Id
			loanOrder.setLoanId(loan.getId());
			//添加记录 TB_LOAN_ORDER
			loanOrderService.addloanOrder(loanOrder);
			logger.info("平台添加标的订单记录,订单号："+loanOrder.getOrderId());
			logger.info("订单状态：处理中");
			
			//添加标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
			UmpTenderTransferRecord uttRecord=new UmpTenderTransferRecord();
			uttRecord.setOrderid(loanOrder.getOrderId());//订单号
			uttRecord.setAmount(loanOrder.getAmount());//交易金额
			uttRecord.setLoanid(loan.getId());//借款ID
			uttRecord.setStatus(FundRecordStatus.PROCESSING);//状态
			uttRecord.setTenderaction(FundRecordOperation.IN);//标的操作
			uttRecord.setTimecreated(new Date());//创建日期
			uttRecord.setTimelastupdated(new Date());//最后修改日期
			uttRecord.setTransfertype(FundRecordType.INVEST);//交易类型
			uttRecord.setUmpaccountid(umpAccount.getAccountId());//联动帐号
			uttRecord.setUmpaccountname(umpAccount.getAccountName());//联动帐户名称
			UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(loan.getId());
			UmpTender queryUmpTender = umpTender;
			if(queryUmpTender==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"标的账户信息获取失败");
			}
			uttRecord.setUmptenderaccountid(queryUmpTender.getUmptenderaccountid());//联动标的帐号
			uttRecord.setUserid(investVo.getUser().getId());//用户ID
			uttrService.addUmpTenderTransferRecord(uttRecord);
			logger.info("平台添加标的交易记录，订单号："+uttRecord.getOrderid());
			
			//做出账资金流水记录 TB_FUND_RECORD
			FundRecord fundRecord=new FundRecord();
			fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
			fundRecord.setAmount(loanOrder.getAmount());//投资金额
			fundRecord.setDescription("投资处理中");
			fundRecord.setOperation(FundRecordOperation.OUT);//转出
			fundRecord.setOrderid(loanOrder.getOrderId());//订单号
			fundRecord.setRecordpriv("投资"+loan.getTitle()+"资金处理");//扩展信息
			fundRecord.setStatus(FundRecordStatus.PROCESSING);//处理中
			fundRecord.setTimerecorded(new Date());//创建时间
			fundRecord.setType(FundRecordType.INVEST);//资金记录类型
			fundRecord.setUserId(investVo.getUser().getId());//用户ID
			fundRecord.setAviAmount(userFund.getAvailableAmount());
			fundRecordService.create(fundRecord);
			logger.info("平台做账户资金出账初始化记录，记录ID："+fundRecord.getId());
			
			//准备联动需要的请求参数
			Integer transferAmount = investAmount*100;
			String amount = transferAmount.toString();
			Map<String, String> map=new HashMap<String, String>();
			//协议参数
			
			map.put("notify_url", zycfurlIp+"/bidTransactionSettle");
			if(sourceV!=null&&"HTML5".equals(sourceV)){
				logger.info("操作来源：移动端");
				map.put("sourceV",sourceV);
				map.put("ret_url", "app:invest");
			}
			else if(sourceV!=null&&"wap".equals(sourceV)){
				logger.info("操作来源：微信");
				map.put("sourceV","HTML5");
				map.put("ret_url", "http://www.zuoyoufy.com/zycfMarket/wap/html/InvestSuccess.html");
			}
			else{
				map.put("ret_url","https://www.zuoyoufy.com/zycfMarket/html/account/investmentSuccess.html");
			}
			logger.info("操作来源：PC端");
			//业务参数
			map.put("order_id", loanOrder.getOrderId());//商户订单号
			SimpleDateFormat sdfs=new SimpleDateFormat("yyyyMMdd");
			String mer_date = sdfs.format(loanOrder.getOrderDate());
			map.put("mer_date", mer_date);//商户生成订单的日期
			if(umpTender!=null&&umpTender.getUmptenderid()!=null&&!"".equals(umpTender.getUmptenderid())){
				map.put("project_id", umpTender.getUmptenderid());//标的号
			}
			else{
				map.put("project_id", loan.getId().replace("-",""));//标的号
			}
			map.put("serv_type", "01");//转入 01投标
			map.put("trans_action", "01");//01 标的转入
			map.put("partic_type", "01");//取值范围：01投资者
			map.put("partic_acc_type", "01");////01 个人
			map.put("partic_user_id", umpAccount.getAccountName());//联动开立的用户号
			map.put("amount", amount);//金额 单位：分
			logger.info("构造标的交易联动需要的参数，参数内容："+map.toString());
			//发送到联动优势
			logger.info("发送请求到联动优势，订单号："+loanOrder.getOrderId());
			UmpaySignature umpaySignature = new UmpaySignature("project_transfer",map);
			//获得联动响应
			String signatureStrng = umpaySignature.getSignatureStrng();
			logger.info("响应内容："+signatureStrng);
			return signatureStrng;
			
		}
		catch(Exception e){
			e.printStackTrace();
			logger.info("投资操作出现异常，异常信息："+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
	}

	public String bidTransactionSettleBiz(UmpInvestVo umpInvestVo) {
		try {
			logger.info("投资回调");
			logger.info("投资回调内容："+umpInvestVo.toString());
			Map<String, String> map=new HashMap<String, String>();
			String order_id=umpInvestVo.getOrder_id();//订单号
			String ret_code = umpInvestVo.getRet_code();//返回码
			String mer_check_date = umpInvestVo.getMer_check_date();//资金账户托管平台对账日期
			String trade_no = umpInvestVo.getTrade_no();//交易流水号
			String ret_msg = umpInvestVo.getRet_msg();//返回信息
			String mer_date=umpInvestVo.getMer_date();//订单日期

			//准备回调参数
			map.put("order_id", order_id);
			map.put("mer_date", mer_date);
			map.put("ret_code",ret_code);

			
			//响应联动优势
			logger.info("给联动优势做回调响应，响应内容："+map.toString());
			UmpaySignature umpaySignature = new UmpaySignature(null,map);
						
			//根据订单号查询订单信息
			LoanOrder loanOrder = loanOrderService.queryLoanOrderByOrderId(order_id);
			
			if(loanOrder!=null){
				if(FundRecordStatus.SUCCESSFUL.equals(loanOrder.getStatus())||FundRecordStatus.FAILED.equals(loanOrder.getStatus())){
					return umpaySignature.callBackSignature();
				}
			}
			
			if(!"0000".equals(ret_code)){
				logger.info("投资已做处理");
				
				LoanOrder order=new LoanOrder();
				order.setOrderId(order_id);
				order.setStatus(FundRecordStatus.FAILED);//订单失败
				loanOrderService.modifyLoanOrder(order);
				
				//修改投资信息TB_INVEST
				Invest invest=new Invest();
				invest.setOrderId(order_id);
				invest.setStatus(InvestStatus.FAILED);//投资失败
				investService.modifyInvest(invest);
				logger.info("投资人投资状态：投资失败");
				
				//修改标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
				UmpTenderTransferRecord uttRecord=new UmpTenderTransferRecord();
				uttRecord.setOrderid(loanOrder.getOrderId());//订单号
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				uttRecord.setMercheckdate(sdf.parse(mer_check_date));//对帐日期
				uttRecord.setMerdate(loanOrder.getOrderDate());//交易日期
				uttRecord.setRetcode(ret_code);//返回码
				uttRecord.setRetmsg(ret_msg);//返回信息
				uttRecord.setStatus(FundRecordStatus.FAILED);//状态
				uttRecord.setTimelastupdated(new Date());//最后修改日期
				uttRecord.setTradeno(trade_no);//交易流水号
				uttrService.modifyUmpTenderTransferRecord(uttRecord);
				logger.info("修改标的交易记录，标的交易失败");
				
				//修改资金流水记录 TB_FUND_RECORD
				FundRecord fundRecord=new FundRecord();
				fundRecord.setDescription("投资失败");
				fundRecord.setOrderid(loanOrder.getOrderId());//订单号
				fundRecord.setRecordpriv("资金完成处理");//扩展信息
				fundRecord.setStatus(FundRecordStatus.FAILED);//失败
				fundRecordService.modifyFundRecord(fundRecord);
				logger.info("修改平台账户资金出账记录，记录orderId："+loanOrder.getOrderId());
								
				return umpaySignature.callBackSignature();
			}
			
			//修改标的信息

			UmpTender tender=new UmpTender();
			tender.setLoanid(loanOrder.getLoanId());
			UmpTender queryTender = umpTenderService.queryUmpTenderByLoanId(loanOrder.getLoanId());
			tender.setAmount(BigDecimalAlgorithm.add(queryTender.getAmount(), loanOrder.getAmount()));
			umpTenderService.modifyUmpTender(tender);
				
			Loan loan = loanService.queryLoanById(loanOrder.getLoanId());
			Loan modifyLoan=new Loan();
			modifyLoan.setId(loan.getId());
			modifyLoan.setBidAmount(loan.getBidAmount().add(loanOrder.getAmount()));//修改实际投资金额
			modifyLoan.setBidNumber(loan.getBidNumber()+1);//修改投资人数
			Date date=new Date();
			if(loan.getAmount().equals(modifyLoan.getBidAmount())){
				modifyLoan.setStatus(LoanStatus.FINISHED);
				Timestamp timeStamp = new Timestamp(date.getTime());
				modifyLoan.setTimeFinished(timeStamp);
			}
			loanService.modifyLoan(modifyLoan);
			logger.info("修改标的信息投标信息");
			
			//修改投资订单信息
			LoanOrder order=new LoanOrder();
			order.setOrderId(order_id);
			order.setStatus(FundRecordStatus.SUCCESSFUL);//订单成功
			loanOrderService.modifyLoanOrder(order);
			
			//修改投资信息TB_INVEST
			Invest invest=new Invest();
			invest.setOrderId(order_id);
			invest.setStatus(InvestStatus.AUDITING);//审核中
			investService.modifyInvest(invest);
			logger.info("投资人投资状态：审核中");
			
			//修改标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
			UmpTenderTransferRecord uttRecord=new UmpTenderTransferRecord();
			uttRecord.setOrderid(loanOrder.getOrderId());//订单号
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			uttRecord.setMercheckdate(sdf.parse(mer_check_date));//对帐日期
			uttRecord.setMerdate(loanOrder.getOrderDate());//交易日期
			uttRecord.setRetcode(ret_code);//返回码
			uttRecord.setRetmsg(ret_msg);//返回信息
			uttRecord.setStatus(FundRecordStatus.SUCCESSFUL);//状态
			uttRecord.setTimelastupdated(new Date());//最后修改日期
			uttRecord.setTradeno(trade_no);//交易流水号
			uttrService.modifyUmpTenderTransferRecord(uttRecord);
			logger.info("修改标的交易记录，标的交易成功");
			
			//冻结投资用户资金TB_USER_FUND
			Invest qi=new Invest();
			qi.setId(loanOrder.getInvestId());
			Invest queryInvest = investService.queryById(qi);
			UserFund uf=new UserFund();
			uf.setUserId(queryInvest.getUserid());
			UserFund userFund = userFundService.byUserID(uf);
			uf.setAvailableAmount((BigDecimalAlgorithm.sub(userFund.getAvailableAmount(), loanOrder.getAmount())));//修改可用余额
			uf.setFrozenAmount((BigDecimalAlgorithm.add(userFund.getFrozenAmount(),loanOrder.getAmount())));//修改冻结资金
			uf.setTimeLastUpdated(new Date());//最后提交日期
			userFundService.modifyUserFund(uf);
			logger.info("冻结投资资金");
			
//			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//					.getRequest();
//			request.getSession().setAttribute("invest", queryInvest);
//			request.getSession().setAttribute("fresh", queryInvest);
			
			//修改资金流水记录 TB_FUND_RECORD
			FundRecord fundRecord=new FundRecord();
			fundRecord.setAviAmount(uf.getAvailableAmount());
			fundRecord.setDescription("投资成功");
			fundRecord.setOrderid(loanOrder.getOrderId());//订单号
			fundRecord.setRecordpriv("资金完成处理");//扩展信息
			fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);//成功
			fundRecordService.modifyFundRecord(fundRecord);
			logger.info("修改平台做账户资金出账记录，记录orderId："+loanOrder.getOrderId());

			return umpaySignature.callBackSignature();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("投资操作出现异常，异常信息："+e.getMessage());
			logger.info(e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
	}
	
	
	
	@Override
	public PagerVO<InvestDetail> investRecords(PagerVO<InvestDetail> investVo) {
		try {
			PagerVO<InvestDetail> pagerVo=new PagerVO<InvestDetail>();
			List<InvestDetail> investDetails=new ArrayList<InvestDetail>();
			Page<InvestDetail> page=new Page<InvestDetail>();
			if(investVo.getStart()!=0){
				page.setPageNo(investVo.getStart());
			}
			else{
				page.setPageNo(1);
			}
			if(investVo.getLength()!=0){
				page.setPageSize(investVo.getLength());
			}
			else{
				page.setPageSize(5);
			}
			if(investVo.getField()!=null&&investVo.getValue()!=null){
				String[] fileds = investVo.getField().split(",");
				String[] values = investVo.getValue().split(",");
				if("".equals(investVo.getValue())){
					page.getParams().put("submittime","submittime");
				}
				else{
					if(!investVo.getField().contains("timeOpen")){
						page.getParams().put("submittime","submittime");
					}
				}
				for (int i = 0; i < fileds.length; i++) {
					page.getParams().put(fileds[i],values[i]);
				}
			}
			else{
				page.getParams().put("submittime","SUBMITTIME");
			}
			if(investVo.getSort()!=null){
				page.getParams().put("sort",investVo.getSort());
			}
			else{
				page.getParams().put("sort","desc");
			}
			List<Invest> invests = investService.queryInvestByLoanId(page);
			pagerVo.setRecordsTotal(invests.size());//总数
			int totalPage;
			if(invests.size()%page.getPageSize()==0){
				totalPage=invests.size()/page.getPageSize();
			}
			else{
				totalPage=(invests.size()/page.getPageSize())+1;
			}
			pagerVo.setTotalPage(totalPage);//页数
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			for (Invest invest : invests) {
				InvestDetail ids=new InvestDetail();
				User user = userService.queryById(invest.getUserid());
				String mobile = cipher.decrypt(user.getMobile());
				String userName=mobile.substring(0, mobile.length() - (mobile.substring(3)).length()) + "****"+ mobile.substring(7);
				ids.setUserName(userName);
				ids.setAmount(invest.getAmount());
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date submittime = invest.getSubmittime();
				ids.setTime(sdf.format(submittime));
				ids.setType(invest.getBidmethod());
				investDetails.add(ids);
			}
			pagerVo.setData(investDetails);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询投资记录出现异常，异常信息："+e.getMessage());
			logger.info(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"查看日志信息");
		}
	}
	@Override
	public AppInvestPage queryAppInvestPageData(HttpServletRequest request,String loanId) {
		AppInvestPage data=new AppInvestPage();
		try {
			User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"用户未登录");
			}
			UserFund userFund=new UserFund();
			userFund.setUserId(user.getId());
			UserFund fund = userFundService.byUserID(userFund);
			if(fund==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未获得账户信息");
			}
			data.setAvailableAmount(fund.getAvailableAmount());
			Loan loan = loanService.queryLoanById(loanId);
			if(loan==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未获标的信息");
			}
			
			FreshAmount freshAmount=new FreshAmount();
			freshAmount.setMonths(loan.getMonths());
			freshAmount.setStatus(0);
			freshAmount.setType(1);
			freshAmount.setUserId(user.getId());
			List<FreshAmount> results = freshAmountService.queryFreshAmountByParams(freshAmount);
			
			data.setLoanId(loanId);
			data.setLoanName(loan.getTitle());
			data.setMethod(loan.getMethod());
			data.setMinAmount(loan.getMinAmount());
			data.setStepAmount(loan.getStepAmount());
			data.setProductName(loan.getProductName());
			data.setSuplusAmount(BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()));
			data.setMonths(loan.getMonths());
			data.setRate(loan.getRate());
			data.setAddRate(loan.getAddRate());
			data.setMaxAmount(loan.getMaxAmount());
			data.setFreshAmounts(results);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("app查询投资记录出现异常，异常信息："+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map queryByOrderId(String orderId) {
		Invest invest=new Invest();
		invest.setOrderId(orderId);
		Map map=null;
		try {
			map = investService.queryByOrderId(invest);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询记录出现异常，异常信息："+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
		return map;
	}
	@Override
	public List<Map<String,Object>> queryByTime() {
		Date endTime=new Date();
		Date startTime=new Date(endTime.getTime()-2*24*3600*1000);
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("endTime", endTime);
		map.put("startTime", startTime);
		List<Map<String,Object>> returnMaps=new ArrayList<Map<String,Object>>();
		try {
			List<Map<String,Object>> queryMap = investService.queryByParams(map);
			for (Map<String,Object> result : queryMap) {
				Map<String,Object> res=new HashMap<String,Object>();
				String name = (String)result.get("name");
				name=name.substring(0, 1)+"**";
				res.put("name", name);
				res.put("amount", result.get("amount"));
				Date submitTime=(Date)result.get("submitTime");
				long hour=(new Date().getTime()-submitTime.getTime())/(1000*3600);
				res.put("hour", hour);
				returnMaps.add(res);
			}
			return returnMaps;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询投资记录出现异常，异常信息："+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
	}

	//体验金投资
	@Override
	@Transactional
	public Message investVirtualLoan() {
		Message message=new Message();
		try{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			User user=(User)request.getSession().getAttribute("zycfLoginUser");
			if(user==null){
				message.setCode(0);
				message.setMessage("用户未登录");
				return message;
			}
			if(user.getIdnumber()==null){
				message.setCode(2);
				message.setMessage("用户未实名认证，不允许投资");
				return message;
			}
			
			ExperienceAmount condition=new ExperienceAmount();
			condition.setUserId(user.getId());
			condition.setEnable(false);
			condition.setEnableUse(false);
			ExperienceAmount result = experienceAmountService.queryByParams(condition);
			if(result==null){
				message.setCode(3);
				message.setMessage("没有可用的体验金");
				return message;
			}
			
			VirtualRecord record=new VirtualRecord();
			record.setUserId(user.getId());
			VirtualRecord queryRecord = virtualRecordService.queryVirtualRecordByParams(record);
			if(queryRecord!=null){
				message.setCode(4);
				message.setMessage("您已做过体验投资，请勿重复体验");
				return message;
			}
			
			Date date = new Date();
			//使用体验金
			result.setEnableUse(true);
			result.setEnable(null);
			result.setUseTime(date);
			experienceAmountService.modifyExperienceAmount(result);	
			
			VirtualLoan vLoan=new VirtualLoan();
			vLoan.setStatus(1);
			VirtualLoan queryResult = virtualLoanService.queryVirtualLoanByParams(vLoan);
			
			if(queryResult==null){
				message.setCode(5);
				message.setMessage("没有可投资的体验标");
				return message;
			}
			
			//创建体验投资记录
			VirtualLoan loan=new VirtualLoan();
			loan.setId(queryResult.getId());
			loan.setStatus(null);
			VirtualLoan virtualLoan = virtualLoanService.queryVirtualLoanByParams(loan);
			VirtualRecord vr=new VirtualRecord();
			vr.setId(UUID.randomUUID().toString().toUpperCase());
			vr.setAmount(result.getAmount());
			BigDecimal multiply = result.getAmount().multiply(new BigDecimal(virtualLoan.getRate())).multiply(new BigDecimal(virtualLoan.getLoanDay()));
			BigDecimal res = multiply.divide(new BigDecimal(36500),2, BigDecimal.ROUND_HALF_UP);
			vr.setRealEarning(res);
			vr.setCreateTime(date);
			vr.setLoanDay(virtualLoan.getLoanDay());
			vr.setRepayTime(new Date(date.getTime()+virtualLoan.getLoanDay()*24*3600*1000L));
			vr.setVloanId(queryResult.getId());
			vr.setStatus("SUCCESSFUL");
			vr.setFlag(false);
			vr.setUserId(user.getId());
			int count = virtualRecordService.addVirtualRecord(vr);
			if(count>0){
				message.setCode(1);
				message.setMessage("投资成功");
				return message;
			}else{
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"添加体验投资记录异常");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
			message.setCode(6);
			message.setMessage("未知错误");
			return message;
		}
	}
}
