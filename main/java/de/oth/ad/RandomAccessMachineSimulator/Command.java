package de.oth.ad.RandomAccessMachineSimulator;

enum Command {
	// Main commands
	ADD, SUB, MUL, DIV, LDA, LDK, STA, INP, OUT, HLT,
	// Jumps
	JMP, JEZ, JNE, JLZ, JLE, JGZ, JGE
}
