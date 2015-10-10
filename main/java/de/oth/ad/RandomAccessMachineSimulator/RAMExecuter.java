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
	private boolean trackExecution;
	private ArrayList<CommandAdressPair> instructions;
	private HashMap<String, Double> storage;
	
	RAMExecuter(ArrayList<CommandAdressPair> instructions) {
		this.instructions = instructions;
		storage = new HashMap<String, Double>();
		setAccuValue(0.0);
		currentInstructionIndex = 0;
		trackExecution = false;
	}
	
	void executeNextInstruction() {
		CommandAdressPair currentInstruction = instructions.get(currentInstructionIndex);
		Command currentCommand = currentInstruction.getCommand();
		String currentAddressParameter = currentInstruction.getAddress();
		if (trackExecution) {
			printInstructionTrack(currentInstruction);
		}
		switch (currentCommand) {
			case ADD:
				ramAdd(currentAddressParameter);
				break;
			case SUB:
				ramSub(currentAddressParameter);
				break;
			case MUL:
				ramMul(currentAddressParameter);
				break;
			case DIV:
				ramDiv(currentAddressParameter);
				break;
			case LDA:
				ramLda(currentAddressParameter);
				break;
			case LDK:
				ramLdk(currentAddressParameter);
				break;
			case STA:
				ramSta(currentAddressParameter);
				break;
			case INP:
				ramInp(currentAddressParameter);
				break;
			case OUT:
				ramOut(currentAddressParameter);
				break;
			case HLT:
				ramHlt(currentAddressParameter);
				break;
			case JMP:
				ramJmp(currentAddressParameter);
				break;
			case JEZ:
				ramJez(currentAddressParameter);
				break;
			case JNE:
				ramJne(currentAddressParameter);
				break;
			case JLZ:
				ramJlz(currentAddressParameter);
				break;
			case JLE:
				ramJle(currentAddressParameter);
				break;
			case JGZ:
				ramJgz(currentAddressParameter);
				break;
			case JGE:
				ramJge(currentAddressParameter);
				break;
			default:
				// Null statement (or command)
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
	private void ramJmp(String address) {
		// Line number at address - 1
		Integer instructionIndex = getStorageEntryValue(address).intValue() - 1;
		if (instructionIndex < 0 || instructionIndex >= instructions.size()) {
			// Index is out of bounds
			throw new IndexOutOfBoundsException("Jump to line " + (instructionIndex + 1) + " out of bounds");
		}
		// Next command will increment currentInstructionIndex, thus the -1
		currentInstructionIndex = instructionIndex - 1;
	}
	
	private void ramJez(String address) {
		if (Double.compare(getAccuValue(), 0.0) == 0) {
			ramJmp(address);
		}
	}
	
	private void ramJne(String address) {
		if (Double.compare(getAccuValue(), 0.0) != 0) {
			ramJmp(address);
		}
	}
	
	private void ramJlz(String address) {
		if (Double.compare(getAccuValue(), 0.0) < 0) {
			ramJmp(address);
		}
	}
	
	private void ramJle(String address) {
		if (Double.compare(getAccuValue(), 0.0) <= 0) {
			ramJmp(address);
		}
	}
	
	private void ramJgz(String address) {
		if (Double.compare(getAccuValue(), 0.0) > 0) {
			ramJmp(address);
		}
	}
	
	private void ramJge(String address) {
		if (Double.compare(getAccuValue(), 0.0) >= 0) {
			ramJmp(address);
		}
	}
	
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
	
	// Supporting methods
	private void printInstructionTrack(CommandAdressPair instr) {
		System.out.println((currentInstructionIndex + 1) + ": (" + instr.getCommand() + ", " + instr.getAddress() + ")");
	}
	
	void setExecutionTracking(boolean val) {
		trackExecution = val;
	}

}
