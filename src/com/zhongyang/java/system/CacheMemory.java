package com.zhongyang.java.system;

import java.util.HashMap;
import java.util.Map;

public class CacheMemory {
	
		private static CacheMemory cacheMemory;
		private  Map<String,Object> parameter;
		
		
		public static  Map<String, Object> getCacheMemory() {
			if(cacheMemory==null){
				cacheMemory=new CacheMemory();
				cacheMemory.setParameter(new HashMap<String,Object>());
			}
			return cacheMemory.getParameter();
		}
		
		private  Map<String, Object> getParameter() {
			return parameter;
		}
		
		private void setParameter(Map<String, Object> parameter) {
			this.parameter = parameter;
		}

		private CacheMemory() {}
		
		
		
		
		
		
		
}
