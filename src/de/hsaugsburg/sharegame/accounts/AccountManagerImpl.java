package de.hsaugsburg.sharegame.accounts;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsaugsburg.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;

public class AccountManagerImpl implements AccountManager {

	private Player[] players;
	private final int SIZE = 32;
	private int playerCount = 0;
	private StockPriceProvider priceProvider;
	
	public AccountManagerImpl(StockPriceProvider priceProvider) {
		players = new Player[SIZE];
		this.priceProvider = priceProvider;
	}
	
	
	@Override
	public void addPlayer(String name, long cash) throws PlayerAlreadyExistsException {
		if(getPlayerIndex(name) >= 0)
			throw new PlayerAlreadyExistsException(name);
		
		players[playerCount] = new Player(name, cash);
		playerCount++;
	}

	@Override
	public void buyShare(String playerName, String shareName, int count) throws NotEnoughMoneyException {
		getPlayer(playerName).buyShare(priceProvider.getShare(shareName), count);
	}

	@Override
	public void sellShare(String playerName, String shareName, int count)  {
		getPlayer(playerName).sellShare(priceProvider.getShare(shareName), count);
	}

	@Override
	public long getPlayerDepositValue(String name) {
		return getPlayer(name).getDepositValue();
	}

	@Override
	public long getPlayerCashValue(String name) {
		return getPlayer(name).getCashValue();
	}

	@Override
	public long getPlayerAssetValue(String name) {
		Player p = getPlayer(name);
		return p.getCashValue() + p.getDepositValue();
	}

	@Override
	public int getPlayerSharesCount(String name, String shareName) {
		return getPlayer(name).getShareCount(priceProvider.getShare(shareName));
	}


	@Override
	public long getPlayerSharesValue(String name, String shareName) {
		return getPlayer(name).getSharesValue(priceProvider.getShare(shareName));
	}
	
	@Override
	public long getPlayerSharesProfit(String name, String shareName) {
		Player p = getPlayer(name);
		Share s = priceProvider.getShare(shareName);
		return p.getSharesValue(s) - p.getSharesBuyValue(s);
	}	
	
	private int getPlayerIndex(String name) {
		for(int i = 0; i < playerCount; i++) {
			if(players[i].getName().equals(name)) 
				return i;
		}
		return -1;
	}
	
	private Player getPlayer(String name) {
		int playerI = getPlayerIndex(name);
		if(playerI < 0)
			throw new UnknownPlayerException(name);
		return players[playerI];
	}


	@Override
	public void addBot(String name, long intervall) {
		getPlayer(name).addBot(priceProvider, intervall);
	}


	@Override
	public void removeBot(String name) {
		getPlayer(name).removeBot();
	}

	
}