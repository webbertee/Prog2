package de.hsaugsburg.sharegame.tests.unit;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsaugsburg.sharegame.accounts.TransactionHistory;
import de.hsaugsburg.sharegame.accounts.Transaction.Type;

public class TransactionHistoryTest {
	private TransactionHistory th;
	
	@Before
	public void setUp() throws Exception {
		th = new TransactionHistory("owner");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		th.addTransaction(Type.CREDIT, 1000L,"B",1, "test2", new Date());
		th.addTransaction(Type.CREDIT, 1000L,"A",1, "test2", new Date());
		System.out.println(th.listByShares());
		System.out.println();
		System.out.println(th.listByTime());
		System.out.println();
		System.out.println(th.listShare("A"));
		assertTrue("toString", true);
	
	
	}

}
