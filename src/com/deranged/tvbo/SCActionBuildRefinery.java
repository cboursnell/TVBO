package com.deranged.tvbo;

public class SCActionBuildRefinery extends SCAction {

	public SCActionBuildRefinery(Model model, int startTime, int y) {
		super(model, startTime, y, model.getTime("Refinery"), "Refinery");
		preactionTime=1; // pull scv 3 seconds before starting building
		preactionComplete=false; // TODO implement pulling scv from minerals 3s before
	}
	
	public boolean execute() {
		boolean f = true;
		if(complete) {
			f = false;
		} else if(model.freeGeysers()==0) {
			f = false;
			errorMsg = "NO GEYSERS";
		} else if(model.getMinerals() < model.getMineralCost("Refinery")) {
			f = false;
			errorMsg = "MINERALS";
		} else if(!model.buildRefinery()) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}
	
	public boolean preaction() { // this gets done X seconds before the execute() method
		boolean p = true;
		if(!model.setSCVBuilding(preactionTime+duration)) { // adding time before and after
			p = false;
		}
		if(p) {
			//System.out.println(model.printTime() + "   <SCActionBuilding> Preaction");
			preactionComplete=true;
		}
		return p;
		
	}
}
