package de.hsaugsburg.ShareGame.AccountManagement;

import de.hsaugsburg.ShareGame.AccountManagement.Exceptions.NotEnoughMoneyException;
import de.hsaugsburg.ShareGame.AccountManagement.Exceptions.PlayerAlreadyExistsException;
import de.hsaugsburg.ShareGame.AccountManagement.Exceptions.UnknownPlayerException;
import de.hsaugsburg.ShareGame.AccountManagement.Exceptions.UnknownShareException;
import de.hsaugsburg.ShareGame.Assets.Share;

public class AccountManagerImpl implements AccountManager {

	private Player[] players;
	private final int SIZE = 32;
	private int playerCount = 0;
	private final Share[] shares;
	public AccountManagerImpl() {
		players = new Player[SIZE];
		shares = new Share[3];
		shares[0] = new Share("Audi", 1000);
		shares[1] = new Share("Tesla", 1500);
		shares[2] = new Share("GM", 2000);
	}
	
	
	@Override
	public void addPlayer(String name, long cash) {
		if(getPlayerIndex(name) >= 0)
			throw new PlayerAlreadyExistsException();
		
		players[playerCount] = new Player(name, cash);
		playerCount++;
	}

	@Override
	public void buyShare(String playerName, String shareName, int count) throws NotEnoughMoneyException {
		getPlayer(playerName).buyShare(getShare(shareName), count);
	}

	@Override
	public void sellShare(String playerName, String shareName, int count) {
		getPlayer(playerName).sellShare(getShare(shareName), count);
	}

	@Override
	public long getPlayerSharesValue(String name) {
		return getPlayer(name).getShareValue();
	}

	@Override
	public long getPlayerCashValue(String name) {
		return getPlayer(name).getCashValue();
	}

	@Override
	public long getPlayerAssetValue(String name) {
		Player p = getPlayer(name);
		return p.getCashValue() + p.getShareValue();
	}

	@Override
	public long getShareValue(String name) {
		return getShare(name).getValue();
	}

	@Override
	public String getAllShares() {
		StringBuilder out = new StringBuilder();
		for(int i = 0; i < shares.length; i++) {
			out.append(shares[i].getName() + " " + shares[i].getValue());
			out.append(", ");
		}
		return out.toString();
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
	
	private int getShareIndex(String share) {
		for(int i = 0; i < shares.length; i++) {
			if(shares[i].getName().equals(share))
				return i;
		}
		return -1;
	}
	
	private Share getShare(String name) {
		int shareI = getShareIndex(name);
		if(shareI < 0)
			throw new UnknownShareException(name);
		return shares[shareI];	
	}
}