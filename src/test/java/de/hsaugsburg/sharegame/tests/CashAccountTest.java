package de.hsaugsburg.sharegame.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsaugsburg.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsaugsburg.sharegame.assets.CashAccount;

public class CashAccountTest {
	private CashAccount ca;

	@Before
	public void setUp() throws Exception {
		ca = new CashAccount("name", 100);
	}


	@Test(expected=NotEnoughMoneyException.class)
	public void testRemoveException() {
			ca.remove(1000);
	}
	
	@Test
	public void testAdd() {
		ca.add(100);
		assertEquals(ca.getValue(),200);
	}
	
	@Test
	public void testRemove() {
		ca.remove(100);
		assertEquals(ca.getValue(),0);
	}
}
