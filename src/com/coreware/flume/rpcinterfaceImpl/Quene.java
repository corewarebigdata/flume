package com.coreware.flume.rpcinterfaceImpl;

import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;

public class Quene {
	
	private static Quene quene = new Quene();
	
	private static int maxlength = 5000000;
	
    public static LinkedBlockingQueue<Event> incache = new LinkedBlockingQueue<Event>(maxlength);
    
    public Quene(){}
    
    public Quene(int maxlength){
    	this.maxlength = maxlength;
    }
    
    public static Quene newInstance(){
    	return quene;
    }
    
    public synchronized void offerQue(String event){
    	try{
    		Event flumeEventCover = EventBuilder.withBody(event,Charset.forName("utf-8"));
	    	this.incache.offer(flumeEventCover);
    	}catch(Exception e){
    		new Exception("数据进队失败");
    	}
    }

}
