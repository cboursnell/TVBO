package com.deranged.tvbo;

public class SCActionTransferToGas extends SCAction {

	public SCActionTransferToGas(Model model, int startTime, int y) {
		super(model, startTime, y, 20, "TransferToGas");
	}
	
	public boolean execute() {
		boolean f = true;
		if(complete) {
			f = false;
		} else if(model.freeRefineries()==0) {
			f = false;
			errorMsg = "NO REFINERY";
		} else if(!model.transferToGas()) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}
	
	public String toString() {
		return "+1 on gas";
	}

}
