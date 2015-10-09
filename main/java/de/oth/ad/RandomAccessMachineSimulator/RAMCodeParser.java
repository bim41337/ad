package de.oth.ad.RandomAccessMachineSimulator;

import java.util.ArrayList;
import java.util.List;

class RAMCodeParser {
	
	// Properties
	private final int validCommandLength = 4;
	private List<String> initialCode;
	private ArrayList<String> commands;
	
	// Constructor
	RAMCodeParser(List<String> initialCode) throws InvalidCodeException {
		this.initialCode = initialCode;
		commands = new ArrayList<String>();
		parseInitialCode();
	}
	
	private void parseInitialCode() throws InvalidCodeException {
		for (String line : initialCode) {
			// Remove possible comments
			if (line.indexOf(" #") > 0) {
				line = line.substring(0, line.indexOf(" #"));
			}
			line = line.trim(); // Remove newline character
			if (line.length() != validCommandLength) {
				throw new InvalidCodeException("Invalid command was found");
			}
			commands.add(line.toUpperCase());
		}
	}
	
	ArrayList<String> getCommands() {
		return commands;
	}

}
