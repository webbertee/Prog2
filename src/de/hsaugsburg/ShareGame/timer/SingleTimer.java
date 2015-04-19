package de.hsaugsburg.sharegame.timer;

import java.util.Timer;

public class SingleTimer {
	private static Timer timer;
	
	public static Timer getTimer() {
		if(timer == null) {
			timer = new Timer();
		}
		return timer;
	}
}
