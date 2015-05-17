package de.hsaugsburg.sharegame.accounts;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsaugsburg.commands.AsCommand;
import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsaugsburg.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;

public class AccountManagerImpl implements AccountManager {

	private Map<String, Player> players;
	private StockPriceProvider priceProvider;
	private final String CFORMAT = "#0.00€";
	private final Logger logger = Logger.getLogger(AccountManagerImpl.class
			.getName());

	public AccountManagerImpl(StockPriceProvider priceProvider) {
		players = new HashMap<String, Player>();
		this.priceProvider = priceProvider;
	}

	@Override
	@AsCommand(command = "crp", helpText = "<name> <money> * create a new player by name", feedback = "Player created")
	public void addPlayer(String name, long cash)
			throws PlayerAlreadyExistsException {
		if (players.get(name) != null)
			throw new PlayerAlreadyExistsException(name);

		players.put(name, new Player(name, cash));
	}

	@Override
	@AsCommand(command = "buys", helpText = "<playername> <sharename> <count> * buy a amout of shares", feedback = "shares successfully purchased")
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
	@AsCommand(command = "sells", helpText = "<playername> <sharename> <amount> * sell a amount of shares", feedback = "Shares successfully sold")
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
	@AsCommand(command = "sdv", helpText = "<name> * get the value of the players share deposit", feedback = "Value of all Shares: ", currencyFormat = CFORMAT)
	public long getPlayerDepositValue(String name) {
		return getPlayer(name).getDepositValue();
	}

	@Override
	@AsCommand(command = "scv", helpText = "<name> * show players remaining cash", feedback = "Cash left: ", currencyFormat = CFORMAT)
	public long getPlayerCashValue(String name) {
		return getPlayer(name).getCashValue();
	}

	@Override
	@AsCommand(command = "sav", helpText = "<name> * show the sum of a players assets", feedback = "Sum of players assets: ", currencyFormat = CFORMAT)
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
	@AsCommand(command = "sps", helpText = "<name> <shareName> * show value of a players collection of shares", feedback = "Value of Shares:", currencyFormat = CFORMAT)
	public long getPlayerSharesValue(String name, String shareName) {
		return getPlayer(name)
				.getSharesValue(priceProvider.getShare(shareName));
	}

	@Override
	@AsCommand(command = "ssp", helpText = "<name> <shareName> * show profit made by a specific share", feedback = "Current Profit: ", currencyFormat = CFORMAT)
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
	@AsCommand(command = "his", helpText = "<name> <sortmethod> * get Players transaction history, methods: time, share, share=<share>", feedback = "")
	public String getHistory(String name, String sortmethod) {
		if(sortmethod.equals("time"))
			return getPlayer(name).getHistoryByTime();
		else if(sortmethod.equals("share"))
			return getPlayer(name).getHistoryByShares();
		else if(sortmethod.startsWith("share="))
			return getPlayer(name).getShareHistory(sortmethod.substring("share=".length()));
		else
			return sortmethod + " is a unknown sort method";
	}

	@Override
	public boolean playerExists(String name) {
		return (players.containsKey(name));
	}
}