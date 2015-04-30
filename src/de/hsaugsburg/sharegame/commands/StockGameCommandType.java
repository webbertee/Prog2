package de.hsaugsburg.sharegame.commands;

import de.hsaugsburg.commands.CommandTypeInfo;

public enum StockGameCommandType implements CommandTypeInfo{
	HELP         ("help", "  * list all commands"),
	EXIT         ("exit", "  * exit program"),
	CREATEPLAYER ("crp",  "<name> <money> * create a new player by name", String.class, long.class), 
	BUYSHARE     ("buys",  "<playername> <sharename> <amount> * buy that amount of shares", String.class, String.class, int.class),
	SELLSHARE	 ("sells", "<playername> <sharename> <amount> * buy that amount of shares", String.class, String.class, int.class),
	SHOWCASH	 ("sc", "<playername> * show remaining cash", String.class),
	SHOWASSETVALUE("sav", "<playername> * show asset value", String.class),
	SHOWSHARECOUNT("ssc", "<playername> <sharename> * show amount of shares in deposit", String.class, String.class),
	SHOWSALLSHARES("sas", " * show all shares");

	private StockGameCommandType(String name, String help, Class<?>... args) {
		this.name = name;
		this.help = help;
		this.args = args;
	}
	
	
	private String name;
	private String help;
	private Class<?>[] args;
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
		return args;
	}

}
