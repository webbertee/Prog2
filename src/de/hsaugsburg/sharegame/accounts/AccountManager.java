package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;

public abstract class AccountManager {

	/**
	 * 
	 * @param name
	 * @throws PlayerAlreadyExistsException
	 */
	public abstract void addPlayer(String name, long cash);

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws NotEnoughMoneyException
	 * @throws UnknownShareException
	 */
	public abstract void buyShare(String playerName, String shareName, int count)
			throws NotEnoughMoneyException;

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws UnknownShareException
	 */
	public abstract void sellShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param name
	 */
	public abstract long getPlayerDepositValue(String name);

	/**
	 * 
	 * @param name
	 */
	public abstract long getPlayerCashValue(String name);

	/**
	 * 
	 * @param name
	 */
	public abstract long getPlayerAssetValue(String name);

	public abstract int getPlayerSharesCount(String name, String shareName);

	public abstract long getPlayerSharesValue(String name, String shareName);

	public abstract long getPlayerSharesProfit(String name, String shareName);

	public abstract void addBot(String name, long intervall);

	public abstract void removeBot(String name);

}