package checking;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pojos.Currencies;
import pojos.Pairs;
import stats.PersonalPrefs;
import stats.ProxyClass;

@Stateless
public class PairChecking {
	static Properties properties = new Properties();		//the properties file that contains some constant values we need to retrieve
	private static final String USER_AGENT = "Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion";
	private static final String CMC_URL = "https://coinmarketcap.com/currencies/";			//The URL contains the market table for each currency
	private List<Pairs> pairList;
	private static List<Currencies> currenciesList;
	ProxyClass prox = new ProxyClass();
	
	//"business logic" here
	public List<Currencies> checkPairs(List<String> coinNames) throws IOException, InterruptedException, ParseException {
		Document doc = null;
		Element table;
		currenciesList = new ArrayList<>();		//the currencies list must be initialized each time we start the process over from the first currency
		
		
		//Iterating through all the currencies we need to check
		for(String coin : coinNames) {
			boolean status = false;
			while(!status) {
				try {
					
					String address_plus_port = prox.getRandomAddressAndPort();
					String address = address_plus_port.substring(0, address_plus_port.indexOf(":"));
					int port = Integer.parseInt(address_plus_port.substring(address_plus_port.indexOf(":") + 1, address_plus_port.indexOf(" ")));
					System.out.println(address + "   " + port);
					
					doc = Jsoup.connect(CMC_URL + coin + "/#markets").userAgent(USER_AGENT).get();
					status = true;
				} catch (SocketTimeoutException e1) {
					e1.printStackTrace();
					status = false;
				} catch (HttpStatusException e2) {
					e2.printStackTrace();
					status = false;
				} catch (IOException e3) {
					e3.printStackTrace();
					status = false;
				}finally {
					TimeUnit.MILLISECONDS.sleep(2000);
				}
			}
			System.out.println(coin);
			pairList = new ArrayList<>();		//the pairs list must be initialized each time we iterate a coin

			table = doc.select("tbody").get(0);			//the table containing the exchanges list
			Elements rows = table.select("tr");			//retrieval of each row of the table
			
			
			//Iterating through each currency's pair
			for(Element row : rows){
				
				
				//loop stops in case there is a value with an asterisk. Those values are not needed
				if(row.select("td").get(3).text().contains("*") || row.select("td").get(3).text().contains("*"))
					continue;
				
				NumberFormat format = NumberFormat.getNumberInstance(java.util.Locale.US);		//volume format must be altered because comma is used for the thousands separator.
				Number num = format.parse(row.select("td").get(3).text().substring(1));
				Long volume = num.longValue();
				String index = row.select("td").first().text();
				double price = Double.parseDouble(row.select("td").get(4).text().replaceAll("[^\\d.]", ""));		//'$' sign is removed from the string
				String source = row.select("td").get(1).text();
				String pairString = row.select("td").get(2).text();
			
				//The pair table is sorted according to the market's value in descending order, so when the limit is exceded
				//we can continue to the next coin
				if(volume < PersonalPrefs.getVolumeLimit()) {
					break;
				}else if(Arrays.asList(PersonalPrefs.getExchanges()).contains(source)) {		//checks if the values match certain criteria, creates a new Pairs object then
					Pairs pair = new Pairs();
					pair.setName(pairString);
					pair.setCoinName(coin);
					pair.setIndex(Integer.parseInt(index));
					pair.setPrice(price);
					pair.setSource(source);
					pair.setVolume(volume);
					pairList.add(pair);
					System.out.println(coin + "    " + volume + "    " + pairString + "    " + index + "    " + price);
				}
			}
			/*
			 * As a final procedure for each coin, we must find the minimum and maximum prices contained on the pairList ArrayList
			 * and create a new Currencies object when it passed the "arbitrage conditions". The object then is added to
			 * the currenciesList ArrayList.
			 */
			
			if(pairList.size() < 2) {		//we need at least 2 values in each coin(duh!)
				continue;
			}else {
				int indexOfMinPrice = 0;
				for(Pairs p : pairList) {
					if(p.getPrice() < pairList.get(indexOfMinPrice).getPrice()) {
						indexOfMinPrice = pairList.indexOf(p);
						System.out.println(indexOfMinPrice);
					}
				}
				
				int indexOfMaxPrice = 0;
				for(Pairs p : pairList) {
					if(p.getPrice() > pairList.get(indexOfMaxPrice).getPrice()) {
						indexOfMaxPrice = pairList.indexOf(p);
					}
				}
				
				
				Double numeric_difference = pairList.get(indexOfMaxPrice).getPrice() - pairList.get(indexOfMinPrice).getPrice();
				int arbitrage = (int) (numeric_difference / pairList.get(indexOfMaxPrice).getPrice() * 100);
				System.out.println("ARBI   " + arbitrage);
				if(arbitrage >= PersonalPrefs.getPercentageDifference()) {
					Currencies currency = new Currencies();
					currency.setName(coin);
					currency.setPairHigh(pairList.get(indexOfMaxPrice).getName());
					currency.setPairLow(pairList.get(indexOfMinPrice).getName());
					currency.setPriceHigh(pairList.get(indexOfMaxPrice).getPrice());
					currency.setPriceLow(pairList.get(indexOfMinPrice).getPrice());
					currency.setSourceHigh(pairList.get(indexOfMaxPrice).getSource());
					currency.setSourceLow(pairList.get(indexOfMinPrice).getSource());
					currency.setVolumeHigh(pairList.get(indexOfMaxPrice).getVolume());
					currency.setVolumeLow(pairList.get(indexOfMinPrice).getVolume());
					currency.setIndexHigh(pairList.get(indexOfMaxPrice).getIndex());
					currency.setIndexLow(pairList.get(indexOfMinPrice).getIndex());
					currency.setDifference(arbitrage);
					currenciesList.add(currency);
					System.out.println(currency.getName() + "   " + currency.getDifference() +  "    " + currency.getPriceHigh() + "    " + currency.getPriceLow() + "    " + currency.getPairHigh() + "    " + currency.getPairLow());
					
				}
			}
		}
		System.out.println(currenciesList + "   fffff");
		return currenciesList;
	}
	
	public static List<Currencies> getCurrenciesList (){
		return currenciesList;
	}
	

}
