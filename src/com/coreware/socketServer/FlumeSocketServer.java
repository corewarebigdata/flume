package com.coreware.socketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.coreware.flume.rpcinterfaceImpl.Quene;
import com.coreware.utils.PropUtils;

public class FlumeSocketServer implements ServletContextListener {
	
	private static ServerSocket server ;
	private static BufferedReader is;
	private static Socket socket;
	private static Quene offerQuene = Quene.newInstance();
	
	
	private static void init(){
		System.out.println("Flume Server init..");
		try {
			if(server == null){
				int port = Integer.parseInt(PropUtils.getValue("socketPort"));
				server=new ServerSocket(port);
			}

			if(socket !=null){
				socket.close();
			}
			socket = server.accept(); 
			is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void run(){
		System.out.println("Flume Server start..");
		init();
  		String msg ="";
  		
  		while(true){
  			try{
  				msg = is.readLine();
		  		if(msg !=null && !msg.isEmpty()){
		  			offerQuene.offerQue(msg);
		  			msg ="";
		  		}else{
	  				init();
	  			}	
  			}catch(Exception e){
  				e.printStackTrace();
  				init();
  			}	
  		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		if(server != null){
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		run();
	}

}
