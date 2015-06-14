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
import de.hsa.sharegame.gui.App;
import de.hsa.sharegame.shares.ConstStockPriceProvider;
import de.hsa.sharegame.shares.HistoricalStockPriceProvider;
import de.hsa.sharegame.shares.RandomStockPriceProvider;
import de.hsa.sharegame.shares.StockPriceProvider;

public class ConsoleApp {
	
	public static void main(String[] args) {
		HistoricalStockPriceProvider spp = new HistoricalStockPriceProvider(1000);
		AccountManagerImpl am = new AccountManagerImpl(spp);
		//start console input loop
		consoleInputLoop(new StockGameCommandProcessor(am, spp));
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
