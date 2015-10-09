package de.oth.ad.RandomAccessMachineSimulator;

import java.util.ArrayList;
import java.util.List;

class RAMCodeParser {
	
	// Properties
	private final int validInstructionLength = 4;
	private final int validCommandLength = 2;
	private List<String> initialCode;
	private ArrayList<String> instructions;
	private ArrayList<CommandAdressPair> validInstructions;
	
	// Constructor
	RAMCodeParser(List<String> initialCode) throws InvalidCodeException {
		this.initialCode = initialCode;
		instructions = new ArrayList<String>();
		validInstructions = new ArrayList<CommandAdressPair>();
		cleanInitialCode();
		validateCommands();
	}
	
	private void cleanInitialCode() throws InvalidCodeException {
		for (String line : initialCode) {
			// Remove possible comments
			if (line.indexOf(" #") > 0) {
				line = line.substring(0, line.indexOf(" #"));
			}
			line = line.trim(); // Remove newline character
			if (line.length() != validInstructionLength) {
				throw new InvalidCodeException("Malformed code detected");
			}
			instructions.add(line.toUpperCase());
		}
	}
	
	void validateCommands() throws InvalidCodeException {
		boolean matchingValueFound;
		String instruction, command, address;
		for (int i = 0; i < instructions.size(); i++) {
			instruction = instructions.get(i);
			command = instruction.substring(0, validCommandLength);
			address = instruction.substring(validCommandLength, instruction.length());
			matchingValueFound = false;
			for (Command cmd : Command.values()) {
				if (cmd.getCommandValue().equals(command)) {
					validInstructions.add(new CommandAdressPair(cmd, address));
					matchingValueFound = true;
					break;
				}
			}
			if (!matchingValueFound) {
				throw new InvalidCodeException("Invalid command found");
			}
		}
	}
	
	ArrayList<CommandAdressPair> getValidInstructions() {
		return validInstructions;
	}

}
