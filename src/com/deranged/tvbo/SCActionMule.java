package com.deranged.tvbo;

public class SCActionMule extends SCAction {

	public SCActionMule(Model model, int startTime, int y) {
		super(model, startTime, y, 90, "Mule");
	}
	
	public boolean execute() {
		boolean f = true;
		if(complete) {
			f = false;
		} else if(!model.hasEnergy(50)) {
			f = false;
			errorMsg = "ENERGY";
		} else if(!model.mule()) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}

}
