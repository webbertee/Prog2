package de.hsa.sharegame.gui;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.assets.exceptions.NotEnoughMoneyException;
import de.hsa.sharegame.assets.exceptions.NotEnoughSharesException;

public class BuySellHandlerImpl implements ShareInfoRow.BuySellHandler {

	private AccountManager am;
	private MainScene main;
	private ShareInfoRow row;

	public BuySellHandlerImpl(AccountManager am, MainScene main,
			ShareInfoRow row) {
		super();
		this.am = am;
		this.main = main;
		this.row = row;
	}

	@Override
	public void buy() {
		try {
			am.buyShare(main.getPlayerName(), row.getShareName(),
					row.getCountInput());
		} catch (NotEnoughMoneyException e) {
			main.writeMessage("Not enough money");
			// TODO: i18n
		}
	}

	@Override
	public void sell() {
		try {
			am.sellShare(main.getPlayerName(), row.getShareName(),
					row.getCountInput());
		} catch (NotEnoughSharesException e) {
			main.writeMessage("Not enough shares");
			// TODO: i18n
		}
	}

}
