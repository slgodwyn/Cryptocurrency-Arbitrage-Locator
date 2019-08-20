package pojos;

/*
 * This class is going to store the pairs that check our desired conditions. The pair with the highest and the pair with the 
 * lowest price are going to be stored as Currencies objects. 
 */

public class Pairs {
	private String name;
	private String coinName;
	private int index;
	private double price;
	private double volume;
	private String source;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}
