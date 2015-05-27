package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;

public interface AccountManager {

	/**
	 * 
	 * @param name The Players name
	 * @param cash the amount of money in cents
	 * @throws PlayerAlreadyExistsException if player already exits
	 */
	 void addPlayer(String name, long cash);

	/**
	 * 
	 * @param playerName The players name
	 * @param shareName The name of the share
	 * @param count The amount of shares
	 */
	 void buyShare(String playerName, String shareName, int count)
			throws NotEnoughMoneyException;

	/**
	 * 
	 * @param playerName The players name
	 * @param shareName The name of the share
	 * @param count The amount of shares
	 */
	 void sellShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param name The players name
	 * @return value in cents
	 */
	 long getPlayerDepositValue(String name);

	/**
	 * 
	 * @param name The players name
	 * @return value in cents
	 */
	 long getPlayerCashValue(String name);

	/**
	 * 
	 * @param name The players name
	 * @return value of player's assets in cents
	 */
	 long getPlayerAssetValue(String name);

	 int getPlayerSharesCount(String name, String shareName);

	 long getPlayerSharesValue(String name, String shareName);

	 long getPlayerSharesProfit(String name, String shareName);

	 void addBot(String name, long intervall);

	 void removeBot(String name);
	 
	 boolean playerExists(String name);

	String getHistory(String name, String sortmethod);

}