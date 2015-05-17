package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;

public interface AccountManager {

	/**
	 * 
	 * @param name
	 * @throws PlayerAlreadyExistsException
	 */
	 void addPlayer(String name, long cash);

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws NotEnoughMoneyException
	 * @throws UnknownShareException
	 */
	 void buyShare(String playerName, String shareName, int count)
			throws NotEnoughMoneyException;

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws UnknownShareException
	 */
	 void sellShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param name
	 */
	 long getPlayerDepositValue(String name);

	/**
	 * 
	 * @param name
	 */
	 long getPlayerCashValue(String name);

	/**
	 * 
	 * @param name
	 */
	 long getPlayerAssetValue(String name);

	 int getPlayerSharesCount(String name, String shareName);

	 long getPlayerSharesValue(String name, String shareName);

	 long getPlayerSharesProfit(String name, String shareName);

	 void addBot(String name, long intervall);

	 void removeBot(String name);
	 
	 String getHistory(String name);
	 
	 boolean playerExists(String name);

}