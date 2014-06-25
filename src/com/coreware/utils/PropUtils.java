package com.coreware.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtils {
	
	public static String getValue(String name){
		Properties p = new Properties(); 
		String value = "";
		try {  
			InputStream in = PropUtils.class.getResourceAsStream("/flume.properties");
			p.load(in);  
			in.close();  
			if(p.containsKey(name)){  
				value = p.getProperty(name);  
			} 
			
		} catch (IOException ex) {  
			ex.printStackTrace();  
		}  
		return value;
	}
	
	public static void main(String[] args){
		System.out.println(getValue("socketPort"));
	}

}
