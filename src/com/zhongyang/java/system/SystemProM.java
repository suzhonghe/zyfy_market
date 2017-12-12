package com.zhongyang.java.system;

import java.util.Map;

public class SystemProM {
private static Map<String,Object> properties;
	
	static{	
		String url=SystemProM.class.getResource("/").getPath();
		properties=DomParseService.xmlTOmap(url+"systemPro.xml");
	}

	public static Map<String,Object> getProperties() {
		return properties;
	}
}
