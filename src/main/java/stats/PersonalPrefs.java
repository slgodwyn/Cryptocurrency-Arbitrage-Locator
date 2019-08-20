package stats;

public class PersonalPrefs {
	private static final double VOLUME_LIMIT = 5000;	//The minimum 24 hour trading volume of a pair we need
	private static final int PERCENTAGE_DIFFERENCE = 1;	//The minimum difference we need between prices of two pairs 
	//private static final String[] EXCHANGES = {"Binance", "EtherDelta", "Tidex", "YoBit",  "KuCoin", "HitBTC", "CoinExchange", "ForkDelta"};
	private static final String[] EXCHANGES = {"Binance", "YoBit",  "KuCoin", "HitBTC"};
	private static final String[] BASECOINS = {"Bitcoin", "Ethereum", "Dogecoin", "Litecoin", "Binance Coin", "Tether"};
	
	
	public static double getVolumeLimit() {
		return VOLUME_LIMIT;
	}
	public static int getPercentageDifference() {
		return PERCENTAGE_DIFFERENCE;
	}
	public static String[] getExchanges() {
		return EXCHANGES;
	}
	public static String[] getBasecoins() {
		return BASECOINS;
	}
	
	
}
