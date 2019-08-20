package cmc;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import stats.PersonalPrefs;

class Exchanges {
	private List<String> exchangeList;

	//A test suite to check whether or not the exchanges we use are still listed in top 100 exchanges oncoinmarketcap.com
	@Test
	void testExchangesExistance() throws URISyntaxException, ClientProtocolException, IOException {
		exchangeList = new ArrayList<String>();
    	Document doc = Jsoup.connect("https://coinmarketcap.com/rankings/exchanges/").get();		//jsoup connection to the url
    	Element table = doc.getElementById("exchange-rankings");		//the table containing the exchanges list
    	Elements rows = table.select("tr");			//retrieval of each row of the table
    	
		for (int i = 1; i < rows.size(); i++) {
			System.out.println(rows.get(i).text());
			exchangeList.add(rows.get(i).select("td").get(1).text());
		}
		assertTrue(exchangeList.containsAll(Arrays.asList(PersonalPrefs.getExchanges())));

	}

}
