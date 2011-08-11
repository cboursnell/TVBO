package com.deranged.tvbo;

public class SCActionBuildSCV extends SCAction {

	public SCActionBuildSCV(Model model, int startTime, int y) {
		super(model, startTime, y, model.getTime("SCV"), "SCV");
	}

	public boolean execute() {
		boolean f = true;

		if(complete) {
			f = false;
		} else if(model.getMinerals()<model.getMineralCost(name)) {
			f = false;
			errorMsg = "MINERALS";
		} else if(model.getFood(name)>0 && model.getFood()+model.getFood(name)>model.getSupply()) {
			f=false;
			errorMsg="SUPPLY BLOCKED";
		} else if(!model.isFreeBase()){
			f=false;
			errorMsg="QUEUE";
		} else if(!model.buildSCV()) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;


	}

}
