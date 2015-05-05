package de.hsaugsburg.sharegame.commands;


import de.hsaugsburg.commands.CommandTypeInfo;

public enum StockGameCommandType implements CommandTypeInfo{
	HELP			("help", 
					"  * list all commands",
					"Available Commands: ",
					ExeResult.HELP,
					null),
	EXIT         	("exit",
					"  * exit program",
					"exiting...",
					ExeResult.EXIT,
					null),
	CREATEPLAYER 	("crp",
					"<name> <money> * create a new player by name",
					"Player created.",
					ExeResult.SUCCESS,
					"addPlayer", 
					String.class, long.class), 
	BUYSHARE     	("buys",
					"<playername> <sharename> <amount> * buy that amount of shares",
					"Shares successfully bought",
					ExeResult.SUCCESS,
					"buyShare",
					String.class, String.class, int.class),
	SELLSHARE	 	("sells",
					"<playername> <sharename> <amount> * buy that amount of shares",
					"Shares successfully sold",
					ExeResult.SUCCESS,
					"sellShare", 
					String.class, String.class, int.class),
	SHOWCASH	 	("sc", 
					"<playername> * show remaining cash", 
					"Avalable cash: ",
					ExeResult.SUCCESS,
					"getPlayerCashValue",
					String.class),
	SHOWASSETVALUE	("sav", 
					"<playername> * show asset value",
					"Value of assets: ",
					ExeResult.SUCCESS,
					"getPlayerAssetValue", 
					String.class),
	SHOWSHARECOUNT	("ssc",
					"<playername> <sharename> * show amount of shares in deposit", 
					"Number of Shares: ",
					ExeResult.SUCCESS,
					"getPlayerSharesCount",
					String.class, String.class),
	PLAYESHARESRPROFIT("spf",
					"<playername> <sharename> * show profit made by a specific share",
					"Profit made: ",
					ExeResult.SUCCESS,
					"getPlayerSharesProfit", 
					String.class, String.class),
					
	ADDBOT			("abot",
					"<playername>, <intervall>",
					"Added bot to player",
					ExeResult.SUCCESS,
					"addBot",
					String.class, long.class),
	REMOVEBOT		("rbot",
					"<playername>",
					"removed bot from player",
					ExeResult.SUCCESS,
					"removeBot",
					String.class)
	//SHOWSALLSHARES	("sas", " * show all shares")
	
	;
	


	private StockGameCommandType(String name, String help,String message, ExeResult exeResult, String method,Class<?>... paramTypes) {
		this.name = name;
		this.help = help;
		this.message = message;
		this.exeResult = exeResult;
		this.method = method;
		this.paramTypes = paramTypes;
	}
	
	
	private String name;
	private String help;
	private Class<?>[] paramTypes;
	private String method;
	private String message;
	private ExeResult exeResult;
	
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

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public ExeResult getExeResult() {
		return exeResult;
	}

}
