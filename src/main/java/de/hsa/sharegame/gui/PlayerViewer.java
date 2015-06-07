package de.hsa.sharegame.gui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.hsa.sharegame.accounts.AccountManager;
import de.hsa.sharegame.accounts.exceptions.UnknownPlayerException;
import de.hsa.sharegame.shares.StockPriceInfo;
import de.hsa.sharegame.timer.SingleTimer;

@SuppressWarnings("serial")
public class PlayerViewer extends JFrame {
	private static final int TICK_PERIOD = 1000;
	private Timer ticker;
	private JLabel clockLabel;
	private final String player;
	private final StockPriceInfo pInfo;
	private final AccountManager am;

	public PlayerViewer(String player, AccountManager am, StockPriceInfo pInfo) {
		this.player = player;
		if(!am.playerExists(player)) 
			throw new UnknownPlayerException(player);
		this.am = am;
		this.pInfo = pInfo;
		clockLabel = new JLabel("loading...");
		add("Center", clockLabel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(200, 300);
		setLocation(0,400);
		setVisible(true);

	}

	public void start() {
		ticker = SingleTimer.getTimer();
		ticker.scheduleAtFixedRate(new TickerTask(), 1000, TICK_PERIOD);
	}

	private class TickerTask extends TimerTask {
		public void run() {
			String output = createText();
			clockLabel.setText(output);
			clockLabel.repaint();
		}

		private StringBuffer buffer = new StringBuffer();
		
		private String createText() {
			String symbol = Currency.getInstance(Locale.getDefault()).getSymbol();
			buffer.setLength(0);
			buffer.append("<html><body>Player " + player + "<br><br>");
			// ----Adding Table----
			buffer.append("<table><tr><th>Stock</th><th>Amount</th></tr>");
			String[] shareNames = pInfo.getShareNames();
			for (String s : shareNames) {
				buffer.append("<tr><td>");
				buffer.append(s);
				buffer.append("</td><td>");
				buffer.append(am.getPlayerSharesCount(player, s));
				buffer.append("</td></tr>");
			}
			buffer.append("</table>");
			buffer.append("Cash: " + NumberFormat.getCurrencyInstance().format(am.getPlayerCashValue(player)/100.0) + " " + symbol + "<br>");
			buffer.append("Deposit value: " + NumberFormat.getCurrencyInstance().format(am.getPlayerDepositValue(player)/100.0) + " " + symbol);
			// --------------------
			buffer.append("</body></html>");
			return buffer.toString();
		}
	}
}