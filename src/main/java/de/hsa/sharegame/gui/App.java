package de.hsa.sharegame.gui;


import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.AccountManagerImpl;
import de.hsa.sharegame.commands.StockGameCommandProcessor;
import de.hsa.sharegame.events.ConsoleOutputEvent;
import de.hsa.sharegame.events.ConsoleOutputHandler;
import de.hsa.sharegame.launchers.ConsoleApp;
import de.hsa.sharegame.shares.HistoricalStockPriceProvider;
import de.hsa.sharegame.shares.StockPriceProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class App extends Application {
	private AccountManager am;
	private StockPriceProvider spp;
	private StockGameCommandProcessor processor;
	
    
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void init() {
		spp = new HistoricalStockPriceProvider(1000);
		am = new AccountManagerImpl(spp);
		processor = new StockGameCommandProcessor(am, spp);
		
		//Start reading input from System.in:
		
		(new Thread() {
		      @Override 
		      public void run() {
		    	  ConsoleApp.consoleInputLoop(processor);
		      };
		   }).start();
	}
	
    @Override
    public void start(Stage primaryStage) {
    	//Only render when gui is enabled
    	if(this.getParameters().getRaw().contains("nogui"))
    		return;
    	//TODO: PopUp-Thingy
    	am.addPlayer("player", 100000);
        primaryStage.setScene(new MainScene(this, "player",spp, am, processor));
        primaryStage.show();
    }
    
//	private Scene createMainScene() {
//        GridPane gridPane = new GridPane();
//        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.setPadding(new Insets(25, 25, 25, 25));
//        
//       
//        consoleOutput = new TextArea("adf");
//        gridPane.add(consoleOutput, 1, 1);
//        textField = new TextField("commands here...");
//        gridPane.add(textField,1,2);
//        HBox hBox = new HBox(10);
//        hBox.setAlignment(Pos.BOTTOM_RIGHT);
//        final Button button = new Button("Increment");
//        hBox.getChildren().add(button);
//        gridPane.add(hBox,2,2);
//        
//        //Events
//        
//        //Button
//        EventHandler<ActionEvent> inputHandler = new EventHandler<ActionEvent>() {
//        	@Override
//			public void handle(ActionEvent arg0) {
//        		processor.readCommand(textField.getText());
//        		textField.setText("");
//			}};
//			
//        button.setOnAction(inputHandler);
//        //---------
//        
//        //Console output
//        ConsoleOutputHandler out = new ConsoleOutputHandler() {
//
//			@Override
//			public void handle(ConsoleOutputEvent e) {
//				Platform.runLater(new Runnable() {
//
//					@Override
//					public void run() {
//						consoleOutput.appendText(e.getOutput() + System.getProperty("line.separator"));
//					}});
//			}
//		};
//		
//		processor.addConsoleOutputHandler(out);
//        
//		//turn on Autoscroll
//		consoleOutput.textProperty().addListener(new ChangeListener<Object>() {
//		    @Override
//		    public void changed(ObservableValue<? extends Object> observable, Object oldValue,
//		            Object newValue) {
//		        consoleOutput.setScrollTop(Double.MAX_VALUE);
//		    }
//		});
//		//---------------
//		
//        Scene scene = new Scene(gridPane, 300, 275);
//		return null;
//	}
}