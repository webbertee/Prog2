package de.hsa.sharegame.accounts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.commands.AsCommand;
import de.hsa.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsa.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.assets.exceptions.NotEnoughMoneyException;
import de.hsa.sharegame.shares.StockPriceProvider;

public class AccountManagerImpl implements AccountManager {
	private Map<String, Player> players;
	private StockPriceProvider priceProvider;
	private final Logger logger = Logger.getLogger(AccountManagerImpl.class
			.getName());

	public AccountManagerImpl(StockPriceProvider priceProvider) {
		players = new HashMap<String, Player>();
		this.priceProvider = priceProvider;
	}

	@Override
	@AsCommand(command = "crp", helpText = "addPlayerHelp", feedback = "addPlayerFeedback")
	public void addPlayer(String name, long cash)
			throws PlayerAlreadyExistsException {
		if (players.get(name) != null)
			throw new PlayerAlreadyExistsException(name);

		players.put(name, new Player(name, cash));
	}

	@Override
	@AsCommand(command = "buys", helpText = "buyShareHelp", feedback = "buyShareFeedback")
	public void buyShare(String playerName, String shareName, int count)
			throws NotEnoughMoneyException {
		logger.log(Level.FINEST, "Attemting to buy " + count + " " + shareName
				+ " shares");
		try {
			getPlayer(playerName).buyShare(priceProvider.getShare(shareName),
					count);
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, "Buying a share failed", e);
			throw e;
		}
		logger.log(Level.INFO, "Player " + playerName + " bought " + count
				+ " " + shareName + " shares");
	}

	@Override
	@AsCommand(command = "sells", helpText = "sellShareHelp", feedback = "sellShareFeedback")
	public void sellShare(String playerName, String shareName, int count) {
		logger.log(Level.FINEST, "Attemting to sell " + count + " " + shareName
				+ " shares");
		try {
			getPlayer(playerName).sellShare(priceProvider.getShare(shareName),
					count);
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, "Buying a share failed", e);
			throw e;
		}
		logger.log(Level.INFO, "Player " + playerName + " sold " + count + " "
				+ shareName + " shares");
	}

	@Override
	@AsCommand(command = "sdv", helpText = "<name> * get the value of the players share deposit", feedback = "Value of all Shares: ", currencyFormat = true)
	public long getPlayerDepositValue(String name) {
		return getPlayer(name).getDepositValue();
	}

	@Override
	@AsCommand(command = "scv", helpText = "<name> * show players remaining cash", feedback = "Cash left: ", currencyFormat = true)
	public long getPlayerCashValue(String name) {
		return getPlayer(name).getCashValue();
	}

	@Override
	@AsCommand(command = "sav", helpText = "<name> * show the sum of a players assets", feedback = "Sum of players assets: ", currencyFormat = true)
	public long getPlayerAssetValue(String name) {
		Player p = getPlayer(name);
		return p.getCashValue() + p.getDepositValue();
	}

	@Override
	@AsCommand(command = "ssc", helpText = "<name> <shareName> *  show amount of shares in deposit", feedback = "Number of Shares: ")
	public int getPlayerSharesCount(String name, String shareName) {
		return getPlayer(name).getShareCount(priceProvider.getShare(shareName));
	}

	@Override
	@AsCommand(command = "sps", helpText = "<name> <shareName> * show value of a players collection of shares", feedback = "Value of Shares:", currencyFormat = true)
	public long getPlayerSharesValue(String name, String shareName) {
		return getPlayer(name)
				.getSharesValue(priceProvider.getShare(shareName));
	}

	@Override
	@AsCommand(command = "ssp", helpText = "<name> <shareName> * show profit made by a specific share", feedback = "Current Profit: ", currencyFormat = true)
	public long getPlayerSharesProfit(String name, String shareName) {
		Player p = getPlayer(name);
		Share s = priceProvider.getShare(shareName);
		return p.getSharesValue(s) - p.getSharesBuyValue(s);
	}

	private Player getPlayer(String name) {
		Player player = players.get(name);
		if (player == null)
			throw new UnknownPlayerException(name);
		return player;
	}

	@Override
	@AsCommand(command = "abot", helpText = "<name> <intervall> * add a bot to a player", feedback = "Bot added.")
	public void addBot(String name, long intervall) {
		getPlayer(name).addBot(priceProvider, intervall);
		logger.log(Level.INFO, "Bot added to Player " + name);
	}

	@Override
	@AsCommand(command = "rbot", helpText = "<name> * remove bot from a Player", feedback = "bot removed")
	public void removeBot(String name) {
		getPlayer(name).removeBot();
		logger.log(Level.INFO, "Bot removed from Player " + name);
	}

	@Override
	@AsCommand(command = "his", helpText = "<name> <sortmethod> <MIMEType> <output> * get Players transaction history, methods: time, share, share=<share>, output is either filename or 'here' for console", feedback = "")
	public String getHistory(String name, String sortmethod, String MIMEType, String output) {
		Writer writer;
		boolean unknownSort = false;
		if(output.equals("here")) {
			writer = new PrintWriter(System.out);
		} else {
			try {
				writer = new PrintWriter(new FileOutputStream(output));
			} catch (FileNotFoundException e) {
				return "Clould not open file " + output;
			}
		}
		if(sortmethod.equals("time"))
			getPlayer(name).getHistoryByTime(writer, MIMEType);
		else if(sortmethod.equals("share"))
			getPlayer(name).getHistoryByShares(writer, MIMEType);
		else if(sortmethod.startsWith("share="))
			getPlayer(name).getShareHistory(writer, MIMEType, sortmethod.substring("share=".length()));
		else
			unknownSort = true;
		
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(unknownSort) 
			return "Clould not write file " + output;
		else if(output.equals("here"))
			return "History printet out";
		else
			return "History written into " + output;
	}

	@Override
	public boolean playerExists(String name) {
		return (players.containsKey(name));
	}
}