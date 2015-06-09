package de.hsaugsburg.sharegame.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.assets.ShareDepositAccount;
import de.hsa.sharegame.assets.exceptions.NotEnoughSharesException;

public class ShareDepositAccountTest {
	private ShareDepositAccount sda;
	private Share audi;

	@Before
	public void setUp() throws Exception {
		sda = new ShareDepositAccount("test");
		audi = new Share("Audi",10);
	}

	
	
	@Test
	public void getValue() {
		sda.addShare(audi, 2);
		assertEquals(sda.getValue(),20);
	}

	@Test
	public void testGetBuyValue() {
		sda.addShare(audi, 1);
		assertEquals(sda.getSharesBuyValue(audi), 10);
	}

	@Test
	public void testAddShare() {
		sda.addShare(audi, 1);
		assertEquals(sda.getSharesCount(audi), 1);
	}

	@Test
	public void testRemoveShare() {
		sda.addShare(audi, 1);
		sda.removeShare(audi, 1);
		assertEquals(sda.getSharesCount(audi), 0);
	}
	
	@Test(expected=NotEnoughSharesException.class)
	public void testRemoveShareException() {
		sda.removeShare(audi, 1);

	}

	@Test
	public void testGetSharesBuyValue() {
		sda.addShare(audi, 1);
		assertEquals(sda.getBuyValue(), 10);
	}

}
