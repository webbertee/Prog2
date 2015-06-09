package de.hsa.sharegame.launchers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import javafx.application.Application;
import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.AccountManagerImpl;
import de.hsa.sharegame.assets.Share;
import de.hsa.sharegame.commands.StockGameCommandProcessor;
import de.hsa.sharegame.gui.StockGUI;
import de.hsa.sharegame.shares.ConstStockPriceProvider;
import de.hsa.sharegame.shares.HistoricalStockPriceProvider;
import de.hsa.sharegame.shares.RandomStockPriceProvider;
import de.hsa.sharegame.shares.StockPriceProvider;

public class App {
	public static void main(String[] args) {
		// Share[] shares = {new Share("SpaceX", 10500), new Share("Boing",
		// 12000),new Share("Siemens", 20000),
		// new Share("SAP", 12000) , new Share("Audi", 10000)};
		//
		
		//Interpret console arguments
		boolean nogui = true;
		for(String s : args) {
			if(s.equals("nogui"))
				nogui = true;
			
			if(s.startsWith("lang=")) {
				Locale.setDefault(new Locale(s.substring("lang=".length())));
			}
		}

		StockPriceProvider spp;
		spp = new HistoricalStockPriceProvider(500);
		AccountManager am = new AccountManagerImpl(spp);
		
		StockGameCommandProcessor processor = new StockGameCommandProcessor(am, spp);
		
		
		if(!nogui) {
			new Thread(() -> consoleInputLoop(processor)).start();
			StockGUI.setUp(am, spp, processor, args);
		} else {
			consoleInputLoop(processor);
		}
	}
	
	public static void consoleInputLoop(StockGameCommandProcessor processor) {
		String line;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			while((line = in.readLine()) != null) {
				System.out.println(processor.readCommand(line));
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
