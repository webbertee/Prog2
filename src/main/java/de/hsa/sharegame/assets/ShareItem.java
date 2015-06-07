package de.hsa.sharegame.assets;
public class ShareItem extends Asset {

	private int count;
	private long buyValue; 
	private Share share;

	public long getValue() {
		return share.getValue() * count;
	}
	
	public int getCount() {
		return count;
	}

	public long getBuyValue() {
		return this.buyValue;
	}

	public String toString() {
		return "ShareItem " + getName() + "with " + count + share.getName() + " shares.";
	}



	
	public ShareItem(Share share, String name) {
		super(name);
		this.share = share;
	}

	public void remove(int count) {
		if(count == 0)
			return;
		
		if(count < 0)
			throw new IllegalArgumentException("count must be >= 0");
		
		if(count > this.count)
			throw new  IllegalStateException("Can not sell "+count+" shares, only "+this.count+"left"); 
		
		this.count -= count;
		buyValue -= count * share.getValue();
	}


	public void add(int count) {
		if(count < 1)
			throw new IllegalArgumentException("Count must be > 0");
		
		this.count += count;
		buyValue += count * share.getValue();
	}

}