package com.deranged.tvbo;

public class SCObject {

	protected Model model;
	protected String name;

	protected boolean complete;
	protected int progress;

	protected int buildtime;

	public SCObject(Model model, String name) {
		this.model = model;
		this.name = name;
		this.buildtime = model.getTime(name);
		progress = 0;
		complete = false;
		//System.out.println("Buildtime " + buildtime);
	}

	public boolean isComplete() {
		return complete;
	}

	public void update() {
		//System.out.println(model.printTime() + "   <SCObject> "+name+" Progress "+progress+"/"+buildtime);
		if(!complete) {
			if(progress<buildtime) {
				progress++;
			} 
			if(progress>=buildtime) {
				complete=true;
				progress=0;
				//System.out.println(model.printTime() + "   <SCObject> " + name + " complete");
			}
		}
	}

	public void complete() {
		complete = true;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	public boolean isAvailable() {
		if(complete) {
			return true;
		} else {
			return false;
		}
	}

	public int getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(int buildtime) {
		this.buildtime = buildtime;
	}

	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean lift() {
		if(name.equals("Barracks") ||name.equals("Factory") ||name.equals("Starport")) {
			return true;
		} else {
			System.out.println(model.printTime() + "   <SCObject> Cannot lift " + name);
			return false;
		}
		
	}
	public boolean land(String addon) {
		if(name.equals("Barracks") ||name.equals("Factory") ||name.equals("Starport")) {
			return true;
		} else {
			System.out.println(model.printTime() + "   <SCObject> Cannot land " + name);
			return false;
		}
		
	}

	public boolean detach() {
		if(name.equals("TechLab") || name.equals("Reactor")) {
			return true;
		} else {
			System.out.println(model.printTime() + "   <SCObject> Cannot detach " + name);
			return false;
		}
		
	}
	public boolean attach(String building) {
		if(name.equals("TechLab") || name.equals("Reactor")) {
			return true;
		} else {
			System.out.println(model.printTime() + "   <SCObject> Cannot detach " + name);
			return false;
		}
		
	}
}
