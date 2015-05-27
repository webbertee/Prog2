package de.hsaugsburg.sharegame.assets;
public class Share {

	private long value;
	private final String name;


	public Share(String name, long value) {
		this.value = value;
		this.name = name;
	}


	public void setValue(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}


	@Override
	public boolean equals(Object share) {
		if(this == share) 
			return true;
		
		if (share instanceof Share) {
			Share other = (Share) share;
			if (other.name.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Share " + name + " with value" + value;
	}
}