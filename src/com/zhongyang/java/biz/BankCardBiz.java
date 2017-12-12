package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.ModifyBankCardCallBackVO;
import com.zhongyang.java.vo.bankCardCallBackVO;

public interface BankCardBiz {
		public Message bankCardBinding(String bank,String account,String fastPayment,User user,String source);
		
		public Message modifyBankCard(String bank,String account,User user,String sourceV,String source);
		
		public String umpCardBinding(bankCardCallBackVO vo);
		
		public String modifyBankCardCallBack(ModifyBankCardCallBackVO vo);
		
		public Message appBankCardBinding(String bank, String account, String fastPayment, User user);
}
