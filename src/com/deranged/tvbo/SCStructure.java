package com.deranged.tvbo;

public class SCStructure extends SCObject {
	protected int queue;
	protected int supply = 0;
	protected SCObject constructing;
	protected SCObject constructing2; // for when you have a reactor ONLY
	private String addonName;
	private boolean lifted;

	public SCStructure(Model model, String name) {
		super(model, name);
		queue=0;
		supply = model.getSupply(name);
		addonName="";
		lifted=false;
	}

	public void update() {
		//super.update();
		if(complete) {
			//System.out.println(model.printTime() + "   <SCStructure> "+name+" Updating queue " + queue);
			if(queue>0) {
				progress++;
				if(progress<buildtime) {
				} else if(progress>=buildtime){
					queue--;
					//System.out.println(model.printTime() + "   <SCStructure> Queue set to 0");
					progress=0;
				}
			}
			if(constructing!=null) {
				//System.out.println(model.printTime() + "   <SCStructure> Constructing " + constructing.getName() + " Progress = " + constructing.getProgress());
				constructing.update();
				if(constructing.isComplete() && !constructing.getName().equals("SCV")) {
					model.addUnit(constructing);
					//System.out.println(model.printTime() + "   <SCStructure:update> Setting constructing to null");
					constructing=null;
				}
			} else {
				//System.out.println(model.printTime() + "   <SCStructure> Something wrong here");
			}
			if(addonName.equals("Reactor") && constructing2!=null) {
				//System.out.println(model.printTime() + "   <SCStructure> Constructing " + constructing.getName() + " Progress = " + constructing.getProgress());
				constructing2.update();
				if(constructing2.isComplete() && !constructing2.getName().equals("SCV")) {
					model.addUnit(constructing2);
					constructing2=null;
				}
			} else {
				//System.out.println(model.printTime() + "   <SCStructure> Something wrong here");
			}

		} else {
			if(progress<buildtime) {
				progress++;
			} 
			if(progress>=buildtime) {
				complete=true;
				progress=0;
				model.addSupply(supply);
			}
			//			if(name.equals("TechLab")) System.out.println(model.printTime() + "   <SCStructure:update!complete> Progress = " + progress);
			/*if(progress>=buildtime) {
				model.addSupply(supply);
				System.out.println(model.printTime() + "   <SCStructure> Adding "+supply+" to supply");
			}*/
		}
	}

	public boolean addObjectToQueue(String name) {
		buildtime=model.getTime(name);
		//System.out.println(model.printTime() + "   <SCStructure> Adding " + name + " to queue");
		if(addonName.equals("Reactor") && complete) {
			if(constructing==null && queue==0 && complete) {
				constructing=new SCObject(model, name);
				model.spendMinerals(model.getMineralCost(name));
				model.spendGas(model.getGasCost(name));
				model.addFood(model.getFood(name));
				return true;
			} else if(constructing!=null && constructing2==null) {
				constructing2=new SCObject(model, name);
				model.spendMinerals(model.getMineralCost(name));
				model.spendGas(model.getGasCost(name));
				model.addFood(model.getFood(name));
				return true;
			} else {
				System.out.println("Oh Shit!");
				return false;
			}
		} else if(constructing==null && queue==0 && complete) {
			setBuildtime(buildtime);
			constructing=new SCObject(model, name);
			model.spendMinerals(model.getMineralCost(name));
			model.spendGas(model.getGasCost(name));
			model.addFood(model.getFood(name));
			//con = name;
			progress=0;
			//queue++;
			return true;
		} else {
			return false;
		}
	}

	public boolean isAvailable() {
		if(addonName.equals("Reactor")) {
			if(constructing!=null  && constructing2!=null) {
				return false;
			} else {
				return true;
			}
		} else {
			if(constructing==null) {
				return true;
			} else {
				System.out.println(model.printTime() + "   <SCStructure:isAvailable> constructing is not null");
				return false;
			}
		}
	}

	public boolean buildAddon(String addonName) {
		setBuildtime(model.getTime(addonName));
		queue++;
		model.spendMinerals(model.getMineralCost(addonName));
		model.spendGas(model.getGasCost(addonName));
		progress=0;
		this.addonName = addonName;
		if(addonName.equals("Reactor")) {

		}
		return true;
	}

	public int getQueueLength() {
		return queue;
	}

	public int getSupply() {
		return supply;
	}

	public void setSupply(int supply) {
		this.supply = supply;
	}

	public String getAddonName() {
		return addonName;
	}

	public void setAddonName(String addonName) {
		this.addonName = addonName;
	}

	public boolean isLifted() {
		return lifted;
	}

	public void setLifted(boolean lifted) {
		this.lifted = lifted;
	}
}
