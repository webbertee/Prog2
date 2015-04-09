package de.hsaugsburg.ShareGame.AccountManagement;

import javax.net.ssl.SSLContext;

import de.hsaugsburg.ShareGame.AccountManagement.Exceptions.NotEnoughMoneyException;
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
		players[playerCount] = new Player(name, cash);
		playerCount++;
	}

	@Override
	public void buyShare(String playerName, String shareName, int count) {
		Player
	}

	@Override
	public void sellShare(String playerName, String shareName, int count) throws NotEnoughMoneyException {

	}

	@Override
	public long getPlayerSharesValue(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getPlayerCashValue(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getPlayerAssetValue(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getShareValue(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getAllShares() {
		StringBuilder out = new StringBuilder();
		for(Share s : shares) {
			out.append(s.getName());
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

}