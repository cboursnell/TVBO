package com.deranged.tvbo;

public class SCActionUpgradeBase extends SCAction {

	public SCActionUpgradeBase(Model model, int startTime, int y, String name) {
		super(model, startTime, y, 35, name);
	}
	
	public boolean execute() {
		boolean f = true;
		String prereq = model.getPrereq(name);
		if(complete) {
			f = false;
		} else if(model.getMinerals() < model.getMineralCost(name)) {
			f = false;
			errorMsg = "MINERALS";
		} else if(model.getGas() < model.getGasCost(name)) {
			f = false;
			errorMsg = "GAS";
		} else if(!model.isObjectComplete(prereq)) {
			f = false;
			errorMsg = "PREREQ";
		} else if(!model.isFreeBase()) {
			f = false;
			errorMsg = "BASE";
		} else if(!model.upgradeBase(name)) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}
}
