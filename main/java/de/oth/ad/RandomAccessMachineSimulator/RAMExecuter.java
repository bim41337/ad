package de.oth.ad.RandomAccessMachineSimulator;

import java.util.ArrayList;
import java.util.HashMap;

class RAMExecuter {
	
	// Properties
	private final String accuAdress = "00";
	private Double accumulator;
	/*
	private String output;
	*/
	private int currentCommandIndex;
	private ArrayList<String> commands;
	private HashMap<String, Double> storage;
	
	RAMExecuter(ArrayList<String> commands) {
		this.commands = commands;
		currentCommandIndex = 0;
	}
	
	void executeNextCommand() {
		for (String command : commands) {
			System.out.println(command);
		}
	}
	
	boolean isRunning() {
		return !(currentCommandIndex == commands.size());
	}

}
