package de.hsaugsburg.sharegame.viewer;

import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.hsaugsburg.sharegame.shares.StockPriceInfo;
import de.hsaugsburg.sharegame.shares.exceptions.UnknownShareException;
import de.hsaugsburg.sharegame.timer.SingleTimer;

@SuppressWarnings("serial")
public class Viewer extends JFrame {
	private static final int TICK_PERIOD = 1000;
	private Timer ticker;
	private JLabel clockLabel;
	private StockPriceInfo priceInfo;

	public Viewer(StockPriceInfo priceInfo) {
		this.priceInfo = priceInfo;
		clockLabel = new JLabel("loading...");
		add("Center", clockLabel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(200, 300);
		setLocation(200,200);
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
		private DecimalFormat df = new DecimalFormat("#.00");
		
		private String createText() {
			buffer.setLength(0);
			buffer.append("<html><body>The current share prices<br><br>");
			// ----Adding Table----
			buffer.append("<table><tr><th>Stock</th><th>Price in €</th></tr>");
			String[] shareNames = priceInfo.getShareNames();
			for (String s : shareNames) {
				buffer.append("<tr><td>");
				buffer.append(s);
				buffer.append("</td><td>");
				try {
					buffer.append(df.format(priceInfo.getShareValue(s)/100.0));
				} catch (UnknownShareException e) {
					//this should not happen
					e.printStackTrace();
				}
				buffer.append("</td></tr>");
			}
			buffer.append("</table>");
			// --------------------
			buffer.append("</body></html>");
			return buffer.toString();
		}
	}
}
