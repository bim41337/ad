package de.oth.ad.RandomAccessMachineSimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;

class RAMExecuter {
	
	// Properties
	private final String ACCUMULATOR_KEY = "00";
	private int currentInstructionIndex;
	private ArrayList<CommandAdressPair> instructions;
	private HashMap<String, Double> storage;
	
	RAMExecuter(ArrayList<CommandAdressPair> instructions) {
		this.instructions = instructions;
		storage = new HashMap<String, Double>();
		setAccuValue(0.0);
		currentInstructionIndex = 0;
	}
	
	/*
	 * TODO: Muss erkennen, was zu tun ist (Switch) und dann ausführen.
	 * TODO: Storage-Dumb am Ausführungsende ausgeben
	 */
	void executeNextInstruction() {
		CommandAdressPair currentInstruction = instructions.get(currentInstructionIndex);
		Command currentCommand = currentInstruction.getCommand();
		String currentAddressParamter = currentInstruction.getAddress();
		switch (currentCommand) {
			case ADD:
				ramAdd(currentAddressParamter);
				break;
			case SUB:
				ramSub(currentAddressParamter);
				break;
			case MUL:
				ramMul(currentAddressParamter);
				break;
			case DIV:
				ramDiv(currentAddressParamter);
				break;
			case LDA:
				ramLda(currentAddressParamter);
				break;
			case LDK:
				ramLdk(currentAddressParamter);
				break;
			case STA:
				ramSta(currentAddressParamter);
				break;
			case INP:
				ramInp(currentAddressParamter);
				break;
			case OUT:
				ramOut(currentAddressParamter);
				break;
			case HLT:
				ramHlt(currentAddressParamter);
				break;
				
		}
		currentInstructionIndex++;
	}
	
	void printStorageDumb() {
		System.out.println("Storage Dumb:");
		for (Entry<String, Double> entry : storage.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	boolean isRunning() {
		return currentInstructionIndex < instructions.size();
	}
	
	// RAM standard execution methods
	private void ramAdd(String address) {
		setAccuValue(getAccuValue() + getStorageEntryValue(address));
	}
	
	private void ramSub(String address) {
		setAccuValue(getAccuValue() - getStorageEntryValue(address));
	}
	
	private void ramMul(String address) {
		setAccuValue(getAccuValue() * getStorageEntryValue(address));
	}
	
	private void ramDiv(String address) {
		if (Double.compare(0.0, getStorageEntryValue(address)) == 0) {
			throw new ArithmeticException("Division by Zero detected");
		}
		setAccuValue(getAccuValue() / getStorageEntryValue(address));
	}
	
	private void ramLda(String address) {
		setAccuValue(getStorageEntryValue(address));
	}
	
	private void ramLdk(String intHexConstant) {
		// Sign as hexadecimal value
		Integer parsed = Integer.parseInt(intHexConstant, 16);
		setAccuValue(parsed.doubleValue());
	}
	
	private void ramSta(String address) {
		setStorageEntryValue(address, getAccuValue());
	}
	
	private void ramInp(String address) {
		Scanner scanner = new Scanner(System.in);
		Integer input, max = 0xFF;
		System.out.println("Input requested (Hex number 0 - FF): ");
		input = scanner.nextInt(16);
		try {
			if (input > max) {
				throw new InputMismatchException("Input out of range (0 - FF)");
			}
		} catch (InputMismatchException e) {
			input = 0;
			System.out.println(e.getMessage());
		}
		setStorageEntryValue(address, input.doubleValue());
	}
	
	private void ramOut(String address) {
		System.out.println("Out: " + getStorageEntryValue(address));
	}
	
	private void ramHlt(String address) {
		System.out.println("Hlt(" + address + ") reached: Exiting machine");
		currentInstructionIndex = instructions.size(); // Jump to end
	}
	
	// Ram execution jump methods
	
	
	// RAM execution support methods
	private Double getStorageEntryValue(String address) {
		Double value = 0.0;
		if (address.equals(ACCUMULATOR_KEY)) {
			value = getAccuValue();
		} else {
			try {
				value = storage.get(address);
				if (value == null) {
					throw new NonExistingStorageFieldException(address);
				}
			} catch(NonExistingStorageFieldException e) {
				value = 0.0;
				System.out.println(e.getMessage());
			}
		}
		return value;
	}
	
	private void setStorageEntryValue(String address, Double value) {
		if (address.equals(ACCUMULATOR_KEY)) {
			setAccuValue(value);
		} else {
			storage.put(address, value);
		}
	}
	
	private Double getAccuValue() {
		return storage.get(ACCUMULATOR_KEY);
	}
	
	private void setAccuValue(Double val) {
		storage.put(ACCUMULATOR_KEY, val);
	}

}
