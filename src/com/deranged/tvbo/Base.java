package com.deranged.tvbo;

import java.util.ArrayList;

public class Base extends SCStructure {

	private MineralPatch[] patches;
	private VespeneGeyser[] gas;
	private String upgrading;
	
	private int energy;
	private double energyRegen;
	private double energyCounter;
	
	private ArrayList<SCV> idleSCVs = new ArrayList<SCV>(); // not mining because the patches are saturated
	private ArrayList<SCV> busySCVs = new ArrayList<SCV>(); // building something

	public Base(Model model) {
		super(model, "CommandCenter");
		supply = 11;
		patches = new MineralPatch[8];
		patches[0] = new MineralPatch(1500, 1);
		patches[1] = new MineralPatch(1500, 1);
		patches[2] = new MineralPatch(1500, 1);
		patches[3] = new MineralPatch(1500, 1);
		patches[4] = new MineralPatch(1500, 2);
		patches[5] = new MineralPatch(1500, 2);
		patches[6] = new MineralPatch(1500, 3);
		patches[7] = new MineralPatch(1500, 3);

		gas = new VespeneGeyser[2];
		gas[0] = new VespeneGeyser(2500);
		gas[1] = new VespeneGeyser(2500);

		idleSCVs = new ArrayList<SCV>();
		busySCVs = new ArrayList<SCV>();
		setName("CommandCenter");
		upgrading="";
		complete=false;
		energy=0;
		energyRegen=0;
		energyCounter=0;
	}

	public void start() {
		for(int i = 0; i < 6; i++) {
			SCV s = new SCV(model);
			s.complete();
			patches[i].addSCV(s);
			model.addFood(1);
		}
		model.addSupply(supply);
		complete = true;

	}

	public void update() {
		super.update();
		if(complete) {
			//System.out.println(model.printTime() + "   <Base> "+name+" Updating...");
			for(int i=0;i<8;i++) { // Update 8 Mineral patches (and probes on them)
				patches[i].update();
			}
			for(int i=0;i<2;i++) { // Update gas
				gas[i].update();
			}
			if(energy<200) {
				energyCounter+=energyRegen;
			}
			if(energyCounter>1) {
				energy++;
				energyCounter--;
			}/*
			if(queue>0) {
				if(progress>=buildtime) {
					if(upgrading.equals("OrbitalCommand")) {
						setName(upgrading);
						setEnergy(50);
						setEnergyRegen(0.5625);
						upgrading="";
						System.out.println(model.printTime() + "   <Base:update> Energy = " + energy + " Regen = " + energyRegen);
					} else if( upgrading.equals("PlanetaryFortress")) {
						setName(upgrading);
					}
				}
				
			}*/
			if(queue ==0  && upgrading.equals("OrbitalCommand") && progress==0 && buildtime==35) {
				setName(upgrading);
				setEnergy(50);
				setEnergyRegen(0.5625);
				upgrading="";
			}
			if(queue ==0  && upgrading.equals("PlanetaryFortress") && progress==0 && buildtime==50) {
				setName(upgrading);
				upgrading="";
			}
			//if(constructing != null) System.out.println(model.printTime() + "   <Base> Progress "+constructing.getProgress()+" isComplete:"+constructing.isComplete());
			if(constructing != null && constructing.isComplete()) {
				//System.out.println(model.printTime() + "   <Base:update> SCV Complete");
				if(constructing.getName().equals("SCV")) {
					//System.out.println(model.printTime() + "   <Base:update> name is \"SCV\"");
					if(!addSCVtoMinerals((SCV)constructing)) {
						System.out.println("model.printTime() + <Base> SCV not added to minerals ...");
					} else {
						constructing=null;
					}
				}
			}
			//
			if(idleSCVs.size()>0 && scvCount()<24) {
				if(!addSCVtoMinerals(idleSCVs.remove(0))) {
					//System.out.println(model.printTime() + "   <Base:update> SCV failed to move from idle");
				}
			}
			if(busySCVs.size()>0) {
				for(int i = 0 ; i < busySCVs.size();i++) {
					busySCVs.get(i).update();
					if(busySCVs.get(i).getJob()==0) {
						// TODO add back to mineral patches...
						addSCVtoMinerals(busySCVs.get(i));
						busySCVs.remove(i);
						i--;
						//System.out.println(model.printTime() + "   <Base:update> Adding SCV back to minerals");
					}
				}
			}
		} 
	}

	public int idleSCVCount() {
		return idleSCVs.size();
	}
	
