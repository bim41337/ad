package de.oth.ad.RandomAccessMachineSimulator;

class NonExistingStorageFieldException extends Exception {
	
	String requestedFieldAddress;
	
	public NonExistingStorageFieldException(String address) {
		super("Invalid storage field address __" + address + "__ requested");
	}
	
	public NonExistingStorageFieldException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
