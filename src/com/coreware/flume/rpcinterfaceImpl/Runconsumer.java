package com.coreware.flume.rpcinterfaceImpl;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class Runconsumer implements ServletContextListener {
	
	private Runconsumer runconsumer;  
    ThreadPoolManager tpm = ThreadPoolManager.newInstance();
    
    public void runConsupmer(){
        tpm.runConsupmer();  
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0){
		
		try{
			runconsumer.runConsupmer();
		}catch(Exception e){
			new Exception("�̳߳ص��ȴ���ʧ��!");
		}
	}  
}
