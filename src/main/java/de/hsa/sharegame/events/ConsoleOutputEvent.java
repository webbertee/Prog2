package de.hsa.sharegame.events;

import java.util.EventObject;

public class ConsoleOutputEvent extends EventObject {
	private final String output;
    public ConsoleOutputEvent(Object source, String output) {
        super(source);
        this.output = output;
    }
    
    public String getOutput()	 {
    	return output;
    }
}