package de.hsa.sharegame.gui;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.exceptions.PlayerAlreadyExistsException;
import de.hsa.sharegame.commands.StockGameCommandProcessor;
import de.hsa.sharegame.events.ConsoleOutputEvent;
import de.hsa.sharegame.events.ConsoleOutputHandler;
import de.hsa.sharegame.gui.ShareInfoRow.BuySellHandler;
import de.hsa.sharegame.shares.StockPriceInfo;
import de.hsa.sharegame.shares.StockPriceProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainScene extends Scene {
	private static final String NEWLINE = System.getProperty("line.separator");
	private VBox rootVBox;
	private MenuBar menu;
	private HBox playerSelector;
	private VBox shareList;
	private TextArea consoleOutput;
	private TextField consoleInput;
	private App app;
	private StockPriceInfo spp;
	private AccountManager am;
	private StockGameCommandProcessor processor;
	
	private String playerName;
	
	public MainScene(App app,String playerName, StockPriceInfo spp, AccountManager am, StockGameCommandProcessor processor) {
		super(new VBox());
		this.app = app;
		this.playerName = playerName;
		this.spp = spp;
		this.am = am;
		this.processor = processor;
		
		rootVBox = (VBox)super.getRoot();
		menu = createMenu();
		playerSelector =  createPlayerSelector();
		shareList = createShareList();
		consoleOutput = createConsoleOutput();
		consoleInput = createConsoleInput();
		
		//TODO: add hander to Input
		
		rootVBox.getChildren().addAll(/*menu,*/playerSelector,shareList, consoleOutput, consoleInput);
		
	}

	private HBox createPlayerSelector() {
		HBox sel = new HBox();
		sel.setPadding(new Insets(10));
		sel.setSpacing(5);
		Label label = new Label("Set Player Name:");
		TextField in = new TextField(playerName);
		Button setPlayerButton = new Button("Set Player");
		Button createPlayerButton = new Button("Create Player");
		
		EventHandler<ActionEvent> setHandler = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(am.playerExists(in.getText())) {
					playerName = in.getText();
				} else {
					writeMessage("Player does not exist");
					in.setText(playerName);
				}
			}
		};
		
		setPlayerButton.setOnAction(setHandler);
		
		createPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					am.addPlayer(in.getText(), 100000);
					playerName = in.getText();
				} catch(PlayerAlreadyExistsException e) {
					writeMessage("Player already exists");
				}
			}});
		
//		in.focusedProperty().addListener(arg -> {
//			if(!in.isFocused() || !setPlayerButton.isFocused() || !createPlayerButton.isFocused())
//				in.setText(playerName);
//		});
		
		sel.getChildren().addAll(label, in, setPlayerButton, createPlayerButton);
		
		return sel;
	}

	protected void writeMessage(String message) {
		consoleOutput.appendText(message + NEWLINE);
	}

	private TextField createConsoleInput() {
		TextField cin = new TextField();
		cin.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode() == KeyCode.ENTER) {
					processor.readCommand(cin.getText());
					cin.setText("");
				}
				
			}});
		return cin;
	}

	private TextArea createConsoleOutput() {
		TextArea cout = new TextArea();
		cout.setEditable(false);
		processor.addConsoleOutputHandler(new ConsoleOutputHandler() {

			@Override
			public void handle(ConsoleOutputEvent e) {
				cout.appendText(e.getOutput() + NEWLINE);
			}});
		
		return cout;
	}

	private VBox createShareList() {
		VBox newShareList = new VBox();
		newShareList.getChildren().add(ShareInfoRow.getHead());
		ShareInfoRow newRow;
		for(String s : spp.getShareNames()) {
			newRow = new ShareInfoRow(spp,am, s);
			newRow.setBuySellHandler(new BuySellHandlerImpl(am, this, newRow));
			newShareList.getChildren().add(newRow);
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
	
	public String getPlayerName() {
		return playerName;
	}

}

