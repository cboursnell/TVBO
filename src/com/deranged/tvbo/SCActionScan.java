package com.deranged.tvbo;

public class SCActionScan extends SCAction {

	public SCActionScan(Model model, int startTime, int y) {
		super(model, startTime, y, 20, "Scan");
	}
	
	public boolean execute() {
		boolean f = true;
		if(complete) {
			f = false;
		} else if(!model.hasEnergy(50)) {
			f = false;
			errorMsg = "ENERGY";
		} else if(!model.scan()) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}

}
