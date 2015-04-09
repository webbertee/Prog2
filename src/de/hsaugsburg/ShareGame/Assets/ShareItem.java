package de.hsaugsburg.ShareGame.Assets;
public class ShareItem extends Asset {

	private int count;
	private long buyValue; //?!?!
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

	/**
	 * 
	 * @param share
	 * @param count
	 */
	public ShareItem(Share share) {
		super(share.getName());
		this.share = share;
	}

	/**
	 * 
	 * @param count
	 */
	public void remove(int count) { //renamed
		if(count < 1)
			throw new IllegalArgumentException("count must be > 0");
		
		if(count > this.count)
			throw new  IllegalStateException("Can not sell "+count+" shares, only "+this.count+"left"); 
		
		this.count -= count;
		buyValue -= count * share.getValue();
	}

	/**
	 * 
	 * @param count
	 */
	public void add(int count) { //renamed
		if(count < 1)
			throw new IllegalArgumentException("Count must be > 0");
		
		this.count += count;
		buyValue += count * share.getValue();
	}

}