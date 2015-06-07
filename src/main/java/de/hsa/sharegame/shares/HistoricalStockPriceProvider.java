package de.hsa.sharegame.shares;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.shares.exceptions.SharePriceHistoryException;

public class HistoricalStockPriceProvider extends StockPriceProvider {
	private Map<String, Stack<Long>> priceMap;
	private StockPriceProvider backupProvider;
	
	public HistoricalStockPriceProvider(long updaterate) {
		super(new Share[]{});
		backupProvider = new RandomStockPriceProvider(new Share[] {}, updaterate);
		URL url = getClass().getResource("/csv");
		try {
			for(String f: new File(url.toURI()).list()) {
				addShare(new Share(f,100L));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			new SharePriceHistoryException("Could not read in shares");
		}
		super.startUpdate(updaterate, updaterate);
	}

	
	@Override
	public void addShare(Share share)  {
		//priceMap needs to to be constructed here, since addShare is called by the superclass before construction
		if(priceMap == null)
			 priceMap = new HashMap<String, Stack<Long>>();
		
		Stack<Long> priceStack = new Stack<Long>();
		try {
			InputStream in = getClass().getResourceAsStream("/csv/" + share.getName()); 
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			br.readLine(); // Skip first line
			while ((line = br.readLine()) != null) {
				String number = line.split(",")[4].replace(".", "");
				priceStack.push(Long.parseLong(number));
			}
			priceMap.put(share.getName(), priceStack);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SharePriceHistoryException(share.getName());
		}
		super.addShare(share);
	}
	
	@Override
	protected void updateShareRate(Share share) {
		Stack<Long> priceStack = priceMap.get(share.getName());
		if(!priceStack.isEmpty())
			share.setValue(priceMap.get(share.getName()).pop());
		else {
			if(backupProvider != null && !backupProvider.containsShare(share.getName())) {
				backupProvider.addShare(share);
			}
		}
	}
}
