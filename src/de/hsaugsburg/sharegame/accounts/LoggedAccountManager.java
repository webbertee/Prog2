package de.hsaugsburg.sharegame.accounts;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsaugsburg.commands.AsCommand;
import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;

public class LoggedAccountManager extends AccountManagerImpl{
private Logger logger;
	
	public LoggedAccountManager(StockPriceProvider priceProvider) {
		super(priceProvider);
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	@Override
	@AsCommand(command = "buys", helpText = "<playername> <sharename> <count> * buy a amout of shares", feedback = "shares successfully purchased")
	public void buyShare(String playerName, String shareName, int count) throws NotEnoughMoneyException {
		logger.log(Level.FINEST, "Attemting to buy " + count + " " + shareName + " shares for " + playerName);
		try {
			super.buyShare(playerName, shareName, count);
		} catch(RuntimeException e) {
			logger.log(Level.WARNING, "Buying a share failed", e);
			throw e;
		}
		logger.log(Level.INFO, "Player " + playerName + " bought " + count + " " + shareName + " shares");
	}

}
