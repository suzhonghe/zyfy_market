package com.zhongyang.java.system.uitl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;



public class BeanUtils {



	public static Map<String,String> getFieldsAndValues(Object object){
		
		Map<String,String> results = new HashMap();
		
		Set set = new BeanMap(object).entrySet();
		
		
		for(Object entryObj : set){
			Map.Entry entry = (Map.Entry) entryObj;
			
			String key = entry.getKey().toString();
			
			if(entry.getValue() == null)
				continue;
			
			String value = entry.getValue().toString();
			
			if(!key.equalsIgnoreCase("class"))
				results.put(key, value);
			
		}
		return results;
		
	}
}
