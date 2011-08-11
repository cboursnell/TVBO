package com.deranged.tvbo;

public class SCActionLand extends SCAction {

	public SCActionLand(Model model, int startTime, int y, String name) {
		super(model, startTime, y, 30, name);
		option="";
		options.add("TechLab");
		options.add("Reactor");
		options.add("none");
	}
	
	public SCActionLand(Model model, int startTime, int y, String name, String option) {
		super(model, startTime, y, 30, name);
		this.option=option;
		options.add("TechLab");
		options.add("Reactor");
		options.add("none");
	}
	
	public boolean execute() {
		boolean f = true;
		
		if(complete) {
			f = false;
		} else if(option.equals("")) {
			f = false;
			errorMsg = "SET OPTION";
		} else if(!option.equals("none") && !model.freeAddonExists(option)) {
			f = false;
			errorMsg = "NO ADDON";
		} else if(!model.land(name, option)) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
		
	}

	
	@Override
	public void setSupplyPoint(String supplyPoint) {
		//System.out.println("<SCActionLand> supply point set to "+option);
		this.supplyPoint=option;
	}
	
	

	public String toString() {
		return "Land"+name;
	}
	

	
}
