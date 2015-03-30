package Assets;
public class AssetTest {
	private static CashAccount cashAcc;
	private static ShareDepositAccount shareAcc;

	public static void main(String[] args) {
		Share share1 = new Share("Audi", 1000L);
		Share share2 = new Share("SpaceX", 1500L);
		Share share3 = new Share("Facebook", 3000L);
		cashAcc = new CashAccount("Konto", 100000L);
		shareAcc = new ShareDepositAccount("Technologie");
		printValueDebug();
		buyDebug(share1, 10);
		buyDebug(share2, 10);
		System.out.println("Die " + share1.getName() + "-Aktien nehmen 20% an Wert zu");
		share1.setValue((long)(share1.getValue() * 1.2F));
		System.out.println("Das macht einen Profit von " + (shareAcc.getValue() - shareAcc.getBuyValue()));
		printValueDebug();
		sellDebug(share1, 5);
		sellDebug(share1, 10);	//Verkaufen von zu vielen Aktien
		sellDebug(share3, 1);	//Verkaufen von nicht vorhandenen Aktien
		buyDebug(share3, 100);	//Kaufen von Aktien ohne genügend Geld

		//Speicher Test
//		Share[] shareArray = new Share[20];
//		for(int i = 0; i < 20; i++) {
//			shareArray[i] = new Share("Aktie" + i, 100L);
//		}
//		System.out.println("Vor: " + shareAcc.getLengthDebug());
//		
//		for(int i = 0; i < 10; i++) {
//			buyDebug(shareArray[i], 1);
//		}
//		System.out.println("+10: " + shareAcc.getLengthDebug());
//		
//		for(int i = 10; i < 20; i++) {
//			buyDebug(shareArray[i], 1);
//		}
//		System.out.println("+10: " + shareAcc.getLengthDebug());
//		
//		for(int i = 0; i < 10; i++) {
//			sellDebug(shareArray[i], 1);
//		}
//		System.out.println("-10: " + shareAcc.getLengthDebug());
//		for(int i = 10; i < 20; i++) {
//			sellDebug(shareArray[i], 1);
//		}
//		System.out.println("-10: " + shareAcc.getLengthDebug());
	}
	
	
	private static void buyDebug(Share share, int count) {
		if(buy(share, count)) {
			System.out.println("Erflogreich " + count + " " + share.getName() + " Aktien gekauft");
		} else {
			System.out.println("Konnte konnte nicht "  + count + " " + share.getName() + " Aktien kaufen");
		}
		//printValueDebug();
	}
	private static void sellDebug(Share share, int count) {
		if(sell(share, count)) {
			System.out.println("Erflogreich " + count + " " + share.getName() + " Aktien verkauft");
		} else {
			System.out.println("Konnte konnte nicht "  + count + " " + share.getName() + " Aktien verkaufen");
		}
		printValueDebug();
	}
	
	private static void printValueDebug() {
		System.out.println("Kontostand: " + cashAcc.getValue());
		System.out.println("Depotwert:" + shareAcc.getValue());
	}

	private static boolean buy(Share share, int count) { //Grund zurückgeben?
		if ((share.getValue() * count) > cashAcc.getValue())
			return false;
		if (shareAcc.addShare(share, count)) {
			cashAcc.remove(share.getValue() * count);
			return true;
		}
		return false;
	}
	private static boolean sell(Share share, int count) {
		if(shareAcc.removeShare(share, count)) {
			cashAcc.add(share.getValue() * count);
			return true;
		}
		return false;
	}
}
