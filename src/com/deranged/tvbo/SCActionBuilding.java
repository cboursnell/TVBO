package com.deranged.tvbo;

public class SCActionBuilding extends SCAction {


	public SCActionBuilding(Model model, int startTime, int y, String name) {
		super(model, startTime, y, model.getTime(name), name);
		preactionTime=3; // pull scv 3 seconds before starting building
		preactionComplete=false; // TODO implement pulling scv from minerals 3s before
		if(name.equals("Barracks") || name.equals("Factory") || name.equals("Starport")) {
			options.add("TechLab");
			options.add("Reactor");
			options.add("none");
		}
		option="none";
	}
	public SCActionBuilding(Model model, int startTime, int y, String name, String option) {
		super(model, startTime, y, model.getTime(name), name);
		this.option = option;
		preactionTime=3; // pull scv 3 seconds before starting building
		preactionComplete=false; // TODO implement pulling scv from minerals 3s before
		if(name.equals("Barracks") || name.equals("Factory") || name.equals("Starport")) {
			options.add("TechLab");
			options.add("Reactor");
			options.add("none");
		}
	}

	public boolean execute() {
		boolean f=true;
		String prereq = model.getPrereq(name); // eg barracks needs depot 
		if(complete) {
			f = false;
		} else if(!preactionComplete) {
			f = false;
			errorMsg = "SCV";
		} else if(prereq!=null && !model.isObjectComplete(prereq)) { // check for prerequisite, eg barracks needs depot
			f = false;
			errorMsg = "PREREQ";
		} else if(!option.equals("none") && !model.freeAddonExists(option)) {
			f = false;
			errorMsg = "ADDON";
		} else if(model.getMinerals() < model.getMineralCost(name)) {
			f = false;
			errorMsg = "MINERALS";
		} else if(model.getGas() < model.getGasCost(name)) {
			f = false;
			errorMsg = "GAS";
		} else if(!model.makeBuilding(name, option)) {
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

	@Override
	public void setSupplyPoint(String supplyPoint) {
		//System.out.println("<SCActionLift> supply point set to "+option);
		if(option==null) {
			System.out.println("<SCActionBuilding> option is null");
		} else {
			if(option.equals("none")) {
				//this.supplyPoint=option;
				this.supplyPoint = supplyPoint;
			} else {
				this.supplyPoint = supplyPoint +"  on "+option;
			}
		}
	}



}
