package de.hsaugsburg.sharegame.tests.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.assets.ShareItem;

public class ShareItemTest {
	private ShareItem si;

	@Before
	public void setUp() throws Exception {
		Share audi = new Share("Audi", 10);
		si = new ShareItem(audi, "test");
	}

	@Test
	public void testGetValue() {
		si.add(1);
		assertEquals(si.getValue(),10);
	}

	@Test
	public void testGetCount() {
		si.add(1);
		assertEquals(si.getCount(),1);
	}

	@Test
	public void testGetBuyValue() {
		si.add(1);
		assertEquals(si.getBuyValue(),10);
	}

	@Test(expected=IllegalStateException.class)
	public void testRemove() {
		si.remove(10);
	}

}
