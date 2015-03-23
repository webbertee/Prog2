public class Share {

	private long value;
	private final String name;

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public Share(String name, long value) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param share
	 */
	public boolean equals(Object share) {
		if (share instanceof Share) {
			Share other = (Share) share;
			if (other.name == name && other.value == value) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Share " + name + " with value" + value;
	}
}