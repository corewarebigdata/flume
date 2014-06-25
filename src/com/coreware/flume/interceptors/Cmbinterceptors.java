package com.coreware.flume.interceptors;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cmbinterceptors implements Interceptor{

	
	/**
	 * @author 杜焕
	 * @description 添加拦截器共通字段 
	 * 
	 * @param 常量描述在constant里
	 * 
	 * **/
	 private static final Logger logger = LoggerFactory
	          .getLogger(Cmbinterceptors.class);
	 
	 private Cmbinterceptors(){}
	 
	 private Cmbinterceptors(boolean preserveExisting,boolean useIP, String header) {
	    
	}
	 
	@Override
	public void close() {
		
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public Event intercept(Event event) {
		
		 Map<String, String> headers = event.getHeaders();
		 
		 try {
			 InetAddress addr = null;
			 addr = InetAddress.getLocalHost();
			 
			 // 向header里面存值  
			 String agent_timestamp = String.valueOf(System.currentTimeMillis());
			 String source_host = addr.getHostName(); 
			 String source_timestamp = "";
			 String source_original_time =""; 
			 String source_msg = new String(event.getBody());
			 
			 headers.put(Constants.AGENT_TIMESTAMP, agent_timestamp);
			 headers.put(Constants.SOURCE_HOST, source_host);
			 headers.put(Constants.SOURCE_TIMESTAMP, source_timestamp);
			 headers.put(Constants.SOURCE_ORIGINAL_TIME, source_original_time);
			 headers.put(Constants.SOURCE_MSG, source_msg);
		 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return event;
	}

	@Override
	public List<Event> intercept(List<Event> events) {
		
		for (Event event : events) {
		      intercept(event);
		}
		return events;
	}
	
	
	
    public static class Builder implements Interceptor.Builder {
    	
	    @Override
	    public Interceptor build() {
	    	return new Cmbinterceptors();
	    }

	    @Override
	    public void configure(Context context) {}
	}
}
