public class ShareDepositAccount extends Asset{

	private int shareItemCount;
	private ShareItem[] shareItems;
//	private final int DEFAUT_LENGTH = 10;
//	private final int LENGTH_CHANGE = 10;
	
	public int getLengthDebug() {
		return shareItems.length;
	}

	public ShareDepositAccount(String name) {
		super(name);
		shareItemCount = 0;
		
		//shareItems = new ShareItem[DEFAUT_LENGTH];
		//Alternativ
		shareItems = new ShareItem[0];
	}

	public String toString() {
		return "ShareDepositAccount with " + shareItemCount + "ShareItems";
	}

	public long getValue() {
		long value = 0;
		for (int i = 0; i < shareItemCount; i++) {
			value += shareItems[i].getValue();
		}
		return value;
	}
	public long getBuyValue() {
		long value = 0;
		for (int i = 0; i < shareItemCount; i++) {
			value += shareItems[i].getBuyValue();
		}
		return value;
	}

	/**
	 * 
	 * @param share
	 * @param count
	 */
	public boolean addShare(Share share, int count) {
		// suche Entsprechendes Paket im Array
		int shareItemIndex = findShareItem(share);
		if (shareItemIndex >= 0)
			return shareItems[shareItemIndex].add(count);

		// Kein entsprechendes Paket vorhanden.
		
		checkAddSize();
		shareItems[shareItemCount] = new ShareItem(share);
		// Frage: Wo soll die Eingabe überprüft werden?
		if (shareItems[shareItemCount].add(count)) {
			shareItemCount++;
			return true;
		}
		shareItems[shareItemCount] = null;
		return false;
	}

	private int findShareItem(Share share) {
		for (int i = 0; i < shareItemCount; i++) {
			if (shareItems[i].getName().equals(share.getName())) { // hässlich.
																	// schöner?
				return i;
			}
		}
		return -1;
	}

	private void checkAddSize() {
//		if (shareItems.length <= shareItemCount) {
//			ShareItem[] newShareItems = new ShareItem[shareItems.length
//					+ LENGTH_CHANGE];
//			System.arraycopy(shareItems, 0, newShareItems, 0, shareItemCount);
//			shareItems = newShareItems;
//		}
		
		//Alternativ
		ShareItem[] newShareItems = new ShareItem[shareItems.length + 1];
		System.arraycopy(shareItems, 0, newShareItems, 0, shareItemCount);
		shareItems = newShareItems;
	}
	
	private void checkRemoveSize() {
//		if(shareItems.length > shareItemCount + LENGTH_CHANGE) {
//			ShareItem[] newShareItems = new ShareItem[shareItemCount 
//			                                          + LENGTH_CHANGE / 2]; //shareItemCount + x?
//			System.arraycopy(shareItems, 0, newShareItems, 0, shareItemCount);
//			shareItems = newShareItems;
//		}
		
		//Alternativ
		ShareItem[] newShareItems = new ShareItem[shareItems.length - 1];
		System.arraycopy(shareItems, 0, newShareItems, 0, shareItemCount);
		shareItems = newShareItems;
	}

	/**
	 * 
	 * @param share
	 * @param count
	 */
	public boolean removeShare(Share share, int count) {
		int shareItemIndex = findShareItem(share);
		// abbrechen, wenn das shareItem nicht vorhanden ist
		// oder das hinzufügen scheitert
		if (shareItemIndex < 0 || !shareItems[shareItemIndex].remove(count))
			return false;
		
		//Wenn das Depot leer ist, entferne es aus dem Speicher
		if (shareItems[shareItemIndex].getCount() == 0) {
			System.arraycopy(shareItems, shareItemIndex + 1, shareItems,
					shareItemIndex, shareItemCount - shareItemIndex - 1);
			shareItemCount--;
			checkRemoveSize();
		}
		return true;
	}
}