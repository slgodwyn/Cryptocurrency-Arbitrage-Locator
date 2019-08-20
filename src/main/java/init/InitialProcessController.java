package init;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONObject;

import api.JsonGeneration;
import checking.CurrencyGeneralData;
import checking.PairChecking;

public class InitialProcessController implements Runnable{

	private static List<JSONObject> coinJsonList;
	
	@Override
	public void run() {
		while(true){
			CurrencyGeneralData data = new CurrencyGeneralData();
			PairChecking pairCheck = new PairChecking();
			JsonGeneration jsonGen = new JsonGeneration();
			
			data.addUriParameters(10, 11);
			try {
				System.out.println(pairCheck.checkPairs(data.returnCurrencies()));
			} catch (IOException | InterruptedException | ParseException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			jsonGen.exportArbitrageToJSON();
			coinJsonList = jsonGen.exportArbitrageToJSON();
			 
		}
	}
	
	public static List<JSONObject> getCoinJsonList() {
		return coinJsonList;
	}
	

}
