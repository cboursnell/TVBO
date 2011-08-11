package com.deranged.tvbo;

public class SCActionLift extends SCAction {

	public SCActionLift(Model model, int startTime, int y, String name) {
		super(model, startTime, y, 30, name);
		option="";
		options.add("TechLab");
		options.add("Reactor");
		options.add("none");
	}
	
	public SCActionLift(Model model, int startTime, int y, String name, String option) {
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
		} else if(!option.equals("none") && !model.hasAddon(name, option)) {
			f = false;
			errorMsg = "NO ADDON";
		} else if(!model.lift(name, option)) {
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
		//System.out.println("<SCActionLift> supply point set to "+option);
		this.supplyPoint=option;
	}
	
	
	public String toString() {
		return "Lift"+name;
	}
	

	
}