	public boolean addSCV(SCV scv) {
		int lowest=0;
		int low=3;
		int total=0;
		for(int i=0;i<8;i++) {
			total+=patches[i].getSCVCount();
			if(patches[i].getSCVCount()<low) {
				low = patches[i].getSCVCount();
				lowest = i;
			}
		}
		scv.setResource(patches[lowest]);
		if(low<3) {
			//System.out.println("<Base:addDrone> Adding Drone to patch " + lowest);
			patches[lowest].addSCV(scv);
		} else {
			//System.out.println("<Base:addDrone> Adding Drone to idle list");
			scv.setJob(3);
			idleSCVs.add(scv);
		}
		//System.out.println(model.printTime() + "   <Base:addDrone> Drones = " + total);
		return true;

	}
	
	public boolean addSCVtoQueue() {
		//System.out.println(model.printTime() + "   <Base:addSCVtoQueue> queue = " + queue + " complete = " + complete);
		if(queue==0 && complete) {
			queue++;
			progress=0;
			buildtime = model.getTime("SCV");
			//System.out.println(model.printTime() + "   <Base:addSCVtoQueue> buildtime = " + buildtime);
			model.spendMinerals(model.getMineralCost("SCV"));
			model.addFood(model.getFood("SCV"));
			constructing=new SCV(model);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addMule() {
		if(energy>=50) {
			int most=0;
			int patch=-1;
			boolean found=false;
			//while(i < 8 && !found) {
			for(int i = 0; i < 8; i++) {
				if(!patches[i].hasMule() && patches[i].resource>most) {
					most = patches[i].resource;
					patch = i;
				}
			}
			if(patch>=0) {
				patches[patch].addMule(model);
				found=true;
			}
			return found;
		} else {
			return false;
		}
	}
	
	public boolean upgrade(String name) {
		//System.out.println(model.printTime() + "   <Base> Upgrading Base to " + name);
		if(queue == 0 && complete) {
			queue++;
			progress=0;
			buildtime = model.getTime(name);
			model.spendMinerals(model.getMineralCost(name));
			model.spendGas(model.getGasCost(name));
			upgrading = name;
			return true;
		} else {
			return false;
		}
	}

	public boolean addSCVtoMinerals(SCV scv) {
		if(scv.isComplete()) {
			int lowest=0;
			int low=3;
			for(int i=0;i<8;i++) {
				//total+=patches[i].getSCVCount();
				if(patches[i].getSCVCount()<low && patches[i].resource>0) {
					low = patches[i].getSCVCount();
					lowest = i;
				}
			}
			scv.setResource(patches[lowest]);
			if(low<3) {
				//System.out.println(model.printTime() + "   <Base:addSCV> Adding SCV to patch " + lowest);
				patches[lowest].addSCV(scv);
			} else {
				//System.out.println(model.printTime() + "   <Base:addSCV> Adding SCV to idle list");
				scv.setJob(3);
				idleSCVs.add(scv);
			}
			//System.out.println(model.printTime() + "   <Base:addDrone> Drones = " + total);
			return true;
		} else {
			System.out.println("<Base> Why are you adding unfinished SCVs here?");
			return false;
		}
	}

	public boolean setSCVBuilding(int duration) {
		SCV s;
		if(idleSCVs.size() > 0) {
			s = idleSCVs.remove(0);
		} else {
			int most=0;
			int patch=-1;
			for(int i = 0; i < 8; i++) {
				if(patches[i].getSCVCount() > most) {
					most = patches[i].getSCVCount();
					patch=i;
				}
			}
			if(patch>=0) {
				s = patches[patch].removeSCV();
			} else {
				return false;
			}
		}
		if(s!=null) {
			s.setJobtime(duration, 2);
			busySCVs.add(s);
			//System.out.println(model.printTime() + "   <Base> SCV jobtime set to " + duration + " and added to busy list");
			return true;
		} else {
			System.out.println(model.printTime() + "   <Base> Null SCV found");
			return false;
		}
		
	}

	public boolean removeSCV() {
		int patch=-1;
		int mostDrones=0;
		for(int i=0;i<8;i++) {
			if(patches[i].getSCVCount()>mostDrones) {
				mostDrones = patches[i].getSCVCount();
				patch = i;
			}
		}
		if(patch>=0) {
			SCV s = patches[patch].removeSCV();
			s = null; // deadded
			model.addFood(-1);
			return true;
		} else {
			return false;
		}

	}
	public SCV transferSCV() {
		int patch=-1;
		int mostSCVs=0;
		for(int i=0;i<8;i++) {
			if(patches[i].getSCVCount()>mostSCVs) {
				mostSCVs = patches[i].getSCVCount();
				patch = i;
			}
		}
		if(patch>=0) {
			SCV d = patches[patch].removeSCV();
			return d;
		} else {
			System.out.println(model.printTime() + "   <Base:transferSCV> Returning null scv");
			return null;
		}
		
	}

	public boolean addRefinery() {
		int patch=-1;
		int most=0;
		for(int i = 0;i < 8;i++) {
			if(patches[i].getSCVCount()>most) {
				most = patches[i].getSCVCount();
				patch = i;
			}
		}
		if(patch>=0) {
			if(gas[0].hasRefinery()) {
				if(gas[1].hasRefinery()) {
					System.out.println(model.printTime() + "   <Base> Error: Both geysers have been taken!");
					return false;
				} else {
					gas[1].addRefinery(model);
					//System.out.println(model.printTime() + "   <Base> Building Refinery on Geyser number 1");
					model.spendMinerals(model.getMineralCost("Refinery"));
					return true;
				}
			} else {
				gas[0].addRefinery(model);
				//System.out.println(model.printTime() + "   <Base> Building Refinery on Geyser number 0");
				model.spendMinerals(model.getMineralCost("Refinery"));
				return true;
			}
		} else {
			System.out.println("Add code here for transfering SCV from another base... just cba atm");
			return false;
		}
	}

	
	public int scvCount() {//just on minerals
		int t=0;
		for(int i = 0;i<8;i++){
			t+=patches[i].scvs;
		}
		return t;
	}
	public int scvCountGas() {// on gas
		int t=0;
		t+=gas[0].getSCVCount();
		t+=gas[1].getSCVCount();
		return t;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public double getEnergyRegen() {
		return energyRegen;
	}

	public void setEnergyRegen(double energyRegen) {
		this.energyRegen = energyRegen;
	}

	public void useEnergy(int i) {
		energy-=i;
	}

	public int remainingMinerals() {
		int total = 0;
		for(int i = 0; i  < 8; i++) {
			total += patches[i].resource;
		}
		return total;
	}

	public int freeGeysers() {
		int g = 0;
		if(!gas[0].hasRefinery()) g++;
		if(!gas[1].hasRefinery()) g++;
		return g;
	}

	public int freeRefineries() {
		int g=0;
		if(complete) {
			if(gas[0].scvs<3 && gas[0].hasCompleteRefinery()) g++;
			if(gas[1].scvs<3 && gas[1].hasCompleteRefinery()) g++;
		}
		return g;
	}

	public boolean transferDroneToGas() { // just transfer 1
//		System.out.println(model.printTime() + "    <Base:transferDronesToGas>");
		SCV d;
		int m=0;
		int p=0;
		int g=0;
		if(gas[0].hasCompleteRefinery() && gas[0].getSCVCount()<3) {
			g=0;
		} else if(gas[1].hasCompleteRefinery() && gas[1].getSCVCount()<3) {
			g=1;
		} else {
//			System.out.println("neither geyser has a complete refinery... weird huh");
			return false;
		}
		if(scvCount()<1) {
//			System.out.println("no scvs to pull from minerals");
			return false;
		}

		m=0;
		for(int i = 0;i < 8;i++) {
			if(patches[i].scvs>=m) {
				m=patches[i].scvs;
				p=i;
			}
		}
		d = patches[p].removeSCV();

		if(gas[g].addSCV(d)) {
			return true;
		} else {
//			System.out.println("<Base> Couldn't add SCV to gas");
			return false;
		}
	}

	public void setSCVScouting(int duration) {
		int most=0;
		int patch=-1;
		for(int i = 0; i < 8; i++) {
			if(patches[i].scvs > most) {
				most = patches[i].scvs;
				patch = i;
			}
		}
		SCV s = patches[patch].removeSCV();
		s.setJobtime(duration, 2);
		busySCVs.add(s);
		
	}

	public boolean transferSCVOffGas() {
		int g=-1;
		int d=0;
		if(gas[1].hasCompleteRefinery()) {
			d = gas[1].getSCVCount();
			g=1;
		}
		if(gas[0].hasCompleteRefinery() && gas[0].getSCVCount()>d) {
			d = gas[0].getSCVCount();
			g=0;
		}
		if(g>=0) {
			SCV r = gas[g].removeSCV();
			addSCV(r); // to minerals
			return true;
		} else {
			return false;
		}
	}

}
