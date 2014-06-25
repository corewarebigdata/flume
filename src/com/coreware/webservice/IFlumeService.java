package com.coreware.webservice;

import javax.jws.WebService;

@WebService
public interface IFlumeService {
	
	public void sendMsg(String event);

}
