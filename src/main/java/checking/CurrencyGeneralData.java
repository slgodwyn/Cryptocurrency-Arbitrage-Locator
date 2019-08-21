package checking;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.HttpHeaders;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


/*
 * This class's use is to return general info about active currencies such as name, id etc.
 * The returnCurrencies() method returns an ArrayList of the currencies's names.
 * A method to return the id of the currencies is not yet implemented
 */
@Stateless
public class CurrencyGeneralData {
	
	private String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";		//API endpoint returning a list of all active cryptocurrencies with latest market data
	private static String apiKey = "xxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxx";		//apiKey
	private List<NameValuePair> parameters = new ArrayList<>();		//every parameter we need to add to the endpoint will be stored in this list
	private List<String> coinResults;
	private int start, end;
	
	//adds the URI parameters on the API endpoint. We only care about 'start' and 'limit'.
	public  void addUriParameters(int start, int end) {					//start must be larger than or equal to 1
		parameters.add(new BasicNameValuePair("start", String.valueOf(start)));
		parameters.add(new BasicNameValuePair("limit", String.valueOf(end)));
		this.start = start;
		this.end = end;
	}
	
	//returns a list of the Currencies we want to check
	public List<String> returnCurrencies() throws URISyntaxException, ClientProtocolException, IOException {
    	String response_content = "";
    	coinResults = new ArrayList<>();

        URIBuilder uriBuilder = new URIBuilder(uri);
        uriBuilder.addParameters(parameters);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(uriBuilder.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);
        CloseableHttpResponse response = client.execute(request);
        
        try {
        	System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
        	response.close();
        }
        
        JSONObject jsonObj = new JSONObject(response_content);
        JSONArray arr = jsonObj.getJSONArray("data");
        
		for (int i = start-1; i < end; i++){
			//Some String modifications on the elements of the list because of HTTP Status Exceptions
			if(arr.getJSONObject(i).getString("name").equals("XRP")) {
				coinResults.add("ripple");
				continue;
			}else if(arr.getJSONObject(i).getString("name").equals("Crypto.com Chain")){
				coinResults.add("crypto-com-chain");
				continue;
			}else if(arr.getJSONObject(i).getString("name").contains(" ")){
				coinResults.add(arr.getJSONObject(i).getString("name").replaceAll(" ", "-"));
				continue;
			}	
			coinResults.add(arr.getJSONObject(i).getString("name"));
		}
        
        return coinResults;
    }

}
