package AccountManagement;

public interface AccountManager {

	/**
	 * 
	 * @param name
	 */
	public void addPlayer(String name);
	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 */
	public void buyShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param playerName
	 * @param shareName
	 * @param count
	 */
	public void sellShare(String playerName, String shareName, int count);

	/**
	 * 
	 * @param name
	 */
	public long getPlayerSharesValue(String name);

	/**
	 * 
	 * @param name
	 */
	public long getPlayerCashValue(String name);

	/**
	 * 
	 * @param name
	 */
	public long getPlayerAssetValue(String name);

	/**
	 * 
	 * @param name
	 */
	public long getShareValue(String name);
	
	public String getAllShares() ;

}