package de.hsa.sharegame.transactions.formaters;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.text.DateFormat;
import java.text.NumberFormat;

import de.hsa.sharegame.transactions.Transaction;
import de.hsa.sharegame.transactions.TransactionFormater;
import de.hsa.sharegame.transactions.Transaction.Type;

public class HTMLFormater extends TransactionFormater {
	StringBuffer out = new StringBuffer();

	@Override
	public String format(Transaction t) {
		String share = 		  escapeHtml4(t.getShare());
		String participant =  escapeHtml4(t.getParticipant());
		String owner =        escapeHtml4(t.getOwner());
		String date = 		  escapeHtml4(DateFormat.getDateInstance().format(t.getDate()));
		String amount = 	  escapeHtml4(NumberFormat.getCurrencyInstance().format(
								t.getAmount() / 100.0));

		out.setLength(0);
		out.append("<tr><td>" + date + "</td>");
		if (t.getType() == Type.CREDIT) {
			out.append("<td>" + participant + "</td><td<" + owner + "</td>");
		} else {
			out.append("<td>" + owner + "</td><td>" + participant + "</td>");
		}

		out.append("<td>" + amount + "<td>");
		out.append("<td>" + t.getCount() + "<td>");
		out.append("<td>" + share + "<td></tr>");
		out.append(System.getProperty("line.separator"));

		return out.toString();
	}

	@Override
	public String preFormat() {
		return "<table><tr><th>Date Of Transaction</th><th>Giver</th><th>Receiver/th>"
				+ "<th>Amount Of Money</th><th>Number Of Shares</th><th>Name of Share</th></tr>";
	}

	@Override
	public String postFormat() {
		return "</table>";
	}
}
