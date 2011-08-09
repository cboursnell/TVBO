package com.deranged.tvbo;

public class SCStructure extends SCObject {

	protected int queue;
	protected int supply = 0;
	protected SCObject constructing;
	
	public SCStructure(Model model, String name) {
		super(model, name);
		queue=0;
		supply = model.getSupply(name);
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
				if(constructing!=null) {
					//System.out.println(model.printTime() + "   <SCStructure> Constructing " + constructing.getName() + " Progress = " + constructing.getProgress());
					constructing.update();
					if(constructing.isComplete() && !constructing.getName().equals("SCV")) {
						model.addUnit(constructing);
						constructing=null;
					}
				} else {
					//System.out.println(model.printTime() + "   <SCStructure> Something wrong here");
				}
			}
		} else {
			if(progress<buildtime) {
				progress++;
			} 
			if(progress>=buildtime) {
				complete=true;
				progress=0;
				model.addSupply(supply);
			}/*
			System.out.println(model.printTime() + "   <SCStructure:update!complete> Progress = " + progress);
			if(progress>=buildtime) {
				model.addSupply(supply);
				System.out.println(model.printTime() + "   <SCStructure> Adding "+supply+" to supply");
			}*/
		}
	}
	
	public boolean addObjectToQueue(String name) {
		buildtime=model.getTime(name);
		//System.out.println(model.printTime() + "   <SCStructure> Adding " + name + " to queue");
		if(queue==0 && complete) {
			setBuildtime(buildtime);
			constructing=new SCObject(model, name);
			model.spendMinerals(model.getMineralCost(name));
			model.spendGas(model.getGasCost(name));
			model.addFood(model.getFood(name));
			//con = name;
			progress=0;
			queue++;
			return true;
		} else {
			return false;
		}
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
}
