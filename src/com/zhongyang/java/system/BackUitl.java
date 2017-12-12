package com.zhongyang.java.system;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年2月17日 下午5:20:40
* 类说明:获取银行内类型
*/
public class BackUitl {
	public static String createBackUitl(String backCode) {
		 Map<String, String> backObj = new HashMap<String, String>();
		 backObj.put("ICBC","中国工商银行");
		 backObj.put("CMB", "招商银行");
		 backObj.put("ABC", "中国农业银行");
		 backObj.put("CCB", "中国建设银行");
		 backObj.put("CMBC","中国民生银行");
		 backObj.put("SPDB","浦发银行");
		 backObj.put("GDB", "广发银行");
		 backObj.put("HXB", "华夏银行");
		 backObj.put("PSBC", "邮储银行");
		 backObj.put("BOC", "中国银行");
		 backObj.put("CEB", "光大银行");
		 backObj.put("BEA", "东亚银行");
		 backObj.put("CIB", "兴业银行");
		 backObj.put("COMM", "交通银行");
		 backObj.put("CITIC", "中信银行");
		 backObj.put("BJBANK", "北京银行");
		 backObj.put("SHRCB", "上海农商银行");
		 backObj.put("WZCB", "温州银行");
		 backObj.put("SPAB", "平安银行");
		 String value= null;
		 for (Entry<String, String> entry : backObj.entrySet()) {
			 String key = entry.getKey().toString();
				 if(key.endsWith(backCode)){
					   value = entry.getValue().toString();
//					   System.out.println("key=" + key + " value=" + value);
				 }
			   }
		return value;	
	}

  
}
