package com.deranged.tvbo;

public class SCActionBuildUnit extends SCAction {

	public SCActionBuildUnit(Model model, int startTime, int y, String name) {
		super(model, startTime, y, model.getTime(name), name);
	}
	
	public boolean execute() {
		boolean f = true;
		String prereq = model.getPrereq(name); // eg armory for thor
		String build = model.getBuild(name);   // factory for thor
		String tech = model.getTech(name);   // eg techlab for thor
		
		if(complete) {
			f = false;
		} else if(prereq!=null && !model.isObjectComplete(prereq)) {
			f = false;
			errorMsg = "PREREQ";
		} else if(prereq!=null && !model.isObjectComplete(build)) {
			f = false;
			errorMsg = "BUILD";
		} else if(tech!=null && !model.hasAddon(build, tech)) {
			f = false;
			errorMsg = "TECHLAB";
		} else if(model.getMinerals()<model.getMineralCost(name)) {
			f = false;
			errorMsg = "MINERALS";
		} else if(!model.isAvailable(build)) {
			f = false;
			errorMsg = "QUEUE";
		} else if(model.getGas() < model.getGasCost(name)) {
			f = false;
			errorMsg = "GAS";
		} else if(!model.addUnitToQueue(name)) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}
}
