package de.oth.ad.RandomAccessMachineSimulator;

class CommandAdressPair {
	
	private Command command;
	private String address;
	
	CommandAdressPair(Command cmd, String adr) {
		command = cmd;
		address = adr;
	}

	Command getCommand() {
		return command;
	}

	String getAddress() {
		return address;
	}

}
