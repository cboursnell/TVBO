package com.deranged.tvbo;

public class SCActionBuildBase extends SCAction {


	public SCActionBuildBase(Model model, int startTime, int y) {
		super(model, startTime, y, model.getTime("CommandCenter"), "CommandCenter");
		preactionTime=10; // pull scv 10 seconds before starting building
		preactionComplete=false; // TODO implement pulling scv from minerals 3s before
	}

	public boolean execute() {
		boolean f=true;
		String prereq = model.getPrereq(name); // eg barracks needs depot 
		if(complete) {
			f = false;
		} else if(!preactionComplete) {
			f = false;
			errorMsg = "SCV";
		} else if(model.getMinerals() < model.getMineralCost(name)) {
			f = false;
			errorMsg = "MINERALS";
		} else if(model.getGas() < model.getGasCost(name)) {
			f = false;
			errorMsg = "GAS";
		} else if(!model.buildBase()) {
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
		if(!model.setSCVBuilding(2*preactionTime+duration)) { // adding time before and after
			p = false;
		}
		if(p) {
			//System.out.println(model.printTime() + "   <SCActionBuilding> Preaction");
			preactionComplete=true;
		}
		return p;

	}

}
