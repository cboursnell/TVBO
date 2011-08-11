package com.deranged.tvbo;

public class SCActionResearch extends SCAction {

	public SCActionResearch(Model model, int startTime, int y, String name) {
		super(model, startTime, y, model.getTime(name), name);
	}

	public boolean execute() {
		boolean f = true;
		String prereq = model.getPrereq(name); // eg factory for nitro pack
		String build = model.getBuild(name);   // eg barracks for stim, ebay for +1
		String tech = model.getTech(name);   // eg techlab for stim
		//System.out.println(model.printTime() + "   <SCActionResearch> Build = "+build+" Prereq = "+prereq+" Tech = "+tech);
		
		boolean pre=true;
		if(name.matches(".*Level.*")) {
			int level = Integer.parseInt(name.substring(name.length()-1, name.length()));
			//System.out.println(model.printTime() + "   <SCActionResearch> Upgrade: Level: " + level);
			//int level = (Integer.valueOf(name.substring(name.length()-2, name.length()-1))).intValue();
			if(level>1) {
				String s = name.substring(0, name.length()-1)+(level-1);
				//System.out.println("Look to see if " + s + " is complete.");
				if(model.isObjectComplete(s)) {
					pre=true;
				} else {
					pre=false;
				}
			}
		}
		
		
		if(complete) {
			f = false;
		} else if(model.alreadyStarted(name)) {
			f=false;
			errorMsg="ALREADY";			
		} else if(!pre) {
			f = false;
			errorMsg = "PREREQ";
		} else if(prereq!=null && !model.isObjectComplete(prereq)) {
			f = false;
			errorMsg = "PREREQ";
		} else if(build!=null && !model.isObjectComplete(build)) {
			f = false;
			errorMsg = "BUILD";
		} else if(tech!=null && !model.hasAddon(build, tech)) {
			f = false;
			errorMsg = "TECHLAB";
		} else if(model.getMinerals()<model.getMineralCost(name)) {
			f = false;
			errorMsg = "MINERALS";
		} else if(model.getGas() < model.getGasCost(name)) {
			f = false;
			errorMsg = "GAS";
		} else if(tech!=null && !model.isAvailable(tech)) {
			f = false;
			errorMsg = "QUEUE";
		} else if(tech==null && !model.isAvailable(build)) {
			f = false;
			errorMsg = "QUEUE";
		} else if(!model.addResearch(name)) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
	}
}