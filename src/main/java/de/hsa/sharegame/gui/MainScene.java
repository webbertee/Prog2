package de.hsa.sharegame.gui;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.commands.StockGameCommandProcessor;
import de.hsa.sharegame.shares.StockPriceInfo;
import de.hsa.sharegame.shares.StockPriceProvider;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainScene extends Scene {
	private VBox rootVBox;
	private MenuBar menu;
	private VBox shareList;
	private TextArea consoleOutput;
	private TextField consoleInput;
	private App app;
	private StockPriceInfo spp;
	private AccountManager am;
	private StockGameCommandProcessor processor;
	
	public MainScene(App app, StockPriceInfo spp, AccountManager am, StockGameCommandProcessor processor) {
		super(new VBox());
		this.app = app;
		this.spp = spp;
		this.am = am;
		this.processor = processor;
		
		rootVBox = (VBox)super.getRoot();
		menu = createMenu();
		shareList = createShareList();
		consoleOutput = new TextArea();
		consoleOutput.setEditable(false);
		consoleInput = new TextField();
		
		//TODO: add hander to Input
		
		rootVBox.getChildren().addAll(menu,shareList, consoleOutput, consoleInput);
		
	}

	private VBox createShareList() {
		VBox newShareList = new VBox();
		ShareInfoRow newRow;
		for(String s : spp.getShareNames()) {
			newRow = new ShareInfoRow(spp,am, s);
			newShareList.getChildren().add(newRow);
			//TODO:
//			newRow.setBuyButtonAction(handler);
//			newRow.setSellButtonAction(handler);
		}
		return newShareList;
	}

	private MenuBar createMenu() {
	   MenuItem newGameItem = new MenuItem("New Game");
	   MenuItem newWindowItem = new MenuItem("New Window");
	   MenuItem exitItem = new MenuItem("Exit");
	   Menu gameMenu = new Menu("Game");
	   gameMenu.getItems().addAll(newGameItem, newWindowItem, exitItem);
	   
	   //TODO: Add Listeners

	   MenuItem helpItem = new MenuItem("Show Help");
	   Menu helpMenu = new Menu("Help");
	   helpMenu.getItems().addAll(helpMenu);
	   
	   //TODO: Add Listeners
	        
	   MenuBar menuBar = new MenuBar();
	   menuBar.getMenus().addAll(gameMenu, helpMenu);
	   return menuBar;

	}
}
