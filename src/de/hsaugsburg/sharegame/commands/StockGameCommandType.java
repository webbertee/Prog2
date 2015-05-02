package de.hsaugsburg.sharegame.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.hsaugsburg.commands.CommandTypeInfo;

public enum StockGameCommandType implements CommandTypeInfo{
	HELP			("help", "", "  * list all commands"),
	EXIT         	("exit", null,"  * exit program"),
	CREATEPLAYER 	("crp",  "addPlayer","<name> <money> * create a new player by name", 
			String.class, long.class), 
	BUYSHARE     	("buys",  "buyShare","<playername> <sharename> <amount> * buy that amount of shares",
			String.class, String.class, int.class),
	SELLSHARE	 	("sells", "sellShare","<playername> <sharename> <amount> * buy that amount of shares", 
			String.class, String.class, int.class),
	SHOWCASH	 	("sc", "getPlayerCashValue","<playername> * show remaining cash", String.class),
	SHOWASSETVALUE	("sav", "getPlayerAssetValue","<playername> * show asset value", String.class),
	SHOWSHARECOUNT	("ssc", "getPlayerSharesCount","<playername> <sharename> * show amount of shares in deposit", 
			String.class, String.class),
	//SHOWSALLSHARES	("sas", " * show all shares")
	
	;

	private StockGameCommandType(String name,String method, String help, Class<?>... paramTypes) {
		this.method = method;
		this.name = name;
		this.help = help;
		this.paramTypes = paramTypes;
	}
	

	
	private String name;
	private String help;
	private Class<?>[] paramTypes;
	private String method;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHelpText() {
		return help;
	}

	@Override
	public Class<?>[] getParamTypes() {
		return paramTypes;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
