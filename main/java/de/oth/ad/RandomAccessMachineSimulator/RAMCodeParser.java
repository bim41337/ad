package de.oth.ad.RandomAccessMachineSimulator;

import java.util.ArrayList;
import java.util.List;

class RAMCodeParser {
	
	// Properties
	private final int VALID_INSTRUCTION_LENGTH = 4;
	private final int VALID_COMMAND_LENGTH = 2;
	private final String NULL_INSTRUCTION = "--00";
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
		int lineIndex;
		for (String line : initialCode) {
			lineIndex = initialCode.indexOf(line);
			// Remove possible comments
			if (line.indexOf(" #") >= 0) {
				if (line.indexOf(" #") == 0) {
					// Whole line is a comment
					instructions.add(NULL_INSTRUCTION);
					continue;
				}
				line = line.substring(0, line.indexOf(" #"));
			}
			line = line.trim(); // Remove newline character
			if (line.length() != VALID_INSTRUCTION_LENGTH) {
				throw new InvalidCodeException("Malformed code detected at line " + (lineIndex + 1));
			}
			instructions.add(line.toUpperCase());
		}
	}
	
	void validateCommands() throws InvalidCodeException {
		boolean matchingValueFound;
		String instruction, command, address;
		for (int i = 0; i < instructions.size(); i++) {
			// Extract information from instruction
			instruction = instructions.get(i);
			command = instruction.substring(0, VALID_COMMAND_LENGTH);
			address = instruction.substring(VALID_COMMAND_LENGTH, instruction.length());
			matchingValueFound = false;
			// Check for matching command value
			for (Command cmd : Command.values()) {
				if (cmd.getCommandValue().equals(command)) {
					// Command is valid, add to instruction stash
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
