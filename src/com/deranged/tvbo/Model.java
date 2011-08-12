package com.deranged.tvbo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Model {

	// TIMINGS //////////////////////////////////
	private int time = 0;
	private int maxTime = 10*60;
	private int minerals;
	private int totalMineralsMined;
	private int totalGasMined;
	private int gas;
	private int lastTime = 0;
	private int supply = 0;
	private int food = 0;

	// GRAPHICS PARAMETERS //////////////////////
	private int width;//=1050;
	private int height;//=900;
	private double scale = 3.0;
	private int scroll = 0;
	private int border = 40;
	private int spacing = 30; // distance between rows
	private int thickness = 15; // height of each bar

	// HASH MAPS ////////////////////////////////
	private HashMap<String, Integer> mineralCost;
	private HashMap<String, Integer> gasCost;
	private HashMap<String, Integer> times;
	private HashMap<String, String> prereqs;
	private HashMap<String, Integer> foods;
	private HashMap<String, Integer> supplies;
	private HashMap<String, String> build;
	private HashMap<String, String> tech;

	// ARRAYLISTS ///////////////////////////////
	private ArrayList<Base> bases;
	private ArrayList<SCObject> objects;
	private ArrayList<SCAction> actions;
	// GRAPHS ///////////////////////////////////
	private int[] mineralGraph;
	private int[] gasGraph;
	private int[] energyGraph;
	//private String totalsText = "";
	// MARQUEE SELECTION ////////////////////////
	int mX1=0;
	int mY1=0;
	int mX2=0;
	int mY2=0;

	public Model() {
		minerals=50;
		gas=0;
		totalMineralsMined=0;
		totalGasMined=0;
		bases = new ArrayList<Base>();
		objects = new ArrayList<SCObject>();
		actions = new ArrayList<SCAction>();
		mineralGraph = new int[maxTime];
		gasGraph = new int[maxTime];
		energyGraph = new int[maxTime];
		setUpHashes();
	}

	public void setup() {
		Base b = new Base(this);
		b.start();
		bases.add(b);
		//actions.add(new SCActionBuildSCV(this, 0, 1));
		//		SCActionBuildAddon a = new SCActionBuildAddon(this, 200, 3, "TechLab");
		//		a.setBuilding("Barracks");
		//		actions.add(a);
	}

	public void clear() {
		reset();
		actions.clear();
	}

	public void play() {
		totalMineralsMined=0;
		totalGasMined=0;
		int t = 0;
		int totalEnergy=0;
		int p = 0;
		lastTime = 0;
		int s = actions.size();
		//System.out.println("___________________________________________________");
		while(time < maxTime) {
			//System.out.println(printTime() + "   <Model> Minerals " + minerals);
			for(int i = 0; i < s; i++) {
				t = actions.get(i).getStartTime();
				p = actions.get(i).getPreactionTime();
				if(p == time) {
					actions.get(i).preaction();
				}
				if(t == time) {
					actions.get(i).setSupplyPoint(food+"/"+supply);
					actions.get(i).execute();
					if(actions.get(i).isComplete() && t+actions.get(i).getDuration()>lastTime) {
						lastTime = t+actions.get(i).getDuration();
					}
				}
			}
			for(int i = 0; i < bases.size(); i++) {
				bases.get(i).update();
			}

			for(int i = 0; i < objects.size(); i++) {
				objects.get(i).update();
			}
			mineralGraph[time] = minerals;
			gasGraph[time] = gas;
			totalEnergy=0;
			for(int b = 0; b < bases.size(); b++) {
				totalEnergy+= bases.get(b).getEnergy();
			}
			energyGraph[time] = totalEnergy;

			time++;
		}
		//setTotalsText();
		//maxTime=lastTime+180;
		/*
		SCStructure barracks;
		SCAddon techlab;
		for(int i = 0 ; i < objects.size(); i++) {
			if(objects.get(i).getName().equals("Barracks")) {
				barracks = (SCStructure)objects.get(i);
				System.out.println("Barracks - " + barracks.getAddonName());
			}
			if(objects.get(i).getName().equals("TechLab")) {
				techlab = (SCAddon)objects.get(i);
				System.out.println("TechLab  - " + techlab.getAttachedTo());
			}

		}*/
		//System.out.println("Total Minerals Mined : " + totalMineralsMined);

	}

	public void reset() {
		bases.clear();
		objects.clear();
		for(int i = 0; i < actions.size(); i++) {
			actions.get(i).reset();
		}
		time = 0;
		minerals = 50;
		gas = 0;
		food = 0;
		supply = 0;
		/*
		for(int i = 0; i < maxTime; i++) {
			mineralGraph[i] = 0;
			gasGraph[i] = 0;
			energyGraph[i] = 0;
		}*/
		//		mineralGraph = new int[maxTime];
		//		gasGraph = new int[maxTime];
		//		energyGraph = new int[maxTime];
		Base b = new Base(this);
		b.start();
		bases.add(b);
	}


	public void setUpHashes() {
		mineralCost = new HashMap<String, Integer>();
		gasCost = new HashMap<String, Integer>();
		foods = new HashMap<String, Integer>();
		supplies = new HashMap<String, Integer>();
		times = new HashMap<String, Integer>();
		prereqs = new HashMap<String, String>();
		build = new HashMap<String, String>();
		tech = new HashMap<String, String>();

		// MINERAL COST //////////////////////////////////////////////////
		//    UNITS     //
		mineralCost.put("SCV", 50);
		mineralCost.put("Marine", 50);
		mineralCost.put("Marauder", 100);
		mineralCost.put("Reaper", 50);
		mineralCost.put("Ghost", 200);
		mineralCost.put("Hellion", 100);
		mineralCost.put("SiegeTank", 150);
		mineralCost.put("Thor", 300);
		mineralCost.put("Viking", 150);
		mineralCost.put("Medivac", 100);
		mineralCost.put("Banshee", 150);
		mineralCost.put("Raven", 100);
		mineralCost.put("Battlecruiser", 400);
		// BUILDINGS /////
		mineralCost.put("SupplyDepot", 100);
		mineralCost.put("CommandCenter", 400);
		mineralCost.put("Refinery", 75);
		mineralCost.put("Barracks", 150);
		mineralCost.put("Bunker", 100);
		mineralCost.put("EngineeringBay", 125);
		mineralCost.put("Factory", 150);
		mineralCost.put("Starport", 150);
		mineralCost.put("OrbitalCommand", 150);
		mineralCost.put("PlanetaryFortress", 150);
		mineralCost.put("GhostAcademy", 150);
		mineralCost.put("FusionCore", 150);
		mineralCost.put("Armory", 150);
		mineralCost.put("TechLab", 50);
		mineralCost.put("Reactor", 50);
		mineralCost.put("MissileTurret", 100);
		mineralCost.put("SensorTower", 125);
		// research //
		mineralCost.put("CombatShield", 100);
		mineralCost.put("StimPack", 100);
		mineralCost.put("ConcussiveShells", 50);
		mineralCost.put("NitroPack", 50);
		mineralCost.put("InfernalPreigniter", 150);
		mineralCost.put("SiegeTech", 100);
		mineralCost.put("250mmStrikeCannons", 150);
		mineralCost.put("CaduceusReactor", 100);
		mineralCost.put("CorvidReactor", 150);
		mineralCost.put("DurableMaterials", 150);
		mineralCost.put("SeekerMissile", 150);
		mineralCost.put("CloakingField", 200);
		
		mineralCost.put("InfantryWeaponsLevel1", 100);
		mineralCost.put("InfantryWeaponsLevel2", 175);
		mineralCost.put("InfantryWeaponsLevel3", 250);
		mineralCost.put("InfantryArmorLevel1", 100);
		mineralCost.put("InfantryArmorLevel2", 175);
		mineralCost.put("InfantryArmorLevel3", 250);
		mineralCost.put("NeosteelFrame", 100);
		mineralCost.put("BuildingArmor", 150);
		mineralCost.put("HiSecAutoTracking", 100);
		
		mineralCost.put("VehicleWeaponsLevel1", 100);		
		mineralCost.put("VehicleWeaponsLevel2", 175);		
		mineralCost.put("VehicleWeaponsLevel3", 250);		
		mineralCost.put("VehicleArmorLevel1", 100);		
		mineralCost.put("VehicleArmorLevel2", 175);		
		mineralCost.put("VehicleArmorLevel3", 250);
		mineralCost.put("ShipWeaponsLevel1", 100);		
		mineralCost.put("ShipWeaponsLevel2", 175);		
		mineralCost.put("ShipWeaponsLevel3", 250);		
		mineralCost.put("ShipArmorLevel1", 150);		
		mineralCost.put("ShipArmorLevel2", 225);		
		mineralCost.put("ShipArmorLevel3", 300);
		
		mineralCost.put("PersonalCloaking", 120);
		mineralCost.put("Nuke", 60);
		mineralCost.put("MoebiusReactor", 80);
		mineralCost.put("BehemothReactor", 80);
		mineralCost.put("WeaponRefit", 60);

		// GAS COST //////////////////////////////////////////////////////
		// UNITS //
		gasCost.put("SCV", 0);
		gasCost.put("Marine", 0);
		gasCost.put("Marauder", 25);
		gasCost.put("Reaper", 50);
		gasCost.put("Ghost", 100);
		gasCost.put("Hellion", 0);
		gasCost.put("SiegeTank", 125);
		gasCost.put("Thor", 200);
		gasCost.put("Viking", 75);
		gasCost.put("Medivac", 100);
		gasCost.put("Banshee", 100);
		gasCost.put("Raven", 200);
		gasCost.put("Battlecruiser", 300);
		// BUILDINGS //
		gasCost.put("SupplyDepot", 0);
		gasCost.put("Barracks", 0);
		gasCost.put("Factory", 100);
		gasCost.put("Starport", 100);
		gasCost.put("OrbitalCommand", 0);
		gasCost.put("PlanetaryFortress", 150);
		gasCost.put("GhostAcademy", 50);
		gasCost.put("FusionCore", 150);
		gasCost.put("Armory", 100);
		gasCost.put("TechLab", 25);
		gasCost.put("Reactor", 50);
		gasCost.put("SensorTower", 100);
		// research //
		gasCost.put("CombatShield", 100);
		gasCost.put("StimPack", 100);
		gasCost.put("ConcussiveShells", 50);
		gasCost.put("NitroPack", 50);
		gasCost.put("InfernalPreigniter", 150);
		gasCost.put("SiegeTech", 100);
		gasCost.put("250mmStrikeCannons", 150);
		gasCost.put("CaduceusReactor", 100);
		gasCost.put("CorvidReactor", 150);
		gasCost.put("DurableMaterials", 150);
		gasCost.put("SeekerMissile", 150);
		gasCost.put("CloakingField", 200);
		
		gasCost.put("InfantryWeaponsLevel1", 100);
		gasCost.put("InfantryWeaponsLevel2", 175);
		gasCost.put("InfantryWeaponsLevel3", 250);
		gasCost.put("InfantryArmorLevel1", 100);
		gasCost.put("InfantryArmorLevel2", 175);
		gasCost.put("InfantryArmorLevel3", 250);
		gasCost.put("NeosteelFrame", 100);
		gasCost.put("BuildingArmor", 150);
		gasCost.put("HiSecAutoTracking", 100);
		
		gasCost.put("VehicleWeaponsLevel1", 100);		
		gasCost.put("VehicleWeaponsLevel2", 175);		
		gasCost.put("VehicleWeaponsLevel3", 250);		
		gasCost.put("VehicleArmorLevel1", 100);		
		gasCost.put("VehicleArmorLevel2", 175);		
		gasCost.put("VehicleArmorLevel3", 250);
		gasCost.put("ShipWeaponsLevel1", 100);		
		gasCost.put("ShipWeaponsLevel2", 175);		
		gasCost.put("ShipWeaponsLevel3", 250);		
		gasCost.put("ShipArmorLevel1", 150);		
		gasCost.put("ShipArmorLevel2", 225);		
		gasCost.put("ShipArmorLevel3", 300);
		
		gasCost.put("PersonalCloaking", 120);
		gasCost.put("Nuke", 60);
		gasCost.put("MoebiusReactor", 80);
		gasCost.put("BehemothReactor", 80);
		gasCost.put("WeaponRefit", 60);

		// TIMES /////////////////////////////////////////////////////////
		// UNITS //
		times.put("SCV", 17);
		times.put("Marine", 25);
		times.put("Marauder", 30);
		times.put("Reaper", 45);
		times.put("Ghost", 40);
		times.put("Hellion", 30);
		times.put("SiegeTank", 45);
		times.put("Thor", 60);
		times.put("Viking", 42);
		times.put("Medivac", 42);
		times.put("Banshee", 60);
		times.put("Raven", 60);
		times.put("Battlecruiser", 90);
		times.put("Mule", 90);
		times.put("TransferToGas", 20);
		times.put("TransferOffGas", 20);
		times.put("Maynard", 20);
		// BUILDINGS //
		times.put("CommandCenter", 100);
		times.put("Refinery", 30);
		times.put("SupplyDepot", 30);
		times.put("Barracks", 60);
		times.put("Bunker", 40);
		times.put("Factory", 60);
		times.put("Starport", 50);
		times.put("EngineeringBay", 35);
		times.put("OrbitalCommand", 35);
		times.put("PlanetaryFortress", 50);
		times.put("CalldownSupply", 30);
		times.put("GhostAcademy", 40);
		times.put("FusionCore", 65);
		times.put("Armory", 65);
		times.put("TechLab", 25);
		times.put("Reactor", 50);
		times.put("SensorTower", 25);
		times.put("MissileTurret", 25);
		//times.put("Lift", 25);
		times.put("Scan", 30);
		// research //
		times.put("CombatShield", 110);
		times.put("StimPack", 170);
		times.put("ConcussiveShells", 60);
		times.put("NitroPack", 100);
		times.put("InfernalPreigniter", 110);
		times.put("SiegeTech", 80);
		times.put("250mmStrikeCannons", 110);
		times.put("CaduceusReactor", 80);
		times.put("CorvidReactor", 110);
		times.put("DurableMaterials", 110);
		times.put("SeekerMissile", 110);
		times.put("CloakingField", 110);
		
		times.put("InfantryWeaponsLevel1", 160);
		times.put("InfantryWeaponsLevel2", 190);
		times.put("InfantryWeaponsLevel3", 220);
		times.put("InfantryArmorLevel1", 160);
		times.put("InfantryArmorLevel2", 190);
		times.put("InfantryArmorLevel3", 220);
		times.put("NeosteelFrame", 110);
		times.put("BuildingArmor", 140);
		times.put("HiSecAutoTracking", 80);
		
		times.put("VehicleWeaponsLevel1", 160);		
		times.put("VehicleWeaponsLevel2", 190);		
		times.put("VehicleWeaponsLevel3", 220);		
		times.put("VehicleArmorLevel1", 160);		
		times.put("VehicleArmorLevel2", 190);		
		times.put("VehicleArmorLevel3", 220);
		times.put("ShipWeaponsLevel1", 160);		
		times.put("ShipWeaponsLevel2", 190);		
		times.put("ShipWeaponsLevel3", 220);		
		times.put("ShipArmorLevel1", 160);		
		times.put("ShipArmorLevel2", 190);		
		times.put("ShipArmorLevel3", 220);
		
		times.put("PersonalCloaking", 120);
		times.put("Nuke", 60);
		times.put("MoebiusReactor", 80);
		times.put("BehemothReactor", 80);
		times.put("WeaponRefit", 60);

		// FOOD //////////////////////////////////////////////////////////
		foods.put("SCV", 1);
		foods.put("Marine", 1);
		foods.put("Marauder", 2);
		foods.put("Reaper", 1);
		foods.put("Ghost", 2);
		foods.put("Hellion", 2);
		foods.put("SiegeTank", 3);
		foods.put("Thor", 6);
		foods.put("Viking", 2);
		foods.put("Medivac", 2);
		foods.put("Banshee", 3);
		foods.put("Raven", 2);
		foods.put("Battlecruiser", 6);
		// SUPPLY ////////////////////////////////////////////////////////
		supplies.put("SupplyDepot", 8);
		// PREREQ ////////////////////////////////////////////////////////
		prereqs.put("Barracks", "SupplyDepot");
		prereqs.put("Factory", "Barracks");
		prereqs.put("Bunker", "Barracks");
		prereqs.put("GhostAcademy", "Barracks");
		prereqs.put("Starport", "Factory");
		prereqs.put("FusionCore", "Starport");
		prereqs.put("Armory", "Factory");
		prereqs.put("OrbitalCommand", "Barracks");
		prereqs.put("Bunker", "Barracks");
		prereqs.put("Ghost", "GhostAcademy");
		prereqs.put("Thor", "Armory");
		prereqs.put("Battlecruiser", "FusionCore");
		prereqs.put("MissileTurret", "EngineeringBay");
		prereqs.put("PlanetaryFortress", "EngineeringBay");
		prereqs.put("SensorTower", "EngineeringBay");
		// research
		prereqs.put("NitroPack", "Factory");
		prereqs.put("InfantryWeaponsLevel2", "Armory");
		prereqs.put("InfantryWeaponsLevel3", "Armory");
		prereqs.put("InfantryArmorLevel2", "Armory");
		prereqs.put("InfantryArmorLevel3", "Armory");
		// BUILD /////////////////////////////////////////////////////////
		build.put("Marine", "Barracks");
		build.put("Marauder", "Barracks");
		build.put("Reaper", "Barracks");
		build.put("Ghost", "Barracks");
		build.put("Hellion", "Factory");
		build.put("SiegeTank", "Factory");
		build.put("Thor", "Factory");
		build.put("Medivac", "Starport");
		build.put("Viking", "Starport");
		build.put("Banshee", "Starport");
		build.put("Raven", "Starport");
		build.put("Battlecruiser", "Starport");
		  // research
		build.put("CombatShield", "Barracks");
		build.put("StimPack", "Barracks");
		build.put("ConcussiveShells", "Barracks");
		build.put("NitroPack", "Barracks");
		build.put("InfernalPreigniter", "Factory");
		build.put("SiegeTech", "Factory");
		build.put("250mmStrikeCannons", "Factory");
		build.put("CaduceusReactor", "Starport");
		build.put("CorvidReactor", "Starport");
		build.put("DurableMaterials", "Starport");
		build.put("SeekerMissile", "Starport");
		build.put("CloakingField", "Starport");
		
		build.put("InfantryWeaponsLevel1", "EngineeringBay");
		build.put("InfantryWeaponsLevel2", "EngineeringBay");
		build.put("InfantryWeaponsLevel3", "EngineeringBay");
		build.put("InfantryArmorLevel1", "EngineeringBay");
		build.put("InfantryArmorLevel2", "EngineeringBay");
		build.put("InfantryArmorLevel3", "EngineeringBay");
		build.put("NeosteelFrame", "EngineeringBay");
		build.put("BuildingArmor", "EngineeringBay");
		build.put("HiSecAutoTracking", "EngineeringBay");
		
		build.put("VehicleWeaponsLevel1", "Armory");		
		build.put("VehicleWeaponsLevel2", "Armory");		
		build.put("VehicleWeaponsLevel3", "Armory");		
		build.put("VehicleArmorLevel1", "Armory");		
		build.put("VehicleArmorLevel2", "Armory");		
		build.put("VehicleArmorLevel3", "Armory");
		build.put("ShipWeaponsLevel1", "Armory");		
		build.put("ShipWeaponsLevel2", "Armory");		
		build.put("ShipWeaponsLevel3", "Armory");		
		build.put("ShipArmorLevel1", "Armory");		
		build.put("ShipArmorLevel2", "Armory");		
		build.put("ShipArmorLevel3", "Armory");
		
		build.put("PersonalCloaking", "GhostAcademy");
		build.put("BehemothReactor", "FusionCore");
		build.put("WeaponRefit", "FusionCore");
		build.put("MoebiusReactor", "GhostAcademy");
		// TECH //////////////////////////////////////////////////////////
		tech.put("Marauder", "TechLab");
		tech.put("Reaper", "TechLab");
		tech.put("Ghost", "TechLab");
		tech.put("SiegeTank", "TechLab");
		tech.put("Thor", "TechLab");
		tech.put("Banshee", "TechLab");
		tech.put("Raven", "TechLab");
		tech.put("Battlecruiser", "TechLab");
		//
		tech.put("CombatShield", "TechLab");
		tech.put("StimPack", "TechLab");
		tech.put("ConcussiveShells", "TechLab");
		tech.put("NitroPack", "TechLab");
		tech.put("InfernalPreigniter", "TechLab");
		tech.put("SiegeTech", "TechLab");
		tech.put("250mmStrikeCannons", "TechLab");
		tech.put("CaduceusReactor", "TechLab");
		tech.put("CorvidReactor", "TechLab");
		tech.put("DurableMaterials", "TechLab");
		tech.put("SeekerMissile", "TechLab");
		tech.put("CloakingField", "TechLab");
	}

	public void addUnitAction(String dropDown) {
		int x=maxTime-1;
		int y=1;
		int dur = getTime(dropDown);
		//System.out.println(dropDown);
		SCAction action;
		if(dropDown.equals("SCV")) {
			action = new SCActionBuildSCV(this, x, y);
		} else if(dropDown.equals("Mule")) {
			action = new SCActionMule(this, x, y);
		} else if(dropDown.equals("TransferToGas")) {
			action = new SCActionTransferToGas(this, x, y);
		} else if(dropDown.equals("TransferOffGas")) {
			action = new SCActionTransferOffGas(this, x, y);
		} else if(dropDown.equals("Maynard")) {
			action = new SCActionMaynard(this, x, y);
		} else if(dropDown.equals("Scout")) {
			action = new SCActionScout(this, x, y);
		} else {
			action = new SCActionBuildUnit(this, x, y, dropDown);
			// TODO set to SCActionBuildUnit
		}

		actions.add(action);
		this.reset();
		this.play();

		if(action.isComplete()) {
			while(action.getStartTime()>0 && action.isComplete()) {
				action.addStartTime(-1);
				this.reset();
				this.play();
				if(!action.isComplete()) {
					action.addStartTime(1);
				}
			}
		} else {
			action.setStartTime(lastTime);
		}
		x = action.getStartTime();
		int size = actions.size();
		int x2;
		int end2;
		//if(markY>=0) {
		//	y = markY;
		//}
		int end = x+dur;

		boolean space = false;
		/*
		 * 1
		 * |-----------|    ax < x && aend > end 
		 *   |----|
		 * 
		 * 2
		 * |------|         ax < x && aend > x
		 *     |-------|
		 * 
		 * 3
		 *     |-------|   x < ax && ax < end && aend > end
		 * |------|
		 * 
		 * 4
		 *    |----|       ax > x && aend < end
		 * |-----------|
		 * 
		 * 
		 */
		SCAction action2;
		while(!space) {
			space=true;
			for(int i = 0; i < size-1;i++) {
				action2 = actions.get(i);
				x2=action2.getStartTime();
				end2=action2.getStartTime()+action2.getDuration();
				if(action2.getY() == y) {
					if(x <= x2 && x2<end && end<=end2) {
						//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
						space=false;
					} else if(x2<=x && x<end2 && end2<=end) {
						//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
						space=false;
					} else if(x<x2 && end2<end) {
						//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
						space=false;
					} else if(x2<x && end<end2) {
						//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
						space=false;
					} else {
						//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2);
					}
				}
			}
			if(!space) {
				y++;
			}
		}
		action.setY(y);

	}

	public void addBuildingAction(String dropDown) {
		int x=maxTime-1;
		int y=1;
		int dur = getTime(dropDown);
		//System.out.println(dropDown);
		SCAction action;
		if(dropDown.equals("OrbitalCommand")) {
			action = new SCActionUpgradeBase(this, x, y, "OrbitalCommand");
		} else if(dropDown.equals("PlanetaryFortress")) {
			action = new SCActionUpgradeBase(this, x, y, "PlanetaryFortress");
		} else if(dropDown.equals("CalldownSupply")) {
			action = new SCActionCalldownSupply(this, x, y);
		} else if(dropDown.equals("Refinery")) {
			action = new SCActionBuildRefinery(this, x, y);
		} else if(dropDown.equals("TechLab")) {
			action = new SCActionBuildAddon(this, x, y, dropDown);
		} else if(dropDown.equals("Reactor")) {
			action = new SCActionBuildAddon(this, x, y, dropDown);
		} else if(dropDown.equals("LiftBarracks")) {
			action = new SCActionLift(this, x, y, "Barracks");
		} else if(dropDown.equals("LiftFactory")) {
			action = new SCActionLift(this, x, y, "Factory");
		} else if(dropDown.equals("LiftStarport")) {
			action = new SCActionLift(this, x, y, "Starport");
		} else if(dropDown.equals("LandBarracks")) {
			action = new SCActionLand(this, x, y, "Barracks");
		} else if(dropDown.equals("LandFactory")) {
			action = new SCActionLand(this, x, y, "Factory");
		} else if(dropDown.equals("LandStarport")) {
			action = new SCActionLand(this, x, y, "Starport");
		} else if(dropDown.equals("CommandCenter")) {
			action = new SCActionBuildBase(this, x, y);
		} else if(dropDown.equals("Scan")) {
			action = new SCActionScan(this, x, y);
		} else {
			action = new SCActionBuilding(this, x, y, dropDown);
		}

		actions.add(action);
		this.reset();
		this.play();

		if(action.isComplete()) {
			while(action.getStartTime()>0 && action.isComplete()) {
				action.addStartTime(-1);
				this.reset();
				this.play();
				if(!action.isComplete()) {
					action.addStartTime(1);
				}
			}
		} else {
			action.setStartTime(lastTime);
		}
		x = action.getStartTime();
		int size = actions.size();
		int x2;
		int end2;
		//if(markY>=0) {
		//	y = markY;
		//}
		int end = x+dur;
		boolean space = false;
		SCAction action2;
		while(!space) {
			space=true;
			for(int i = 0; i < size-1;i++) {
				action2 = actions.get(i);
				x2=action2.getStartTime();
				end2=action2.getStartTime()+action2.getDuration();
				if(action2.getY() == y) {
					if(x <= x2 && x2<end && end<=end2) {
						space=false;
					} else if(x2<=x && x<end2 && end2<=end) {
						space=false;
					} else if(x<x2 && end2<end) {
						space=false;
					} else if(x2<x && end<end2) {
						space=false;
					} 
				}
			}
			if(!space) {
				y++;
			}
		}
		action.setY(y);

	}
	


	public void addResearchAction(String dropDown) {
		int x=maxTime-1;
		int y=1;
		int dur = getTime(dropDown);
		//System.out.println(dropDown);
		SCAction action;
		
		action = new SCActionResearch(this, x, y, dropDown);

		actions.add(action);
		this.reset();
		this.play();

		if(action.isComplete()) {
			while(action.getStartTime()>0 && action.isComplete()) {
				action.addStartTime(-1);
				this.reset();
				this.play();
				if(!action.isComplete()) {
					action.addStartTime(1);
				}
			}
		} else {
			action.setStartTime(lastTime);
		}
		x = action.getStartTime();
		int size = actions.size();
		int x2;
		int end2;
		//if(markY>=0) {
		//	y = markY;
		//}
		int end = x+dur;
		boolean space = false;
		SCAction action2;
		while(!space) {
			space=true;
			for(int i = 0; i < size-1;i++) {
				action2 = actions.get(i);
				x2=action2.getStartTime();
				end2=action2.getStartTime()+action2.getDuration();
				if(action2.getY() == y) {
					if(x <= x2 && x2<end && end<=end2) {
						space=false;
					} else if(x2<=x && x<end2 && end2<=end) {
						space=false;
					} else if(x<x2 && end2<end) {
						space=false;
					} else if(x2<x && end<end2) {
						space=false;
					} 
				}
			}
			if(!space) {
				y++;
			}
		}
		action.setY(y);

	}

	public boolean save(File file) {
		String xml;
		SCAction action;
		xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xml+="<Actions>\n";
		int s = actions.size();
		int index=-1;
		for(int i = 0;i < s ; i++) {
			action = actions.get(i);
			String c = action.getClass().toString();
			index=c.lastIndexOf(".");
			c = c.substring(index+1, c.length());
			xml+="  <Action Class=\""+c+"\">\n";
			xml+="    <Name>"+action.getName()+"</Name>\n";
			xml+="    <StartTime>"+action.getStartTime()+"</StartTime>\n";
			xml+="    <Y>"+action.getY()+"</Y>\n";
			//xml+="    <Duration>"+action.getDuration()+"</Duration>\n";
			xml+="    <Option>"+action.getOption()+"</Option>\n";
			xml+="  </Action>\n";
		}
		xml+="</Actions>\n";

		//return xml;
		// fileoutput stream xml
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter( new FileWriter(file));
			writer.write(xml);
		} catch (IOException e) {
			System.out.println("Can't write XML to file - " + e);
			return false;
		} finally {
			try {
				if(writer!=null) {
					writer.close();
				}
			} catch(IOException e) {
				System.out.println("Something else didn't work - " + e);
				return false;
			}
		}

		return true;
	}
	public boolean load(File file) {
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		actions.clear();
		Element docEle = doc.getDocumentElement();
		NodeList nl = docEle.getElementsByTagName("Action");
		if(nl != null && nl.getLength() > 0) {
			int size = nl.getLength();
			for(int i = 0; i < size;i++) {
				Element element = (Element)nl.item(i);
				actions.add(getActionFromXML(element));
			}
		}
		return true;
	}

	public SCAction getActionFromXML(Element element) {

		String c = element.getAttribute("Class");
		int startTime = getIntValue(element,"StartTime");
		int y = getIntValue(element,"Y");
		//int duration = getIntValue(element, "Duration");
		String name = getTextValue(element, "Name");
		String option = getTextValue(element, "Option");
		SCAction action=null;
		if(c.equals("SCActionBuilding")) {
			action = new SCActionBuilding(this, startTime, y, name, option);
		} else if(c.equals("SCActionBuildRefinery")) {
			action = new SCActionBuildRefinery(this, startTime, y);
		} else if(c.equals("SCActionBuildSCV")) {
			action = new SCActionBuildSCV(this, startTime, y);
		} else if(c.equals("SCActionBuildUnit")) {
			action = new SCActionBuildUnit(this, startTime, y, name);
		} else if(c.equals("SCActionTransferToGas")) {
			action = new SCActionTransferToGas(this, startTime, y);
		} else if(c.equals("SCActionTransferOffGas")) {
			//action = new SCActionTransferOffGas(this, startTime, y);
		} else if(c.equals("SCActionMule")) {
			action = new SCActionMule(this, startTime, y);
		} else if(c.equals("SCActionCalldownSupply")) {
			action = new SCActionCalldownSupply(this, startTime, y);
		} else if(c.equals("SCActionUpgradeBase")) {
			action = new SCActionUpgradeBase(this, startTime, y, name);
		} else if(c.equals("SCActionBuildAddon")) {
			action = new SCActionBuildAddon(this, startTime, y, name, option);
		} else if(c.equals("SCActionLift")) {
			action = new SCActionLift(this, startTime, y, name, option);
		} else if(c.equals("SCActionLand")) {
			action = new SCActionLand(this, startTime, y, name, option);
		} else if(c.equals("SCActionResearch")) {
			action = new SCActionResearch(this, startTime, y, name);
		} else if(c.equals("SCActionBuildBase")) {
			action = new SCActionBuildBase(this, startTime, y);
		} else if(c.equals("SCActionMaynard")) {
			action = new SCActionMaynard(this, startTime, y);
		} else if(c.equals("SCActionScout")) {
			action = new SCActionScout(this, startTime, y, option);
		} else {
			System.out.println("Unknown class type = " + c);
		}
		return action;

	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		int r=0;
		try {
			r=Integer.parseInt(getTextValue(ele,tagName));
		} catch(NumberFormatException e) {
			r=0;
		}
		return r;
	}

	public boolean buildSCV() {

		int selectedBase=-1;
		int leastSCVs=200;
		for(int i = 0; i < bases.size(); i++ ) {
			if(bases.get(i).isComplete() && bases.get(i).getQueueLength()==0 && bases.get(i).scvCount()<leastSCVs) {
				leastSCVs = bases.get(i).scvCount();
				selectedBase = i;
			}
		}
		if(selectedBase>=0) {
			//System.out.println(printTime() + "   <Model> Base = "+selectedBase);
			if(bases.get(selectedBase).addSCVtoQueue()) {
				//System.out.println(printTime() + "   <Model:buildSCV> Adding SCV to base queue");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean upgradeBase(String name) {
		int base=-1;
		int i =0;
		while(i < bases.size() && base<0) {
			if(bases.get(i).isComplete() && bases.get(i).getName().equals("CommandCenter") && bases.get(i).getQueueLength()==0) {
				base=i;
			}
			i++;
		}
		if(base>=0) {
			if(bases.get(base).upgrade(name)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean makeBuilding(String name, String addon) {
		boolean addonFound = false;
		int c = -1;
		SCAddon a;
		int i = 0;
		if(addon.equals("none")) {
			if(objects.add(new SCStructure(this, name))) {
				spendMinerals(getMineralCost(name));
				spendGas(getGasCost(name));
				return true;
			} else {
				return false;
			}
		} else {
			// building a structure on existing addon
			while(i < objects.size() && !addonFound) {
				if(objects.get(i) instanceof SCAddon) {
					a = (SCAddon)objects.get(i);
					if(a.getName().equals(addon) && a.getQueueLength()==0 && a.isAvailable() &&	a.isComplete() && a.getAttachedTo().equals("")) {
						c = i;
						addonFound=true;
					}
				}
				i++;
			}

			if( addonFound) {
				SCStructure s = new SCStructure(this, name);
				s.setAddonName(addon);
				if(objects.add(s)) {
					spendMinerals(getMineralCost(name));
					spendGas(getGasCost(name));
					if(objects.get(c).attach(name)) {
						return true;
					} else {
						System.out.println(printTime() + "   <Model:makeBuilding> Attach addon failed");
						return false;
					}
				} else {
					return false;
				}

			} else {
				System.out.println(printTime() + "   <Model:makeBuilding> AddonFound = " + addonFound);
				return false;
			}
		}
	}

	public boolean buildRefinery() {
		boolean f = false;
		int b = 0;
		while(!f && b < bases.size()) {
			if(bases.get(b).freeGeysers()>0) {
				f = true;
			} else {
				b++;
			}
		}
		if(!f) {
			System.out.println(printTime() + "   <Model:buildRefinery> Couldn't find a base with free geysers");
		} else {
			if(bases.get(b).addRefinery()) {
				return true;
			} else {
				return false;
			}
		}
		return f;

	}



	public boolean mule() {
		int most=0;
		int baseFrom=-1; // where to use the energy
		int baseTo=-1;   // where to drop the mule
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).getEnergy() > most) {
				most = bases.get(i).getEnergy();
				baseFrom=i;
			}
		}
		most=0;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).isComplete() && bases.get(i).remainingMinerals() > most) {
				most = bases.get(i).remainingMinerals();
				baseTo=i;
			}
		}
		if(baseFrom>=0 && baseTo>=0) {
			if(bases.get(baseTo).addMule()) {
				//System.out.println(printTime() + "   <Model> Dropping Mule on Base " + baseTo + " using energy from base " + baseFrom);
				bases.get(baseFrom).useEnergy(50);
				return true;
			} else {
				//System.out.println(printTime() + "   <Model> Couldn't complete base.addMule()");
				return false;
			}
		} else {
			//System.out.println(printTime() + "   <Model> Couldn't find baseFrom && baseTo");
			return false;
		}
	}

	public boolean calldownSupply() {
		int most=0;
		int baseFrom=-1; // where to use the energy
		int depot=-1;
		boolean found=false;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).getEnergy() > most) {
				most = bases.get(i).getEnergy();
				baseFrom=i;
			}
		}
		int i = 0;
		while(i < objects.size() && !found) {
			if(objects.get(i).getName().equals("SupplyDepot")) {
				SCStructure s = (SCStructure)objects.get(i);
				if(s.getSupply()<=8) {
					depot=i;
					found=true;
				}
			}
		}
		if(found) {
			((SCStructure)objects.get(depot)).setSupply(16);
			addSupply(8);
			bases.get(baseFrom).useEnergy(50);
			return true;
		} else {
			return false;
		}


	}
	
	public boolean scout(String option) {
		int duration = Integer.parseInt(option);
		int most =0;
		int base=-1;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).scvCount()>0) {
				most = bases.get(i).scvCount();
				base = i;
			}
		}
		if(base>=0) {
			bases.get(base).setSCVScouting(duration);
			return true;
		} else {
			return false;
		}
		// pull SCV off mining to tootle about the map for 15/30/45/60 seconds (dropdown)
	}

	public boolean scan() {
		int most=0;
		int baseFrom=-1; // where to use the energy
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).getEnergy() > most) {
				most = bases.get(i).getEnergy();
				baseFrom=i;
			}
		}
		if(baseFrom>=0) {
			bases.get(baseFrom).useEnergy(50);
			return true;
		} else {
			return false;
		}
	}

	public boolean isFreeBase() {
		boolean free = false;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).isComplete() && bases.get(i).getQueueLength() == 0) {
				free=true;
			}
		}
		return free;
	}
	public boolean hasEnergy(int e) {
		boolean energy = false;
		for (int i = 0; i < bases.size(); i++) {
			if (bases.get(i).getEnergy() >= e) {
				energy = true;
			}
		}
		return energy;
	}
	public boolean isObjectComplete(String name) { // check to see if a building is complete
		boolean ready = false;
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).isComplete() && objects.get(i).getName().equals(name)) {
				ready = true;
			}
		}
		return ready;
	}
	public boolean alreadyStarted(String name) {
		boolean found = false;
		int i = 0;
		while(i < objects.size() && !found) {
			if(objects.get(i).getName().equals(name)) {
				found=true;
			} else if(objects.get(i) instanceof SCStructure) {
				if(((SCStructure)objects.get(i)).getConstructingName().equals(name)) {
					found=true;
				}
			}
			i++;
		}
		return found;
	}


	public boolean hasAddon(String build, String tech) {
		boolean foundBuilding=false;
		boolean foundTech=false;
		SCStructure building;
		SCAddon addon;
		int i = 0;
		while((!foundTech || !foundBuilding) && i < objects.size()) {
			if(objects.get(i).getName().equals(build)) {
				building = (SCStructure)objects.get(i);
				if(building.getAddonName().equals(tech)) {
					foundBuilding=true;
					//					System.out.println(printTime() + "   <Model> Found building " + i);
				}
			}
			if(objects.get(i).getName().equals(tech)) {
				addon = (SCAddon)objects.get(i);
				if(addon.getAttachedTo().equals(build)) {
					foundTech=true;
					//					System.out.println(printTime() + "   <Model> Found addon " + i);
				}
			}
			i++;
		}
		if(foundBuilding && foundTech) {
			return true;
		} else {
			return false;
		}



	}

	public boolean isAvailable(String name) { // check to see if a building is complete
		boolean ready = false;
		int i = 0;
		while(i < objects.size() && !ready) {
			if(objects.get(i).isComplete() && objects.get(i).getName().equals(name) && objects.get(i).isAvailable()) {
				ready=true;
			}
			i++;
		}
		return ready;
	}

	public boolean setSCVBuilding(int duration) {
		int most=0;
		int base=-1;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).scvCount() > most) {
				most = bases.get(i).scvCount();
				base =i;
			}
		}
		if(base>=0) {
			if(bases.get(base).setSCVBuilding(duration)) {
				//System.out.println(printTime() + "   <Model> Preaction");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public boolean addUnitToQueue(String name) {
		String build = getBuild(name);
		String tech = getTech(name);
		int i = 0;
		int building=-1;
		boolean found = false;
		while(!found && i < objects.size()) {
			if(objects.get(i).getName().equals(build) && objects.get(i).isComplete() && objects.get(i).isAvailable()) {
				SCStructure s = (SCStructure)objects.get(i);
				if(s.getQueueLength()==0) {
					if(tech!=null) {
						if(s.getAddonName().equals(tech)) {
							found=true;
							building=i;
						}
					} else {
						found=true;
						building=i;
					}
				}
			}
			i++;
		}
		if(found) {
			if(((SCStructure)objects.get(building)).addObjectToQueue(name)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}
	
	public boolean addResearch(String name) {
		String build = getBuild(name);  // eg ebay for +1, barracks for stimpack
		String tech = getTech(name);    // eg techlab for stimpack, null for none addon research
		boolean buildingFound=false;
		boolean addonFound=false;
		int i=0;
		int b=-1;
		int c=-1;
		int size = objects.size();
		if(tech==null) {
			// research at a building
			while(i < size) {
				if(objects.get(i).getName().equals(build) && objects.get(i).isAvailable()) {
					buildingFound=true;
					b=i;
				}
				i++;
			}
			if(buildingFound) {
				if(((SCStructure)objects.get(b)).addObjectToQueue(name)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			// research at a techlab
			while(i < size) {
				if(objects.get(i).getName().equals(build) && objects.get(i).isComplete()) {
					if(((SCStructure)objects.get(i)).getAddonName().equals(tech)) {
						buildingFound=true;
						b=i;
					}
				}
				if(objects.get(i).getName().equals(tech) && objects.get(i).isAvailable()) {
					if(((SCAddon)objects.get(i)).getAttachedTo().equals(build)) {
						addonFound=true;
						c=i;
					}
				}
				i++;
			}
			if(buildingFound && addonFound) {
				if(((SCAddon)objects.get(c)).addObjectToQueue(name)) {
					return true;
				} else {
					return false;
				}
			}
			
		}
		return true;
	}

	public int getmX1() {
		return mX1;
	}

	public void setmX1(int mX1) {
		this.mX1 = mX1;
	}

	public int getmY1() {
		return mY1;
	}

	public void setmY1(int mY1) {
		this.mY1 = mY1;
	}

	public int getmX2() {
		return mX2;
	}

	public void setmX2(int mX2) {
		this.mX2 = mX2;
	}

	public int getmY2() {
		return mY2;
	}

	public void setmY2(int mY2) {
		this.mY2 = mY2;
	}
	
	public boolean buildBase() {
		spendMinerals(getMineralCost("CommandCenter"));
		Base b = new Base(this);
		bases.add(b);
		return true;
	}

	public int completedBases() {
		int b = 0;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).isComplete()) {
				b++;
			}
		}
		return b;
	}

	public boolean buildAddon(String name, String building) {
		int i = 0;
		int b =-1;
		boolean found=false;
		SCStructure s;
		while(i < objects.size() && !found) {
			if(objects.get(i) instanceof SCStructure) {
				s = (SCStructure)objects.get(i);
				if(s.getName().equals(building) && s.getQueueLength()==0 && s.isAvailable() && s.getAddonName().equals("")) {
					b = i;
					found=true;
				}
			}
			i++;
		}
		if(found) {
			s = (SCStructure)objects.get(b);
			s.buildAddon(name);
			SCAddon a = new SCAddon(this, name);
			a.setAttachedTo(building);
			objects.add(a);
			//System.out.println(printTime() + "   <Model> Adding " + a.getName() + " to list");
		} 		
		return found;
	}

	public boolean freeAddonExists(String addon) {
		boolean addonFound=false;
		SCAddon a;
		int i = 0;
		while(i < objects.size() && !addonFound) {
			if(objects.get(i) instanceof SCAddon) {
				a = (SCAddon)objects.get(i);
				if(a.getName().equals(addon) && a.getQueueLength()==0 &&
						a.isComplete() && a.getAttachedTo().equals("")) {
					addonFound=true;
					//System.out.println(printTime() + "   <Model> Free addon is " + i + " "+a.getName());
				}
			}
			i++;
		}
		return addonFound;

	}

	public boolean lift(String name, String addon) {
		//System.out.println(printTime() + "   <Model> Lift "+name+" off of "+addon);
		int i = 0;
		int b =-1;
		int c =-1;
		boolean buildingFound=false;
		boolean addonFound=false;
		SCStructure s;
		SCAddon a;
		if(addon.equals("none")) {
			while(i < objects.size() && !buildingFound) {
				if(objects.get(i) instanceof SCStructure) {
					s = (SCStructure)objects.get(i);
					if(s.getName().equals(name) && s.isComplete() && s.getQueueLength()==0 &&
							s.isAvailable() && s.getAddonName().equals("") && !s.isLifted()) {
						b = i;
						buildingFound=true;
					}
				}
				i++;
			}

			if(buildingFound ) {
				if(objects.get(b).lift()) {
					return true;
				} else {
					System.out.println(printTime() + "   <Model:lift> Lift structure failed");
					return false;
				}
			} else {
				System.out.println(printTime() + "   <Model:lift> BuildingFound = " + buildingFound + " AddonFound = " + addonFound);
				return false;
			}
		} else {
			while(i < objects.size() && (!buildingFound || !addonFound)) {
				if(objects.get(i) instanceof SCStructure) {
					s = (SCStructure)objects.get(i);
					if(s.getName().equals(name) && s.isComplete() && s.getQueueLength()==0 &&
							s.isAvailable() && s.getAddonName().equals(addon) && !s.isLifted()) {
						b = i;
						buildingFound=true;
					}
				}
				if(objects.get(i) instanceof SCAddon) {
					a = (SCAddon)objects.get(i);
					if(a.getName().equals(addon) && a.getQueueLength()==0 && a.isAvailable() &&
							a.isComplete() && a.getAttachedTo().equals(name)) {
						c = i;
						addonFound=true;

					}
				}
				i++;
			}

			if(buildingFound && addonFound) {
				if(objects.get(b).lift()) {
					if(objects.get(c).detach()) {
						return true;
					} else {
						System.out.println(printTime() + "   <Model:lift> Detach addon failed");
						return false;
					}
				} else {
					System.out.println(printTime() + "   <Model:lift> Lift structure failed");
					return false;
				}
			} else {
				System.out.println(printTime() + "   <Model:lift> BuildingFound = " + buildingFound + " AddonFound = " + addonFound);
				return false;
			}
		}

	}

	public boolean land(String name, String addon) {
		//System.out.println(printTime() + "   <Model> Land "+name+" on "+addon);
		int i = 0;
		int b =-1;
		int c =-1;
		boolean buildingFound=false;
		boolean addonFound=false;
		SCStructure s;
		SCAddon a;
		if(addon.equals("none")) {
			while(i < objects.size() && !buildingFound) {
				if(objects.get(i) instanceof SCStructure) {
					s = (SCStructure)objects.get(i);
					if(s.getName().equals(name) && s.isComplete() && s.getQueueLength()==0 
							&& s.getAddonName().equals("") && s.isLifted()) {
						b = i;
						buildingFound=true;
					}
				}
				i++;
			}
			if(buildingFound) {
				if(objects.get(b).land(addon)) {
					return true;
				} else {
					System.out.println(printTime() + "   <Model:land> Land building failed");
					return false;
				}
			} else {
				System.out.println(printTime() + "   <Model:land> BuildingFound = " + buildingFound);
				return false;
			}

		} else {
			while(i < objects.size() && (!buildingFound || !addonFound)) {
				if(objects.get(i) instanceof SCStructure) {
					s = (SCStructure)objects.get(i);
					if(s.getName().equals(name) && s.isComplete() && s.getQueueLength()==0 
							&& s.getAddonName().equals("") && s.isLifted()) {
						b = i;
						buildingFound=true;
					}
				}
				if(objects.get(i) instanceof SCAddon) {
					a = (SCAddon)objects.get(i);
					if(a.getName().equals(addon) && a.getQueueLength()==0 && a.isAvailable() &&
							a.isComplete() && a.getAttachedTo().equals("")) {
						c = i;
						addonFound=true;

					}
				}
				i++;
			}

			if(buildingFound && addonFound) {
				if(objects.get(b).land(addon)) {
					if(objects.get(c).attach(name)) {
						return true;
					} else {
						System.out.println(printTime() + "   <Model:lift> Attach addon failed");
						return false;
					}
				} else {
					System.out.println(printTime() + "   <Model:lift> Land structure failed");
					return false;
				}
			} else {
				System.out.println(printTime() + "   <Model:lift> BuildingFound = " + buildingFound + " AddonFound = " + addonFound);
				return false;
			}
		}
	}
	
	public boolean maynard() {
		int scvsFrom=0;
		int scvsTo=200;
		int baseFrom=-1;
		int baseTo=-1;
		int moving=-1;
		for(int b = 0; b < bases.size();b++) {
			if(bases.get(b).scvCount()+bases.get(b).idleSCVCount()>scvsFrom) {
				scvsFrom = bases.get(b).scvCount()+bases.get(b).idleSCVCount();
				baseFrom = b;
			}
			if(bases.get(b).scvCount()+bases.get(b).idleSCVCount()<scvsTo) {
				scvsTo = bases.get(b).scvCount()+bases.get(b).idleSCVCount();
				baseTo = b;
			}
		}
		//System.out.println(printTime() + "   <Model:maynard> " + scvsFrom + " scvs at base " + baseFrom);
		//System.out.println(printTime() + "   <Model:maynard> " + scvsTo + " scvs at base " + baseTo);
		//System.out.println(printTime() + "   <Model:maynard> Transfering scvs from " + baseFrom + " to " + baseTo);
		if(baseFrom>=0 && baseTo>=0 && baseFrom!=baseTo) {
			moving = (int)(Math.round((scvsFrom-scvsTo)*0.5));
			//System.out.println(printTime() + "   <Model:maynard> Moving " + moving + " scvs");
			for(int i = 0; i < moving; i++) {
				SCV s = bases.get(baseFrom).transferSCV();
				bases.get(baseTo).addSCV(s);
			}
			return true;
		} else {
			return false;
		}
	}


	public void startMarquee(int x, int y) {
		mX1=x;
		mY1=y;
	}

	public void updateMarquee(int x, int y) {
		mX2=x;
		mY2=y;
	}

	public void endMarquee(int x, int y) {
		//System.out.println(mX1+" "+mY1+" - "+mX2+" "+mY2);
		mX1 -= border;
		mY1 -= border;
		mY1 /= spacing;
		mX1 /= scale;
		mX1 += scroll;

		mX2 -= border;
		mY2 -= border;
		mY2 /= spacing;
		mX2 /= scale;
		mX2 += scroll;

		int ax;
		int ay;
		int aend;
		//System.out.println(mX1+" "+mY1+" - "+mX2+" "+mY2);
		if(mX2<mX1) {
			int tmp = mX2;
			mX2 = mX1;
			mX1 = tmp;
		} // make mY1 < mY2
		if(mY2<mY1) {
			int tmp = mY2;
			mY2 = mY1;
			mY1 = tmp;
		} // make mY1 < mY2
		if(mX1>=0 && mX2>=0 && mY1>=0 && mY2>=0) {
			for(int i = 0; i < actions.size(); i++) {
				ax=actions.get(i).getStartTime();
				ay=actions.get(i).getY();
				aend=ax+actions.get(i).getDuration();
				if(ax>mX1 && aend<mX2 && ay>=mY1 && ay<=mY2) {
					actions.get(i).select();
				}
			}
		}

		mX1=-1;
		mY1=-1;
		mX2=-1;
		mY2=-1;

	}


	public void selectMultipleAction(int x, int y) {
		x -= border;
		y -= border;
		y /= spacing;
		x /= scale;
		x += scroll;

		int a = 0;
		int size = actions.size();
		boolean f = false;
		while (!f && a < size) {
			if (x >= actions.get(a).getStartTime()
					&& x < actions.get(a).getStartTime() + actions.get(a).getDuration()
					&& y >= actions.get(a).getY()
					&& y < actions.get(a).getY() + 1) {
				actions.get(a).toggleSelect();
				f=true;
				//System.out.println("Selecting action " + a);
			}
			a++;
		}
	}

	public void selectAllActions(int x, int y) {
		x -= border;
		y -= border;
		y /= spacing;
		x /= scale;
		x += scroll;
		for(int i = 0; i < actions.size(); i++) {
			actions.get(i).deselect();
		}
		int a = 0;
		boolean f= false;
		String name="";
		while(!f && a < actions.size()) {
			//for (int i = 0; i < actions.size(); i++) {
			if (x >= actions.get(a).getStartTime()
					&& x < actions.get(a).getStartTime() + actions.get(a).getDuration()
					&& y >= actions.get(a).getY()
					&& y < actions.get(a).getY() + 1) {
				name = actions.get(a).getName();
				actions.get(a).select();
				f=true;
				//System.out.println("Selecting action " + i);
			}
			a++;
		}
		for(int i = 0;i < actions.size(); i++) {
			if(actions.get(i).getName().equals(name)) {
				actions.get(i).select();
			}
		}
	}
	public void rightClick(int x, int y) {
		x -= border;
		y -= border;
		y /= spacing;
		x /= scale;
		x += scroll;
		int a = 0;
		boolean f = false;
		int size = actions.size();
		while (!f && a < size) {
			if (x >= actions.get(a).getStartTime()
					&& x < actions.get(a).getStartTime() + actions.get(a).getDuration()
					&& y >= actions.get(a).getY() && y < actions.get(a).getY() + 1) {
					actions.get(a).setPopup(true);
					actions.get(a).select();
				f=true;
				//System.out.println("Selecting action " + a);
			}
			a++;
		}
		
	}

	public void selectAction(int x, int y) {
		int x2=x;
		int y2=y;

		x -= border;
		y -= border;
		y /= spacing;
		x /= scale;
		x += scroll;

		x2 -= border;
		x2 += (scale*scroll);

		y2 -= border;
		for(int i = 0; i < actions.size(); i++) {
			if(actions.get(i).getPopup()==true) {
				if(actions.get(i).getOptionsSize()>0) {
					//SCActionBuildAddon a = (SCActionBuildAddon)actions.get(i);
					SCAction a = actions.get(i);
					int x3 = (int)(x2-(scale * (a.getStartTime()+a.getDuration())));
					int y3 = y2 - (actions.get(i).getY() * spacing) - thickness;
					y3 /= 14;
					//System.out.println(x3 + " " + y3);
					if(x3>0 && x3<60 && y3 < actions.get(i).getOptionsSize()) {
						actions.get(i).setOption(y3);
					}
				} // add else if for other actions here like SCActionLand
			}
		}
		y2 -= (y*spacing);
		y2 -= thickness;
		for(int i = 0; i < actions.size(); i++) {
			actions.get(i).setPopup(false);
			if(actions.get(i).getOptionsSize()>0) {
				SCAction a = actions.get(i);
				if(a.isSelected()) {
					int t = (int)((scale * (a.getStartTime()+a.getDuration()))-x2);
					//System.out.println(x + " " + y + " " + t + " " + y2);
					if(t>0 && t < 14 && y2 > 0 && y2 < 14) {
						a.setPopup(true);
					}
				}
			}
			actions.get(i).deselect();
		}
		int a = 0;
		int size = actions.size();
		boolean f = false;
		while (!f && a < size) {
			if (x >= actions.get(a).getStartTime()
					&& x < actions.get(a).getStartTime() + actions.get(a).getDuration()
					&& y >= actions.get(a).getY()
					&& y < actions.get(a).getY() + 1) {
				actions.get(a).select();
				f=true;
				//System.out.println("Selecting action " + a);
			}
			a++;
		}
		//		System.out.println(x + " " + y + " " + x2 + " " + y2);

	}
	public void moveSelected(int x, int y) {
		for (int i = 0; i < actions.size(); i++) {
			if (actions.get(i).isSelected()) {
				actions.get(i).addStartTime(x);
				actions.get(i).moveY(y);
			}
		}
	}
	public void moveSelectedToEarliest() {
		SCAction action;
		int x;
		int y;
		int dur;
		int x2;
		int end;
		int end2;
		int size = actions.size();
		for(int i = 0; i < size; i++) {
			if(actions.get(i).isSelected()) {
				action = actions.get(i);
				if(action.isComplete()) {
					while(action.getStartTime()>0 && action.isComplete()) {
						action.addStartTime(-1);
						this.reset();
						this.play();
						if(!action.isComplete()) {
							action.addStartTime(1);
						}
					}
				} else {
					int r = action.getStartTime();
					while(action.getStartTime()<maxTime && !action.isComplete()) {
						action.addStartTime(1);
						this.reset();
						this.play();
					}
					if(!action.isComplete()) {
						action.setStartTime(r);
					}
				}

				x = action.getStartTime();
				y = action.getY();
				dur = getTime(action.getName());
				end = x + dur;
				boolean space = false;
				SCAction action2;
				while(!space) {
					space=true;
					for(int j = 0; j < size; j++) {
						if(i!=j) {
							action2 = actions.get(j);
							if(action2.getY() == y) {
								x2=action2.getStartTime();
								end2=action2.getStartTime()+action2.getDuration();
								if(x <= x2 && x2<end && end<=end2) {
									//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
									space=false;
								} else if(x2<=x && x<end2 && end2<=end) {
									//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
									space=false;
								} else if(x<x2 && end2<end) {
									//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
									space=false;
								} else if(x2<x && end<end2) {
									//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2 + "***");
									space=false;
								} else {
									//						System.out.println("y:" + y + " | " + x +" - "+end+" & " + x2 + " - " + end2);
								}

							}
						}
					}
					if(!space) {
						y++;
					}
				}
				if(space) {
					action.setY(y);
				}
			}
		}
	}

	public String setTotalsText() {
		ArrayList<String> objectNames = new ArrayList<String>();
		SCStructure b;
		int n = 13;
		String name;
		int[] counts = new int[n];
		for(int i = 0; i < n; i++) {
			counts[i]=0;
		}
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).getName().equals("Barracks") || 
					objects.get(i).getName().equals("Factory") || 
					objects.get(i).getName().equals("Starport")) {
				b = (SCStructure)objects.get(i);
				objectNames.add(b.getName()+":"+b.getAddonName());
			} else if(objects.get(i).getName().equals("TechLab") ||
					objects.get(i).getName().equals("Reactor")){
				
			} else {
				objectNames.add(objects.get(i).getName());
			}
		}
		
		for(int i = 0 ; i < objectNames.size(); i++) {
			name = objectNames.get(i);
			if(name.equals("Marine")) {
				counts[0]++;
			} else if(name.equals("Marauder")) {
				counts[1]++;
			}else if(name.equals("Ghost")) {
				counts[2]++;
			}else if(name.equals("Reaper")) {
				counts[3]++;
			}else if(name.equals("Hellion")) {
				counts[4]++;
			}else if(name.equals("SiegeTank")) {
				counts[5]++;
			}else if(name.equals("Thor")) {
				counts[6]++;
			}else if(name.equals("Viking")) {
				counts[7]++;
			}else if(name.equals("Medivac")) {
				counts[8]++;
			}else if(name.equals("Banshee")) {
				counts[9]++;
			}else if(name.equals("Raven")) {
				counts[10]++;
			}else if(name.equals("Battlecruiser")) {
				counts[11]++;
			}else if(name.equals("SupplyDepot")) {
				counts[12]++;
			}
		}
		String s="";
		s +="Supply        : "+food+"/"+supply+"\n";
		s +="Total Minerals: "+totalMineralsMined+"\n";
		s +="Total Gas     : "+totalGasMined+"\n";
		s +="\n";
		// bases and scvs <minerals>&<gas>(<idle>)
		for(int i = 0; i < bases.size(); i++) {
			if(i==0) {
				s += bases.get(i).scvCount() +"&"+ bases.get(i).scvCountGas()
						+"("+bases.get(i).idleSCVCount()+") SCVs at main\n";
			} else if(i==1) {
				s += bases.get(i).scvCount() +"&"+ bases.get(i).scvCountGas()
						+"("+bases.get(i).idleSCVCount()+") SCVs at natural\n";
			} else {
				s += bases.get(i).scvCount() +"&"+ bases.get(i).scvCountGas()
						+"("+bases.get(i).idleSCVCount()+") SCVs at base " + (i+1) + "\n";
			}
		}
		s+="\n";
		if(counts[0] > 0) {				s += counts[0] + " Marines\n";			}
		if(counts[1] > 0) {				s += counts[1] + " Marauders\n";	}
		if(counts[2] > 0) {				s += counts[2] + " Ghosts\n";	}
		if(counts[3] > 0) {				s += counts[3] + " Reapers\n";	}
		if(counts[4] > 0) {				s += counts[4] + " Hellions\n";	}
		if(counts[5] > 0) {				s += counts[5] + " Siege Tanks\n";	}
		if(counts[6] > 0) {				s += counts[6] + " Thors\n";	}
		if(counts[7] > 0) {				s += counts[7] + " Vikings\n";	}
		if(counts[8] > 0) {				s += counts[8] + " Medivacs\n";	}
		if(counts[9] > 0) {				s += counts[9] + " Banshees\n";	}
		if(counts[10] > 0) {			s += counts[10] + " Ravens\n";	}
		if(counts[11] > 0) {			s += counts[11] + " Battlecruisers\n";	}
		if(counts[12] > 0) {			s += counts[12] + " Supply Depots\n";	}

		s+="\n";
		for(int i = 0; i < objectNames.size(); i++) {
			name=objectNames.get(i);
			if(!name.equals("Marine") && !name.equals("Marauder") && !name.equals("Ghost") && 
					!name.equals("Reaper") && !name.equals("Hellion") && 
					!name.equals("SiegeTank") && !name.equals("Thor") && 
					!name.equals("Viking") && !name.equals("Medivac") && 
					!name.equals("Banshee") && !name.equals("Raven") && 
					!name.equals("Battlecruiser") && !name.equals("SupplyDepot")) {
				s+=name;
				s+="\n";
			}
			
			
		}
		
		
		return s;
	}

	
	/*
		for(int i = 0; i < objects.size(); i++) {
			name = objects.get(i).getName();
			if(name.equals("Barracks") || name.equals("Factory") || name.equals("Starport")) {
				if(!((SCStructure)objects.get(i)).getAddonName().equals("")) {
					name += " with ";
					name += ((SCStructure)objects.get(i)).getAddonName();
				}
			} 
			s += name;
			s += "\n";
		}
	 */
	public int freeGeysers() {
		int g= 0;
		for(int i = 0; i < bases.size(); i++) {
			g+=bases.get(i).freeGeysers();
		}
		return g;
	}

	public int freeRefineries() {
		int r = 0;
		for(int i = 0; i < bases.size(); i++) {
			r+=bases.get(i).freeRefineries();
		}
		//System.out.println(printTime() + "   <Model> Free Refineries " + r);
		return r;
	}
	
	public int scvsOnGas() {
		int s = 0;
		for(int i = 0; i < bases.size(); i++) {
			s += bases.get(i).scvCountGas();
		}
		return s;
	}


	public void addUnit(SCObject s) {
		//System.out.println(printTime() + "   <Model>Adding unit " + s.getName());
		objects.add(s);
	}

	public int actionCount() {
		return actions.size();
	}


	public int getSupply() {
		return supply;
	}

	public int getFood() {
		return food;
	}

	public void addSupply(int s) {
		//System.out.println(printTime() + "   <Model:addSupply> Adding " + s);
		supply += s;
	}
	public void addFood(int f) {
		food += f;
	}
	public int getMinerals() {
		return minerals;
	}
	public int getGas() {
		return gas;
	}

	public String[] getUnitOptions() {
		String[] s = {"SCV","Mule","TransferToGas","TransferOffGas","Maynard","Marine", "Marauder", "Reaper", "Ghost", "Hellion", 
				"SiegeTank", "Thor", "Viking", "Medivac", "Banshee", "Battlecruiser", "Raven", "Scout"};
		return s;
	}

	public String[] getBuildingOptions() {
		String[] s = {"SupplyDepot", "Refinery","Barracks", "OrbitalCommand","Factory",
				"Starport", "TechLab", "Reactor", "LiftBarracks","LiftFactory",
				"LiftStarport","LandBarracks","LandFactory","LandStarport","CommandCenter", "CalldownSupply",
				"Bunker","EngineeringBay","Armory",  "GhostAcademy", "Missile Turret",
				"PlanetaryFortress","FusionCore", "SensorTower", "Scan"};
		return s;
	}

	public String[] getResearchOptions() {
		String[] s = {"StimPack",
				"CombatShield",
				"ConcussiveShells",
				"NitroPack",
				"InfernalPreigniter",
				"SiegeTech",
				"250mmStrikeCannons",
				"CloakingField",
				"SeekerMissile",
				"CaduceusReactor",
				"CorvidReactor",
				"BehemothReactor",
				"PersonalCloaking",
				"MoebiusReactor",
				"Nuke",
				"InfantryWeaponsLevel1",
				"InfantryWeaponsLevel2",
				"InfantryWeaponsLevel3",
				"InfantryArmorLevel1",
				"InfantryArmorLevel2",
				"InfantryArmorLevel3",
				"VehicleWeaponsLevel1",
				"VehicleWeaponsLevel2",
				"VehicleWeaponsLevel3",
				"VehicleArmorLevel1",
				"VehicleArmorLevel2",
				"VehicleArmorLevel3",
				"ShipWeaponsLevel1",
				"ShipWeaponsLevel2",
				"ShipWeaponsLevel3",
				"ShipArmorLevel1",
				"ShipArmorLevel2",
				"ShipArmorLevel3",
				"WeaponRefit",
				"NeosteelFrame",
				"BuildingArmor",
				"DurableMaterials",
				"HiSecAutoTracking",
				};
		return s;
	}

	public int getMineralCost(String n) {
		int i = 0;
		try {
			i = mineralCost.get(n);
		} catch (NullPointerException e) {
			//System.out.println("Error: <Model:getMineralCost> Can't find unit name " + n + " - " + e);
			i = 0;
		}
		return i;
	}

	public int getGasCost(String n) {
		int i = 0;
		try {
			i = gasCost.get(n);
		} catch (NullPointerException e) {
			//System.out.println("Error: <Model:getGasCost> Can't find unit name " + n + " - " + e);
			i = 0;
		}
		return i;
	}

	public int getTime(String n) {
		int i = 0;
		try {
			i = times.get(n);
		} catch (NullPointerException e) {
			//System.out.println("Error: <Model:getTime> Can't find unit name " + n + " - " + e);
			i = 0;
		}
		return i;
	}
	public String getPrereq(String n) {
		//System.out.println("Getting prereq for " + n);
		String s;
		try {
			s = prereqs.get(n);
		} catch (NullPointerException e) {
			//System.out.println("Error: <Model:getPrereq> Can't find prereq for " + n + " - " + e);
			s = null;
			//System.out.println("s "+s);
		}
		return s;
	}
	public String getBuild(String n) {
		String s;
		try {
			s = build.get(n);
		} catch (NullPointerException e) {
			//System.out.println("Error: <Model:getBuild> Can't find build for " + n + " - " + e);
			s = null;
		}
		return s;
	}
	public String getTech(String n) {
		String s;
		try {
			s = tech.get(n);
		} catch (NullPointerException e) {
			//System.out.println("Error: <Model:getBuild> Can't find build for " + n + " - " + e);
			s = null;
		}
		return s;
	}
	public int getFood(String n) {
		int i = 0;
		try {
			i = foods.get(n);
		} catch (NullPointerException e) {
			// System.out.println("Error: <Model:getFood> Can't find unit name " + n + " - " + e);
			i = 0;
		}
		return i; // foods.get(n);
	}
	public int getSupply(String n) {
		int i = 0;
		try {
			i = supplies.get(n);
		} catch (NullPointerException e) {
			// System.out.println("Error: <Model:getSupply> Can't find unit name " + n + " - " + e);
			i = 0;
		}
		return i; // foods.get(n);
	}

	public void addMinerals(int c) {
		//System.out.println(printTime() + "   <Model:addMinerals> Adding " + c);
		minerals += c;
		totalMineralsMined += c;
	}
	public void spendMinerals(int c) {
		//System.out.println(printTime() + "   <Model:spendMinerals> Spending " + c);
		minerals -= c;
	}	

	public int getTotalMineralsMined() {
		return totalMineralsMined;
	}

	public void setTotalMineralsMined(int totalMineralsMined) {
		this.totalMineralsMined = totalMineralsMined;
	}

	public int getTotalGasMined() {
		return totalGasMined;
	}

	public void setTotalGasMined(int totalGasMined) {
		this.totalGasMined = totalGasMined;
	}
	public SCAction getAction(int i) {
		return actions.get(i);
	}
	public void addGas(int g) {
		gas += g;
		totalGasMined += g;
	}
	public int getMineralGraph(int t) {
		return mineralGraph[t];
	}

	public int getGasGraph(int t) {
		return gasGraph[t];
	}

	public int getEnergyGraph(int t) {
		return energyGraph[t];
	}

	public void spendGas(int g) {
		gas -= g;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public int getScroll() {
		return scroll;
	}

	public void setScroll(int scroll) {
		this.scroll = scroll;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getSpacing() {
		return spacing;
	}
	public int getThickness() {
		return thickness;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public int getMaxTime() {
		return maxTime;
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String printTime() {
		String s;
		int min = time / 60;
		if (min < 10) {
			s = "0" + min;
		} else {
			s = "" + min;
		}
		int sec = time - (60 * min);
		if (sec < 10) {
			s += ":0" + sec;
		} else {
			s += ":" + sec;
		}
		return s;
	}

	public String printTime(int i) {
		String s;
		int min = i / 60;
		if (min < 10) {
			s = "0" + min;
		} else {
			s = "" + min;
		}
		int sec = i - (60 * min);
		if (sec < 10) {
			s += ":0" + sec;
		} else {
			s += ":" + sec;
		}
		return s;
	}

	public void deleteAction() {
		for (int i = 0; i < actions.size(); i++) {
			if (actions.get(i).isSelected()) {
				actions.remove(i);
				i--;
			}
		}
	}

	public void changeScale(double d) {
		scale += d;
		if(scale<1.1) {
			scale=1.1;
		}
		if(scale>6) {
			scale=6;
		}
	}

	public void scroll(int i) {
		scroll+=i;
		if(scroll<0) {
			scroll=0;
		}
	}
	
	public boolean transferOffGas() {
		int g=0;
		int base=-1;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).scvCountGas()>g) {
				g = bases.get(i).scvCountGas();
				base = i;
			}
		}
		//System.out.println(printTime() + "   <Model:transferOffGas> Base found:" + base);
		if(base>=0) {
			if(bases.get(base).transferSCVOffGas()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean transferToGas() {
		int f=3;
		int base=-1;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).isComplete() && bases.get(i).freeRefineries()<f && bases.get(i).freeRefineries()>0) {
				base=i;
				f=bases.get(i).freeRefineries();
			}
		}
		if(base>=0) {
			if(bases.get(base).transferDroneToGas()) {
				return true;
			} else {
				//				System.out.println("<Model> couldn't transfer drones for some reason");
				return false;
			}
		} else {
			//			System.out.println("<Model> Couldn't find a base!");
			return false;
		}
	}


	public String printBuild() {
		String s="";
		for(int t = 0;t < maxTime;t++) {
			for(int i = 0;i < actions.size();i++) {
				if(actions.get(i).getStartTime()==t) {
					s+=printTime(t) + " - " + actions.get(i).getSupplyPoint() + " - " + actions.get(i).toString()+"\n";
				}
			}
		}
		return s;
	}

	public void selectNext() {
		int i = 0;
		int k=-1;
		while(i < actions.size()) {
			if(actions.get(i).isSelected()) {
				k = i;
			}
			i++;
		}
		if(k>=0 && k == actions.size()-1) {
			actions.get(k).deselect();
			actions.get(0).select();
		} else if(k>=0){
			actions.get(k).deselect();
			actions.get(k+1).select();
		}
	}

}
