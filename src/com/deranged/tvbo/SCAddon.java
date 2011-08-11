package com.deranged.tvbo;

public class SCAddon extends SCStructure {

	private String attachedTo;
	
	public SCAddon(Model model, String name) {
		super(model, name);
	}

	public String getAttachedTo() {
		return attachedTo;
	}

	public void setAttachedTo(String attachedTo) {
		this.attachedTo = attachedTo;
		//System.out.println("Attached to " + attachedTo);
	}

	public boolean detach() {
		if(name.equals("TechLab") || name.equals("Reactor")) {
			System.out.println(model.printTime() + "   <SCAddon> detaching "+name+" from "+attachedTo);
			attachedTo="";
			return true;
		} else {
			System.out.println(model.printTime() + "   <SCObject> Cannot detach " + name);
			return false;
		}
		
	}
	public boolean attach(String building) {
		if(name.equals("TechLab") || name.equals("Reactor")) {
			System.out.println(model.printTime() + "   <SCAddon> attaching "+name+" to "+building);
			attachedTo=building;
			progress=0;
			buildtime=3;
			queue++;
			return true;
		} else {
			System.out.println(model.printTime() + "   <SCObject> Cannot detach " + name);
			return false;
		}
		
	}

	
}
