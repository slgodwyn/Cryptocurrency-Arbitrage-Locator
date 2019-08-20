package api;


import javax.ejb.Stateless;

import init.InitialProcessController;

@Stateless
public class JsonOutput {
	
	public static String getArbiJson() {
		if(InitialProcessController.getCoinJsonList() == null) {
			return "{}";
		}else {
			return InitialProcessController.getCoinJsonList().toString();
		}
	}
	
	//for testing
	public static String getSth() {
		return "eeeeeeeeeeeeeeeeeeeeeeeeeee";
	}
}
