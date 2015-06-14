package de.hsa.sharegame.gui;

import java.text.NumberFormat;
import java.text.ParseException;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.shares.StockPriceInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ShareInfoRow extends HBox {
	private String shareName;
	private int countInputVal = 0;
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
	
	public ShareInfoRow(StockPriceInfo spp, AccountManager am, String s) {
		this.spp = spp;
		this.am = am;
		
		nameLabel = new Label(s);
		priceLabel = new Label(0 + "");
		countLabel = new Label(0 + "");
		valueLabel = new Label(0 + "");
		profitLabel = new Label(0 + "");
		countInput = new TextField();
		buyButton = new Button("buy");
		sellButton = new Button("sell");
		this.getChildren().addAll(nameLabel, priceLabel, countLabel, valueLabel, profitLabel, countInput, buyButton, sellButton);
		
		//Make sure countInput is always a positive Integer
		countInput.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					int newVal = NumberFormat.getIntegerInstance().parse(countInput.getText()).intValue();
					if(newVal > 0)
						countInputVal = newVal;
					else
						countInput.setText(countInputVal + "");
				} catch (ParseException e) {
					countInput.setText(countInputVal + "");
				}
			}});		
	}
	
	
	
	public String getShareName() {
		return shareName;
	}
	
	public int getCountInput() {
		return countInputVal;
	}
	
	public void setBuyButtonAction(EventHandler<ActionEvent> handler) {
		buyButton.setOnAction(handler);
	}
	
	public void setSellButtonAction(EventHandler<ActionEvent> handler) {
		buyButton.setOnAction(handler);
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
