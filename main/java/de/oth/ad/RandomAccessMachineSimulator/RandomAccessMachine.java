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
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public void run() {
		// Start and manage the execution
		executer.executeNextInstruction();
	}
	
	public static void main(String[] args) {
		RandomAccessMachine ram = new RandomAccessMachine();
		ram.run();
	}
	
}
