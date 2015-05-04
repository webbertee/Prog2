package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;

public interface AccountManager {

	/**
	 * 
	 * @param name
	 * @throws PlayerAlreadyExistsException 
	 */
	public void addPlayer(String name, long cash);
	
	
	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws NotEnoughMoneyException 
	 * @throws UnknownShareException 
	 */
	public void buyShare(String playerName, String shareName, int count) throws NotEnoughMoneyException;

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws UnknownShareException 
	 */
	public void sellShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param name
	 */
	public long getPlayerDepositValue(String name);

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

	public int getPlayerSharesCount(String name, String shareName);
	
	public long getPlayerSharesValue(String name, String shareName);
	
	public long getPlayerSharesProfit(String name, String shareName);

	public void addBotToPlayer(String name, int buycount, long intervall);
	
}