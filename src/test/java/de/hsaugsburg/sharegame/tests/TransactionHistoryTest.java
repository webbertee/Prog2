package de.hsaugsburg.sharegame.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsa.sharegame.transactions.TransactionHistory;
import de.hsa.sharegame.transactions.Transaction.Type;

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
	public void testListByTime() {
		th.addTransaction(Type.CREDIT, 1000L,"Btest",1, "test2", new Date());
		th.addTransaction(Type.CREDIT, 1000L,"Atest",1, "test2", new Date());
		
	
	
	}

}
