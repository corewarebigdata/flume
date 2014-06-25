package com.coreware.webservice;

import javax.jws.WebService;
import com.coreware.flume.rpcinterfaceImpl.Quene;

@WebService(endpointInterface = "com.coreware.webservice.IFlumeService") 
public class FlumeService implements IFlumeService {
	
	
	private static Quene offerQuene = Quene.newInstance();
	
	/**
	 * @author �Ż� 
	 * @descirption ���Ȳ��� 
	 * @param 
	 * 
	 * **/
	@Override
	public void sendMsg(String event){
		offerQuene.offerQue(event);
	}
	
}
