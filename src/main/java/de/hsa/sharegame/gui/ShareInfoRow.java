package de.hsa.sharegame.gui;

import java.text.NumberFormat;
import java.text.ParseException;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.shares.StockPriceInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class ShareInfoRow extends HBox {
	public interface BuySellHandler {
		void buy();
		void sell();
	}
	
	private static final int NAME_W = 80;
	private static final int NUM_W = 40;
	private final static int BOX_W = 30;
	private final static int SPACE = 5;

	private String shareName;
	private int countInputVal = 1;
	private StockPriceInfo spp;
	private AccountManager am;
	
	//Forms
	private Label nameLabel;
	private Label priceLabel;
	private Label countLabel;
	private Label valueLabel;
	private Label profitLabel;
	private TextField countInput;
	private Button buyButton;
	private Button sellButton;
	
	public ShareInfoRow(StockPriceInfo spp, AccountManager am, String shareName) {
		this.spp = spp;
		this.am = am;
		this.shareName = shareName;
		
		this.setPadding(new Insets(SPACE));
		this.setSpacing(SPACE);
		nameLabel = new Label(shareName);
		nameLabel.setMinWidth(NAME_W);
		priceLabel = new Label(0 + "");
		priceLabel.setMinWidth(NUM_W);
		countLabel = new Label(0 + "");
		countLabel.setMinWidth(NUM_W);
		valueLabel = new Label(0 + "");
		valueLabel.setMinWidth(NUM_W);
		profitLabel = new Label(0 + "");
		profitLabel.setMinWidth(NUM_W);
		countInput = new TextField(countInputVal + "");
		countInput.setMaxWidth(BOX_W);
		buyButton = new Button("buy");
		sellButton = new Button("sell");
		this.getChildren().addAll(nameLabel, priceLabel, countLabel, valueLabel, profitLabel, countInput, buyButton, sellButton);
		
		//Make sure countInput is always a positive Integer
		countInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
			
				@Override
				public void handle(KeyEvent arg0) {
					try {
						int newVal = Integer.parseInt(countInput.getText());
					if(newVal > 0) {
						countInputVal = newVal;
					} else {
						countInput.setText(countInputVal + "");
					}
				} catch (NumberFormatException e) {
					countInput.setText(countInputVal + "");
				}
			}});	
	}
	

	public static HBox getHead() {
		HBox box = new HBox();
		Label nameL = new Label("Share Name");
		nameL.setMinWidth(NAME_W);
		Label[] nums = new Label[] {new Label("price"), new Label("count"), new Label("value"), new Label("profit")};
		for(Label l : nums)
			l.setMinWidth(NUM_W);
		
		box.getChildren().add(nameL);
		box.getChildren().addAll(nums);
		box.setPadding(new Insets(SPACE));
		box.setSpacing(SPACE);
		return box;
	}
	
	public String getShareName() {
		return shareName;
	}
	
	public int getCountInput() {
		return countInputVal;
	}
	
//	public void setBuyButtonAction(EventHandler<ActionEvent> handler) {
//		buyButton.setOnAction(handler);
//	}
//	
//	public void setSellButtonAction(EventHandler<ActionEvent> handler) {
//		buyButton.setOnAction(handler);
//	}
//}
	public void setBuySellHandler(BuySellHandler handler) {
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				handler.buy();
			}});
		
	sellButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				handler.sell();
			}});
	}
	
	public void setButtonsDisabled(boolean bool) {
		buyButton.setDisable(bool);
		sellButton.setDisable(bool);
	}
	
	public void updateShare(String playerName) {
		priceLabel.setText(NumberFormat.getCurrencyInstance().format(spp.getShareValue(shareName)/100));
		updateValue(playerName);
	}
	
	public void updateAccount(String playerName) {
		countLabel.setText(am.getPlayerSharesCount(playerName, shareName) + "");
		profitLabel.setText(NumberFormat.getCurrencyInstance().format(am.getPlayerSharesProfit(playerName, shareName)));	
		updateValue(playerName);
	}
	
	private void updateValue(String playerName) {
		valueLabel.setText(NumberFormat.getCurrencyInstance().format((
				spp.getShareValue(shareName)/100) * am.getPlayerSharesCount(playerName, shareName)));
	}
}
