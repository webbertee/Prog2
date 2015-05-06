package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.commands.AsCommand;
import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;

public interface AccountManager {

	/**
	 * 
	 * @param name
	 * @throws PlayerAlreadyExistsException
	 */
	@AsCommand(command = "crp", helpText = "<name> <money> * create a new player by name", feedback = "Player created")
	public void addPlayer(String name, long cash);

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws NotEnoughMoneyException
	 * @throws UnknownShareException
	 */
	@AsCommand(command = "buys", helpText = "<playername> <sharename> <count> * buy a amout of shares", feedback = "player created")
	public void buyShare(String playerName, String shareName, int count)
			throws NotEnoughMoneyException;

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 * @throws UnknownShareException
	 */
	@AsCommand(command = "sells", helpText = "<playername> <sharename> <amount> * sell a amount of shares", feedback = "Shares successfully sold")
	public void sellShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param name
	 */
	@AsCommand(command = "sdv", helpText = "<name> * get the value of the players share deposit", feedback = "Value of all Shares: ")
	public long getPlayerDepositValue(String name);

	/**
	 * 
	 * @param name
	 */
	@AsCommand(command = "scv", helpText = "<name> * show players remaining cash", feedback = "Cash left: ")
	public long getPlayerCashValue(String name);

	/**
	 * 
	 * @param name
	 */
	@AsCommand(command = "sav", helpText = "<name> * show the sum of a players assets", feedback = "Sum of players assets: ")
	public long getPlayerAssetValue(String name);

	@AsCommand(command = "ssc", helpText = "<name> <shareName> *  show amount of shares in deposit", feedback = "Number of Shares: ")
	public int getPlayerSharesCount(String name, String shareName);

	@AsCommand(command = "sps", helpText = "<name> <shareName> * show value of a players collection of shares", feedback ="Value of Shares:")
	public long getPlayerSharesValue(String name, String shareName);

	@AsCommand(command = "ssp", helpText = "<name> <shareName> * show profit made by a specific share", feedback = "Current Profit: ")
	public long getPlayerSharesProfit(String name, String shareName);

	@AsCommand(command = "abot", helpText = "<name> <intervall> * add a bot to a player", feedback =  "Bot added.")
	public void addBot(String name, long intervall);

	@AsCommand(command = "rbot", helpText = "<name> * remove bot from a Player", feedback ="bot removed")
	public void removeBot(String name);

}