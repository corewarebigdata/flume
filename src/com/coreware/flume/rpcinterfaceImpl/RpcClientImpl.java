package com.coreware.flume.rpcinterfaceImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

public class RpcClientImpl {
	
	private static String hostname = "";
	private static  int port = 0;
	
	public RpcClientImpl() {
		
		Properties prop  =new Properties();
		String path = RpcClientImpl.class.getResource("").getPath();
		File file = new File(path+"flumeAddr.properties");
	    InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			new Exception("没有找到flume端口配置文件 ！");
		}
	    
		try {
			prop.load(in);
			this.hostname = prop.getProperty("hostaddr");
			this.port = Integer.valueOf(prop.getProperty("port"));
		} catch (IOException e) {
			new Exception("没有找到flume配置文件");
		}
	}
	
	public void sendListToFlume(List<Event> listEvent){
		
		MyRpcClientFacade client = new MyRpcClientFacade();
		
	    client.init(hostname,port);
	    client.sendListToFlume(listEvent);
	    client.cleanUp();
	}
}

class MyRpcClientFacade {
  private RpcClient client;
  private String hostname;
  private int port;

  public void init(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
    this.client = RpcClientFactory.getDefaultInstance(hostname, port);
  }

  public void sendDataToFlume(String data) {
    Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"));

    try {
      client.append(event);
    } catch (EventDeliveryException e) {
      client.close();
      client = null;
      client = RpcClientFactory.getDefaultInstance(hostname, port);
    }
  }
  
  public void sendListToFlume(List<Event> list) {
	    try {
	      client.appendBatch(list);
	    } catch (EventDeliveryException e) {
	      client.close();
	      client = null;
	      client = RpcClientFactory.getDefaultInstance(hostname, port);
	    }
	  }

  public void cleanUp() {
    client.close();
  }

	public String getHostname() {
		return hostname;
	}
	
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

}
