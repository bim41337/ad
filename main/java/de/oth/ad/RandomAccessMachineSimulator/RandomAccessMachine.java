package de.oth.ad.RandomAccessMachineSimulator;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RandomAccessMachine {
	
	// Properties - Components
	private RAMCodeParser parser;
	private RAMExecuter executer;
	
	// Properties - Other
	private final String programInputPath = "src/main/programCode.txt";
	
	public RandomAccessMachine() {
		try {
			parser = new RAMCodeParser(Files.readAllLines(Paths.get(programInputPath)));
			executer = new RAMExecuter(parser.getValidInstructions());
		} catch(Exception e) {
			System.out.println(e.getClass().getSimpleName() + " caught:");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public void run(boolean trackExecution) {
		executer.setExecutionTracking(trackExecution);
		while (executer.isRunning()) {
			executer.executeNextInstruction();
		}
		if (trackExecution) {
			executer.printStorageDumb();
		}
		System.out.println("Machine execution finished");
	}
	
	public void run() {
		run(false);
	}
	
	public static void main(String[] args) {
		RandomAccessMachine ram = new RandomAccessMachine();
		// Set parameter to true to get tracking information printed to console
		ram.run(false);
	}
	
}
