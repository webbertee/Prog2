package de.hsa.sharegame.launchers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.AccountManagerImpl;
import de.hsa.sharegame.accounts.exceptions.NotEnoughMoneyException;
import de.hsa.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.shares.ConstStockPriceProvider;
import de.hsa.sharegame.shares.StockPriceProvider;

public class RegTest {
	private StockPriceProvider spp;
	private AccountManager am;

	@Before
	public void setUp() throws Exception {
		spp = new ConstStockPriceProvider(
				new Share[] { new Share("Audi", 100) });
		am = new AccountManagerImpl(spp);
		am.addPlayer("player", 1000);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuyShare() {
		am.buyShare("player", "Audi", 1);
		assertEquals(am.getPlayerSharesCount("player", "Audi"), 1);
	}

	@Test
	public void testBuyShareException() {
		try {
			am.buyShare("player", "Audi", 100);
			fail();
		} catch (NotEnoughMoneyException e) {

		}
		assertEquals(am.getPlayerSharesCount("player", "Audi"), 0);
	}

	@Test
	public void testSellShare() {
		am.buyShare("player", "Audi", 1);
		am.sellShare("player", "Audi", 1);
		assertEquals(am.getPlayerSharesCount("player", "Audi"), 0);
	}

	public void testSellShareExceptions() {
		try {
			am.sellShare("player", "Audi", 10);
			fail();
		} catch (IllegalStateException e) {

		}
	}
	
	public void testAddPlayerException() {
		try {
			am.addPlayer("player", 1000);
			fail();
		} catch (PlayerAlreadyExistsException e) {
			
		}
	}

}
