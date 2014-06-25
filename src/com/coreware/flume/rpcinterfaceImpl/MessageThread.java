package com.coreware.flume.rpcinterfaceImpl;
import java.util.List;
import org.apache.flume.Event;

public class MessageThread implements Runnable {  
    
    private List<Event> eventList ;
      
    public MessageThread(){  
        super();  
    }
      
    public MessageThread(List<Event> eventList ){
    	this.eventList = eventList;
    }
    
	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public void run() {
		RpcClientImpl rpcSend = new RpcClientImpl()	;
		rpcSend.sendListToFlume(eventList);
    }  
	
}