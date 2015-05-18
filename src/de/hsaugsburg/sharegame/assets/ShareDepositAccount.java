package de.hsaugsburg.sharegame.assets;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsaugsburg.sharegame.assets.exceptions.RemoveShareException;

public class ShareDepositAccount extends Asset {

	private Map<String, ShareItem> shareItems;
	private Logger logger = Logger.getLogger(ShareDepositAccount.class.getName());

	public ShareDepositAccount(String name) {
		super(name);

		// shareItems = new ShareItem[DEFAUT_LENGTH];
		// Alternativ
		shareItems = new HashMap<String, ShareItem>();
	}

	public String toString() {
		return "ShareDepositAccount with " + shareItems.size() + " ShareItems";
	}

	public int getShareCount(Share share) {
		ShareItem sa = shareItems.get(share.getName());
		if (sa == null)
			return 0;
		else
			return sa.getCount();
	}

	public long getValue() {
		long value = 0;
		for (ShareItem sa : shareItems.values()) {
			value += sa.getValue();
		}
		return value;
	}

	public long getBuyValue() {
		long value = 0;
		for (ShareItem sa : shareItems.values()) {
			value += sa.getBuyValue();
		}
		return value;
	}

	/**
	 * 
	 * @param share
	 * @param count
	 */
	public void addShare(Share share, int count) {
		// suche Entsprechendes Paket im Array
		ShareItem sa = shareItems.get(share.getName());
		if (sa == null) {
			logger.log(Level.FINEST, "Added a new ShareItem: " + share.getName());
			sa = new ShareItem(share, super.getName() + "_" + share.getName());
			shareItems.put(share.getName(), sa);
		}
		sa.add(count);
	}

	/**
	 * 
	 * @param share
	 * @param count
	 */
	public void removeShare(Share share, int count) {
		ShareItem sa = shareItems.get(share.getName());

		if (sa == null)
			throw new RemoveShareException("No " + share.getName()
					+ " shares in " + this.toString());
		if (sa.getCount() < count)
			throw new RemoveShareException("Only " + sa.getCount() + " "
					+ share.getName() + " shares remaining");
		
		sa.remove(count);
	}

	public int getSharesCount(Share share) {
		ShareItem sa = shareItems.get(share.getName());
		if (sa == null)
			return 0;
		else
			return shareItems.get(share.getName()).getCount();

	}

	public long getSharesValue(Share share) {
		ShareItem sa = shareItems.get(share.getName());
		if (sa == null)
			return 0;
		else
			return shareItems.get(share.getName()).getValue();
	}

	public long getSharesBuyValue(Share share) {
		ShareItem sa = shareItems.get(share.getName());
		if (sa == null)
			return 0;
		else
			return shareItems.get(share.getName()).getBuyValue();
	}
}