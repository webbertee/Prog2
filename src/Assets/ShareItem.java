package Assets;
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
	public boolean remove(int count) { //renamed
		if(count > 0 && count <= this.count) {
			this.count -= count;
			buyValue -= count * share.getValue();
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param count
	 */
	public boolean add(int count) { //renamed
		if(count > 0) {
			this.count += count;
			buyValue += count * share.getValue();
			return true;
		}
		return false;
	}

}