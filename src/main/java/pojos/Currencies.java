package pojos;

/*
 * This class will hold the coin's info with an arbitrage chance.
 */

public class Currencies {
	
	private String name;
	private String pairLow;
	private String pairHigh;
	private double priceLow;
	private double priceHigh;
	private String sourceLow;
	private String sourceHigh;
	private double volumeLow;
	private double volumeHigh;
	private int indexLow;
	private int indexHigh;
	private int difference;		//in percentage
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPairLow() {
		return pairLow;
	}
	public void setPairLow(String pairLow) {
		this.pairLow = pairLow;
	}
	public String getPairHigh() {
		return pairHigh;
	}
	public void setPairHigh(String pairHigh) {
		this.pairHigh = pairHigh;
	}
	public double getPriceLow() {
		return priceLow;
	}
	public void setPriceLow(double priceLow) {
		this.priceLow = priceLow;
	}
	public double getPriceHigh() {
		return priceHigh;
	}
	public void setPriceHigh(double priceHigh) {
		this.priceHigh = priceHigh;
	}
	public String getSourceLow() {
		return sourceLow;
	}
	public void setSourceLow(String sourceLow) {
		this.sourceLow = sourceLow;
	}
	public String getSourceHigh() {
		return sourceHigh;
	}
	public void setSourceHigh(String sourceHigh) {
		this.sourceHigh = sourceHigh;
	}
	public double getVolumeLow() {
		return volumeLow;
	}
	public void setVolumeLow(double volumeLow) {
		this.volumeLow = volumeLow;
	}
	public double getVolumeHigh() {
		return volumeHigh;
	}
	public void setVolumeHigh(double volumeHigh) {
		this.volumeHigh = volumeHigh;
	}
	public int getDifference() {
		return difference;
	}
	public void setDifference(int difference) {
		this.difference = difference;
	}
	public int getIndexLow() {
		return indexLow;
	}
	public void setIndexLow(int indexLow) {
		this.indexLow = indexLow;
	}
	public int getIndexHigh() {
		return indexHigh;
	}
	public void setIndexHigh(int indexHigh) {
		this.indexHigh = indexHigh;
	}
	
}
