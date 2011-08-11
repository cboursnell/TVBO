package com.deranged.tvbo;

public class SCActionBuildAddon extends SCAction {

	
	public SCActionBuildAddon(Model model, int startTime, int y, String name) {
		super(model, startTime, y, model.getTime(name), name);
		option="";
		options.add("Barracks");
		options.add("Factory");
		options.add("Starport");
	}
	public SCActionBuildAddon(Model model, int startTime, int y, String name, String option) {
		super(model, startTime, y, model.getTime(name), name);
		setOption(option);
		options.add("Barracks");
		options.add("Factory");
		options.add("Starport");
	}
	
	public boolean execute() {
		boolean f = true;
		
		if(complete) {
			f = false;
		} else if(option.equals("")) {
			f = false;
			errorMsg = "SET OPTION";
		} else if(model.getMinerals()<model.getMineralCost(name)) {
			f = false;
			errorMsg = "MINERALS";
		} else if(model.getGas() < model.getGasCost(name)) {
			f = false;
			errorMsg = "GAS";
		} else if(!model.isObjectComplete(option)) {
			f = false;
			errorMsg = "QUEUE";
		} else if(!model.isAvailable(option)) {
			f = false;
			errorMsg = "QUEUE";
		} else if(!model.buildAddon(name, option)) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
		
	}
	public void setOption(int i) {
		option = options.get(i);
		System.out.println("<SCActionBuildAddon> Building set to " + option);
		model.reset();
		model.play();
	}
	
	

}
