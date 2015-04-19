package de.hsaugsburg.sharegame.tests;

//import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
//import de.hsaugsburg.sharegame.accounts.exceptions.PlayerAlreadyExistsException;

public class AccountManagementTest {
	/*
	public static void main(String[] args) {
		AccountManager am = new AccountManagerImpl();
		am.addPlayer("Alice", 100000);
		am.addPlayer("Bob", 200000);
		System.out.println("Die Audiaktie steht bei "+ am.getShareValue("Audi"));
		System.out.println(am.getAllShares());
		try {
			am.buyShare("Alice", "Audi", 10);
			System.out.println("Alice bought 10 shares.");
		} catch (NotEnoughMoneyException e) {
			System.out.println("Alice doesn't have enough money");
		}
		System.out.println("Alice has now: " + am.getPlayerCashValue("Alice") + " in cash and " 
				+ am.getPlayerSharesValue("Alice") + " in shares.");
		
		am.sellShare("Alice", "Audi", 1);
		System.out.println("Alice sold on share");
		
		System.out.println("Alice has now: " + am.getPlayerCashValue("Alice") + " in cash and " 
				+ am.getPlayerSharesValue("Alice") + " in shares.");
		
		//----Exceptions----
		//--Add a Player twice--
		try {
			am.addPlayer("Bob", 0);
		} catch (PlayerAlreadyExistsException e) { //unchecked
			System.out.println("Couldn't add Bob");
		}
		
		//--Sell shares you don't have--
		//am.sellShare("Alice", "Tesla", 1);
		
		//--Sell more Shares Than you have--
		//am.sellShare("Alice", "Audi", 11);
		
		//--Buy more shares than you can afford--
//		try {
//			am.buyShare("Alice", "Tesla", 100000);
//		} catch (NotEnoughMoneyException e) {		//checked
//			e.printStackTrace();
//		}
	}
		*/
}
