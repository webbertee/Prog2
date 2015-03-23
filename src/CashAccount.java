public class CashAccount extends Asset {

	private long value;

	
	public void add(long amount) {
		value += amount;
	}
	
	public boolean remove(long amount) {
		if(value >= amount) {
			value -= amount;
			return true;
		}else {
			return false;
		}
	}
	public long getValue() {
		return this.value;
	}

	public String toString() {
		return "CashAccount " + this.getName() + " with Value " + getValue();
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public CashAccount(String name, long value) {
		super(name);
		this.value = value;
	}

}