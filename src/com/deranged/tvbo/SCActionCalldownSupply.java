package com.deranged.tvbo;

public class SCActionCalldownSupply extends SCAction {

	public SCActionCalldownSupply(Model model, int startTime, int y) {
		super(model, startTime, y, 30, "CalldownSupply");
	}
	
	public boolean execute() {
		boolean f = true;
		if(complete) {
			f = false;
		} else if(!model.hasEnergy(50)) {
			f = false;
			errorMsg = "ENERGY";
		} else if(!model.calldownSupply()) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}

}
