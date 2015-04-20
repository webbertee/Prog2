package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;

public interface AccountManager {

	/**
	 * 
	 * @param name
	 */
	public void addPlayer(String name, long cash);
	
	
	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws NotEnoughMoneyException 
	 */
	public void buyShare(String playerName, String shareName, int count) throws NotEnoughMoneyException;

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 */
	public void sellShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param name
	 */
	public long getPlayerSharesValue(String name);

	/**
	 * 
	 * @param name
	 */
	public long getPlayerCashValue(String name);

	/**
	 * 
	 * @param name
	 */
	public long getPlayerAssetValue(String name);



}