package de.hsaugsburg.launchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsaugsburg.sharegame.accounts.AccountManager;
import de.hsaugsburg.sharegame.accounts.AccountManagerImpl;
import de.hsaugsburg.sharegame.assets.Share;
import de.hsaugsburg.sharegame.shares.ConstStockPriceProvider;
import de.hsaugsburg.sharegame.shares.StockPriceProvider;

public class ProxyLoggingTest {
	public static void main(String[] args) {
		// use the general Proxy class and an InvocationHandler based on reflection
		
		//Set up the AccountManager
		StockPriceProvider spp = new ConstStockPriceProvider(new Share[]{new Share("Audi", 100)});
		AccountManager am = new AccountManagerImpl(spp);
		
        DebugHandler handler = new DebugHandler(am);
        AccountManager amProxy = (AccountManager) Proxy.newProxyInstance(AccountManager.class.getClassLoader(),
        		new Class[] { AccountManager.class }, handler);
        
        
        amProxy.addPlayer("player", 10000000);
        amProxy.buyShare("player", "Audi", 1);
        amProxy.buyShare("player", "Audi", 1000);
	}
}

class DebugHandler implements InvocationHandler  {
    private AccountManager am;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    public DebugHandler(AccountManager am)  { this.am = am; }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  {
       StringBuffer out = new StringBuffer(method.getName() + " with params ");
        for (int i = 0; i < args.length; i++) 
            out.append(" " + args[i]);
        logger.log(Level.FINE,  "calling method " + out.toString());

        Object result = null;
        try  {
            result = method.invoke(am, args);
        } catch(IllegalAccessException ex)  {
        	ex.printStackTrace();
        } catch(InvocationTargetException ex)  {
            logger.log(Level.SEVERE, "Exception when calling " + out, ex.getTargetException());
        }
        logger.log(Level.INFO, "Accountmanager." + method.getName() + " called");
        return result;
    }
}
