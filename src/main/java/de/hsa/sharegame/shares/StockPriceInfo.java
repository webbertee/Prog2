package de.hsa.sharegame.shares;


public interface StockPriceInfo {
	public long getShareValue(String name);
	
	public String getAllShares();
	
	public String[] getShareNames();
}
