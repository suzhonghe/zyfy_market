package com.zhongyang.java.system;



public class MStringUtils {

	
/**
 * 字符串替换加密部分明文信息
 * @param source 需要加密的字符串 
 * @param start 起始索引
 * @param end  结束索引
 * @param c    替换字符
 * @return
 */
	public static String decrypt(String source, int start, int end, char c){
		
		
		if(source == null || source == "")
			return null;
		StringBuilder sb = new StringBuilder(source);
		for(int i = start; i<end;i++)
			sb.setCharAt(i-1, c);
		
		return sb.toString();
	}
	

}
