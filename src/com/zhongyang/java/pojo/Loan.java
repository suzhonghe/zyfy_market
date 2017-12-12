package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.vo.loan.LoanStatus;
import com.zhongyang.java.vo.loan.Method;

public class Loan {
		
	private String id;//ID varchar(36) not null
	
	private Date timeFailed;//流标日期
	
	private String serial;//   SERIAL   varchar(255) comment '唯一编号'
	
	private BigDecimal amount;//   AMOUNT   int(11) comment '贷款金额'
	
	private Integer autoSplitted;//  AUTOSPLITTED  tinyint(1) default 0 comment '是否支持自动拆标'
	
	private BigDecimal bidAmount;//   BID_AMOUNT   int(11) default NULL comment '实际投标金额'
	
	private Integer bidNumber;//   BID_NUMBER   int(11) default NULL comment '实际投标数目'
	
	private Method method;//   METHOD    varchar(255) comment '还款方式'
	
	private Integer mortgaged;//   MORTGAGED    tinyint(1) default 0 comment '是否有抵押'
	
	private Long ordinal;//   ORDINAL  int(11) comment '序号'
	
	private BigDecimal rate;//   RATE   int(11) comment '借款费率'
	
	private double addRate;
	
	private Integer rewarded;//   REWARDED   tinyint(1) default 0 comment '是否已经奖励'
	
	private LoanStatus status;//   STATUS    varchar(255) comment '借款状态'
	
	private Date timeCleared;//   TIMECLEARED    datetime default NULL comment '还清时间'
	
	private Date timeFinished;//   TIMEFINISHED   datetime default NULL comment '募集成功结束时间'
	
	private Date timeOpen;//   TIMEOPEN  datetime default NULL comment '开始募集时间'
	
	private int timeOut;//   TIMEOUT   int(11) default NULL comment '开放募集时间'
	
	private Date timeSettled;//TIMESETTLED    datetime default NULL comment '结标时间'
	
	private String title;//   TITLE    varchar(255) default NULL comment '借款标标题'
	
	private Integer days;//   DAYS   int(11) default NULL comment '借款期限(日)'
	
	private Integer months;//   MONTHS  int(11) default NULL comment '借款期限(月)'
	
	private Integer years;//   YEARS   int(11) default NULL comment '借款期限(年)'
	
	private Integer whetherTransfer;//   WHETHERTRANSFER  tinyint(1) default 0 comment '是否可转，状态码：0不可转，1 可转 默认值为0'
	
	private String loanUserId;//   LOANUSERID   varchar(255) default NULL comment '借款人用户ID'
	
	private String guaranteeId;//   GUARANTEE_ID   varchar(255) default NULL comment '关联担保企业ID'
	
	private String guaranteeRealm;//   GUARANTEE_REALM   varchar(255) default NULL comment '担保实体'
	
	private String productId;//   PRODUCTID   varchar(255) default NULL comment '关联产品ID'
	
	private String productName;//   PRODUCTNAME   varchar(255) default NULL comment '产品名称'
	
	private Integer groupId;//   GROUPID    tinyint(1) default 1 comment '投资人群 0 新用户 1全部用户'
	
	private String mortgagedType;//   MORTGAGEDTYPE  varchar(255) default NULL comment '抵押类型'
	
	private BigDecimal minAmount;//   MINAMOUNT   int(11) default 100 comment '起投金额'
	
	private BigDecimal stepAmount;//   STEPAMOUNT   int(11) default 0 comment '投资增量'
	
	private BigDecimal maxAmount;//   MAXAMOUNT   int(11) comment '最大投资金额'
	
	private BigDecimal loanServiceFee;//   LOANSERVICEFEE   decimal(10.5) default 0 comment '服务费率'
	
	private BigDecimal loanGuaranteeFee;//   LOANGUARANTEEFEE   decimal(10.5) default 0 comment '担保费率'
	
	private BigDecimal loanRiskFee;//   LOANRISKFEE   decimal(10.5) default 0 comment '风险管理费率'
	
	private BigDecimal LoanManageFee;//   LOANMANAGEFE  decimal(10.5) default 0 comment '借款管理费率'
	
