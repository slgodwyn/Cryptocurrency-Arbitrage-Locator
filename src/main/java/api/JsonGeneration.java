package api;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.json.JSONObject;

import checking.PairChecking;
import pojos.Currencies;

@Stateless
public class JsonGeneration {
	List<Currencies> coins;
	private List<JSONObject> coinsToJSON;
	//private PairChecking pc = new PairChecking();
	
	
	public List<JSONObject> exportArbitrageToJSON() {
		coins = PairChecking.getCurrenciesList();
		coinsToJSON = new ArrayList<>();
		System.out.println(coins);
		for(Currencies coin : coins) {
			JSONObject jsonMain = new JSONObject();
			JSONObject jsonBuy = new JSONObject();
			JSONObject jsonSell = new JSONObject();
			
			jsonMain.put("name", coin.getName());
			
			jsonBuy.put("price", coin.getPriceLow());
			jsonBuy.put("pair", coin.getPairLow());
			jsonBuy.put("source", coin.getSourceLow());
			jsonBuy.put("volume", coin.getVolumeLow());
			jsonBuy.put("index", coin.getIndexLow());
			jsonMain.put("buy", jsonBuy);
			
			jsonSell.put("price", coin.getPriceHigh());
			jsonSell.put("pair", coin.getPairHigh());
			jsonSell.put("source", coin.getSourceHigh());
			jsonSell.put("volume", coin.getVolumeHigh());
			jsonSell.put("index", coin.getIndexHigh());
			jsonMain.put("sell", jsonSell);
			
			System.out.println(coin.getName() + "    gggggg");
			System.out.println(jsonMain.toString());
			coinsToJSON.add(jsonMain);
		}
		System.out.println(coinsToJSON);
		return coinsToJSON;
	}
	

}
