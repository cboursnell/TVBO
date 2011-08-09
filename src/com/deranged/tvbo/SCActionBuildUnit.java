package com.deranged.tvbo;

public class SCActionBuildUnit extends SCAction {

	public SCActionBuildUnit(Model model, int startTime, int y, String name) {
		super(model, startTime, y, model.getTime(name), name);
	}
	
	public boolean execute() {
		boolean f = true;
		String prereq = model.getPrereq(name); // eg tech lab
		String build = model.getBuild(name);   // barracks
		
		if(complete) {
			f = false;
		} else {
			
		}
		if(f) {
			complete = true;
		}
		return f;
	}
}
