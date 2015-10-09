package de.oth.ad.RandomAccessMachineSimulator;

import java.util.ArrayList;
import java.util.HashMap;

class RAMExecuter {
	
	// Properties
	private int currentInstructionIndex;
	private ArrayList<CommandAdressPair> instructions;
	private HashMap<String, Double> storage;
	
	RAMExecuter(ArrayList<CommandAdressPair> instructions) {
		this.instructions = instructions;
		currentInstructionIndex = 0;
	}
	
	/*
	 * TODO: Muss erkennen, was zu tun ist (Switch) und dann ausführen.
	 * TODO: Storage-Dumb am Ausführungsende ausgeben
	 */
	void executeNextInstruction() {
		
	}
	
	boolean isRunning() {
		return !(currentInstructionIndex == instructions.size());
	}

}
