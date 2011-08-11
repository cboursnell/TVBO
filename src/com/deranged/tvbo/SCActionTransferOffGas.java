package com.deranged.tvbo;

public class SCActionTransferOffGas extends SCAction {
	
	public SCActionTransferOffGas(Model model, int startTime, int y) {
		super(model, startTime, y, 20, "1 off gas");
	}
	
	public boolean execute() {
		boolean f = true;
		if(complete) {
			f = false;
		} else if(model.scvsOnGas()==0) {
			f = false;
			errorMsg = "NONE";
		} else if(!model.transferOffGas()) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}

}
