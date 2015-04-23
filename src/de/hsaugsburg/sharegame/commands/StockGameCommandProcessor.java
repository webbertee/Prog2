package de.hsaugsburg.sharegame.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import de.hsaugsburg.sharegame.accounts.AccountManager;

public class StockGameCommandProcessor {
	BufferedReader shellReader = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter printwriter = new PrintWriter(System.out);
	AccountManager accountManager;
}