	private BigDecimal loanInterestFee;//   LOANINTERESTFEE   decimal(10.5) default 0 comment '还款（借款）利息管理费率'
	
	private BigDecimal investInterestFee;//   INVESTINTERESTFEE   decimal(10.5) default 0 comment '回款（投资）利息管理费率'
	
	private int loanTime;//   LOANTIME in(11) default '10' comment '标的有效期（天）'
	
	private String projectId;//关联项目ID
	
	private String other;
	
	private Date timeScheduled;
					
	public double getAddRate() {
		return addRate;
	}

	public void setAddRate(double addRate) {
		this.addRate = addRate;
	}

	public Date getTimeScheduled() {
		return timeScheduled;
	}

	public void setTimeScheduled(Date timeScheduled) {
		this.timeScheduled = timeScheduled;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Date getTimeFailed() {
		return timeFailed;
	}

	public void setTimeFailed(Date timeFailed) {
		this.timeFailed = timeFailed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getAutoSplitted() {
		return autoSplitted;
	}

	public void setAutoSplitted(Integer autoSplitted) {
		this.autoSplitted = autoSplitted;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public Integer getBidNumber() {
		return bidNumber;
	}

	public void setBidNumber(Integer bidNumber) {
		this.bidNumber = bidNumber;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Integer getMortgaged() {
		return mortgaged;
	}

	public void setMortgaged(Integer mortgaged) {
		this.mortgaged = mortgaged;
	}

	public Long getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Long ordinal) {
		this.ordinal = ordinal;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getRewarded() {
		return rewarded;
	}

	public void setRewarded(Integer rewarded) {
		this.rewarded = rewarded;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public Date getTimeCleared() {
		return timeCleared;
	}

	public void setTimeCleared(Date timeCleared) {
		this.timeCleared = timeCleared;
	}

	public Date getTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(Date timeFinished) {
		this.timeFinished = timeFinished;
	}

	public Date getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(Date timeOpen) {
		this.timeOpen = timeOpen;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public Date getTimeSettled() {
		return timeSettled;
	}

	public void setTimeSettled(Date timeSettled) {
		this.timeSettled = timeSettled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Integer getWhetherTransfer() {
		return whetherTransfer;
	}

	public void setWhetherTransfer(Integer whetherTransfer) {
		this.whetherTransfer = whetherTransfer;
	}

	public String getLoanUserId() {
		return loanUserId;
	}

	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
	}

	public String getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(String guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getGuaranteeRealm() {
		return guaranteeRealm;
	}

	public void setGuaranteeRealm(String guaranteeRealm) {
		this.guaranteeRealm = guaranteeRealm;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getMortgagedType() {
		return mortgagedType;
	}

	public void setMortgagedType(String mortgagedType) {
		this.mortgagedType = mortgagedType;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getStepAmount() {
		return stepAmount;
	}

	public void setStepAmount(BigDecimal stepAmount) {
		this.stepAmount = stepAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(BigDecimal loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}

	public BigDecimal getLoanGuaranteeFee() {
		return loanGuaranteeFee;
	}

	public void setLoanGuaranteeFee(BigDecimal loanGuaranteeFee) {
		this.loanGuaranteeFee = loanGuaranteeFee;
	}

	public BigDecimal getLoanRiskFee() {
		return loanRiskFee;
	}

	public void setLoanRiskFee(BigDecimal loanRiskFee) {
		this.loanRiskFee = loanRiskFee;
	}

	public BigDecimal getLoanManageFee() {
		return LoanManageFee;
	}

	public void setLoanManageFee(BigDecimal loanManageFee) {
		LoanManageFee = loanManageFee;
	}

	public BigDecimal getLoanInterestFee() {
		return loanInterestFee;
	}

	public void setLoanInterestFee(BigDecimal loanInterestFee) {
		this.loanInterestFee = loanInterestFee;
	}

	public BigDecimal getInvestInterestFee() {
		return investInterestFee;
	}

	public void setInvestInterestFee(BigDecimal investInterestFee) {
		this.investInterestFee = investInterestFee;
	}

	public int getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}