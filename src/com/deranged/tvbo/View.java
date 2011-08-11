package com.deranged.tvbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel {

	private Model model;
	
	private int border;
	private double scale;
	private int scroll;
	private int spacing;
	private int thickness;
	
	private int width;
	private int height;
	
	private Font font;
	private Font littleFont;
	
	//units
	private Image scvImg=null;
	private Image muleImg=null;
	private Image marineImg=null;
	private Image marauderImg=null;
	private Image ghostImg=null;
	private Image reaperImg=null;
	private Image hellionImg=null;
	private Image siegetankImg=null;
	private Image thorImg=null;
	private Image vikingImg=null;
	private Image medivacImg=null;
	private Image ravenImg=null;
	private Image bansheeImg=null;
	private Image battlecruiserImg=null;

	private Image barracksImg=null;
	private Image refineryImg=null;
	private Image factoryImg=null;
	private Image starportImg=null;
	private Image armoryImg=null;
	private Image engineeringbayImg=null;
	private Image fusioncoreImg=null;
	private Image techlabImg=null;
	private Image reactorImg=null;
	private Image commandcenterImg=null;
	private Image orbitalcommandImg=null;
	private Image missileturretImg=null;
	private Image bunkerImg=null;
	private Image ghostacademyImg=null;
	private Image planetaryfortressImg=null;
	private Image supplydepotImg=null;
	
	private Image combatShieldImg=null;
	private Image stimPackImg=null;
	private Image concussiveShellsImg=null;
	private Image nitroPackImg=null;
	private Image infernalPreIgniterImg=null;
	private Image siegeTechImg=null;
	private Image strikeCannonsImg=null;
	private Image caduceusReactorImg=null;
	private Image corvidReactorImg=null;
	private Image durableMaterialsImg=null;
	private Image seekerMissileImg=null;
	private Image cloakingFieldImg=null;
	
	private Image infantryWeaponsLevel1Img=null;
	private Image infantryWeaponsLevel2Img=null;
	private Image infantryWeaponsLevel3Img=null;
	private Image infantryArmorLevel1Img=null;
	private Image infantryArmorLevel2Img=null;
	private Image infantryArmorLevel3Img=null;
	private Image neosteelFrameImg=null;
	private Image buildingArmorImg=null;
	private Image hiSecAutoTrackingImg=null;
	
	private Image vehicleWeaponsLevel1Img=null;
	private Image vehicleWeaponsLevel2Img=null;
	private Image vehicleWeaponsLevel3Img=null;
	private Image vehiclePlatingLevel1Img=null;
	private Image vehiclePlatingLevel2Img=null;
	private Image vehiclePlatingLevel3Img=null;
	private Image shipWeaponsLevel1Img=null;
	private Image shipWeaponsLevel2Img=null;
	private Image shipWeaponsLevel3Img=null;
	private Image shipPlatingLevel1Img=null;
	private Image shipPlatingLevel2Img=null;
	private Image shipPlatingLevel3Img=null;
	
	private Image personalCloakingImg=null;
	private Image nukeImg=null;
	private Image moebiusReactorImg=null;
	private Image behemothReactorImg=null;
	private Image weaponRefitImg=null;
	
	private Image scanImg=null;
	private Image liftImg=null;
	private Image landImg=null;
	private Image gasImg=null;
	private Image mineralsImg=null;
	private Image calldownsupplyImg=null;
	
	
	public View(Model model) {
		super();
		this.model = model;
		font = new Font("Tahoma", 0, 11);
		littleFont = new Font("Tahoma", 0, 9);
		setUpImages();
	}
	
	public void paint(Graphics g) {
		width=this.getWidth();
		height=this.getHeight();
		//Font defaultFont = g.getFont();
		//System.out.println("Fontname:"+defaultFont.getFontName() + " family:" + defaultFont.getFamily()
		//		+ " size:" +defaultFont.getSize() + " style:"+defaultFont.getStyle());
		//System.out.println(width +" x " + height);
		drawAllIcons(g);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(new Color(150,150,220,200));
		g.setFont(font);
		// LINES //////////////////////////////////////////////////////////////////////////////////
		border = model.getBorder();
		scale = model.getScale();
		scroll = model.getScroll();
		spacing = model.getSpacing();
		thickness = model.getThickness();
		
		g.drawRect(border, border, width-2*border, height-2*border);
		for(int i = 0; i < width;i+=30) { //model.getMaxTime()
			if((int)(scale*(i-scroll)+border)<width-border) {
				g.setColor(Color.black);
				if((int)(scale*(i-scroll))>=0) {
					//draw time markers across top
					g.drawString(model.printTime(i), (int)(scale*(i-scroll)+border-10), border-4);
				}
				g.setColor(new Color(150,150,220,200));
				if((int)(scale*(i-scroll))>=0) {
					g.drawLine((int)(scale*(i-scroll)+border), border, (int)(scale*(i-scroll)+border), height-border);
				}
			}
		}
		
		for(int i = 50; i < height-2*border;i+=50) {
			g.setColor(new Color(150,150,150,100));
			g.drawLine(border, height-border-i, width-border, height-border-i);
			g.setColor(new Color(100,100,100,250));
			g.drawString(i+"", width-border+5, height-border-i+3);
		}
		// GRAPHS /////////////////////////////////////////////////////////////////////////////////
		int x1,y1,x2,y2;
		for(int i = 1+scroll;i < model.getMaxTime();i++) {
			// mineral graph
			g.setColor(new Color(150,150,220,150));
			x1=(int)(border+scale*(i-1-scroll));
			y1=height-border-model.getMineralGraph(i-1);
			x2=	(int)(border+scale*(i-scroll));
			y2=height-border-model.getMineralGraph(i);
			if(y1>border && y2>border && x2 < width-border) {
				g.drawLine(x1,y1,x2,y2);
			}
			// gas graph
			g.setColor(new Color(150,220,150,150));
			x1=(int)(border+scale*(i-1-scroll));
			y1=height-border-model.getGasGraph(i-1);
			x2=(int)(border+scale*(i-scroll));
			y2=height-border-model.getGasGraph(i);
			if(y1>border && y2>border && x2 < width-border) {
				g.drawLine(x1,y1,x2,y2);
			}
			// energy graph
			g.setColor(new Color(220,150,220,150));
			x1=(int)(border+scale*(i-1-scroll));
			y1=height-border-model.getEnergyGraph(i-1);
			x2=(int)(border+scale*(i-scroll));
			y2=height-border-model.getEnergyGraph(i);
			if(y1>border && y2>border && x2 < width-border) {
				if(y1>0 || y2>0) {
					g.drawLine(x1,y1,x2,y2);
				}
			}
		}
		// ACTIONS ////////////////////////////////////////////////////////////////////////////////
		int s = model.actionCount();
		int actionY;
		int t;
		String aName;
		boolean popup;
		SCAction action;
		int left;
		int len;
		int top;
		int optionsSize=0;
		for (int i = 0; i < s;i++) {
			action = model.getAction(i);
			actionY = action.getY();
			t = action.getStartTime();
			aName = action.toString();
			left = (int)(border+scale*(t-scroll));
			len = (int)(scale*action.getDuration());
			top = border+(spacing*actionY);
			popup = action.getPopup();
			if(action.isComplete()) {
				if(action.isSelected()) {
					g.setColor(new Color(150,220,150,150));
					g.fillRect(left, top, len, thickness);
					g.setColor(new Color(0,100,0,255));
					g.drawRect(left, top, len, thickness);
					if(action.getOptionsSize()>0) {
						g.setColor(new Color(150,220,150,150));
						g.fillRect(left+len-13, top+thickness, 13, 13);
						g.setColor(new Color(0,100,0,255));
						g.drawRect(left+len-13, top+thickness, 13, 13);
					}
					g.setColor(new Color(75,75,75,255));

					g.setFont(littleFont);
					g.drawString(action.getSupplyPoint(), left+5, top+24);
				} else {
					g.setColor(new Color(150,220,150,150));
					g.fillRect(left, top, len, thickness);
					g.setColor(new Color(150,220,150,200));
					g.drawRect(left, top, len, thickness);
					g.setColor(new Color(75,75,75,255));
					g.setFont(littleFont);
					g.drawString(action.getSupplyPoint(), left+5, top+24);
				}
			} else { // not complete
				if(action.isSelected()) {
					g.setColor(new Color(220,150,150,150));
					g.fillRect(left, top, len, thickness);
					g.setColor(new Color(100,0,0,255));
					g.drawRect(left, top, len, thickness);
					if(action.getOptionsSize()>0) {
						g.setColor(new Color(220,150,150,150));
						g.fillRect(left+len-13, top+thickness, 13, 13);
						g.setColor(new Color(100,0,0,255));
						g.drawRect(left+len-13, top+thickness, 13, 13);
					}
					g.setColor(new Color(255,0,0));
					g.setFont(littleFont);
					g.drawString(action.getErrorMsg(),left, top+24); // print error message
				} else {
					g.setColor(new Color(220,150,150,150));
					g.fillRect(left, top, len, thickness);
					g.setColor(new Color(220,150,150,200));
					g.drawRect(left, top, len, thickness);
					g.setColor(new Color(255,0,0));
					g.setFont(littleFont);
					g.drawString(action.getErrorMsg(),left, top+24); // print error message
				}

			}
			drawIcon(g, left+len-26, top+1, aName);
			g.setFont(font);
			g.drawString(aName, left+3, top+12);
		}

		for (int i = 0; i < s;i++) {
			action = model.getAction(i);
			popup = action.getPopup();
			aName = action.toString();
			left = (int)(border+scale*(action.getStartTime()-scroll));
			len = (int)(scale*action.getDuration());
			top = border+(spacing*action.getY());
			if(popup && action.isSelected()) {
				optionsSize = action.getOptionsSize();
				if(optionsSize>0) {
					g.setColor(new Color(200,200,200,200));
					g.fillRect(left+len, top+thickness, 60, optionsSize*14);
					g.setColor(new Color(100,100,100,200));
					g.drawRect(left+len, top+thickness, 60, optionsSize*14);
					g.setColor(new Color(100,100,100,255));
					for(int op = 0; op < optionsSize; op++) {
						g.drawString(action.getOption(op), left+len+3, top+thickness+(op*14)+11);
					}
				}
			}
			
		}
		
		// DRAW SCROLL AND ZOOM BOXES ////////////////////////////////////

		g.setColor(new Color(150,150,220,200));
		int ay = height-border+5;
		int ax = width-border-30;
//		int pw=4;
//		int pl=24;
		g.drawRect(ax, ay, 30, 30);
		g.drawRect(ax-35, ay, 30, 30);
		g.drawRect(ax-80, ay, 30, 30);
		g.drawRect(ax-115, ay, 30, 30);
		int ad = 5;
		//right arrow
		g.drawLine(ax+ad,ay+10,ax+ad+10,ay+10);
		g.drawLine(ax+ad+10,ay+10,ax+ad+10,ay+5);
		g.drawLine(ax+ad+10,ay+5,ax+25,ay+15);
		g.drawLine(ax+ad+10,ay+25,ax+25,ay+15);
		g.drawLine(ax+ad+10,ay+20,ax+ad+10,ay+25);
		g.drawLine(ax+ad,ay+20,ax+ad+10,ay+20);
		g.drawLine(ax+ad,ay+10,ax+ad,ay+20);
		//left arrow
		ax=width-border-65;
		g.drawLine(ax+25,ay+10,ax+15,ay+10);
		g.drawLine(ax+15,ay+10,ax+15,ay+5);
		g.drawLine(ax+15,ay+5, ax+5,ay+15);
		g.drawLine(ax+15,ay+25,ax+5,ay+15);
		g.drawLine(ax+15,ay+20,ax+15,ay+25);
		g.drawLine(ax+25,ay+20,ax+15,ay+20);
		g.drawLine(ax+25,ay+10,ax+25,ay+20);
		// plus sign
		ax=width-border-110;
		g.drawLine(ax+12, ay+3, ax+18, ay+3);
		g.drawLine(ax+12, ay+27, ax+18, ay+27);
		g.drawLine(ax+3, ay+12, ax+3, ay+18);
		g.drawLine(ax+27, ay+12, ax+27, ay+18);
		
		g.drawLine(ax+12, ay+3, ax+12, ay+12);
		g.drawLine(ax+12, ay+27, ax+12, ay+18);
		g.drawLine(ax+18, ay+3, ax+18, ay+12);
		g.drawLine(ax+18, ay+27, ax+18, ay+18);
		
		g.drawLine(ax+3, ay+12, ax+12, ay+12);
		g.drawLine(ax+3, ay+18, ax+12, ay+18);
		g.drawLine(ax+27, ay+12, ax+18, ay+12);
		g.drawLine(ax+27, ay+18, ax+18, ay+18);
//		g.drawRect(width-border-107, ay+11, 24, 7);
//		g.drawRect(width-border-98, ay+3, 7, 24);

		//minus sign
		g.drawRect(width-border-142, ay+11, 24, 7);
		
		// MARQUEE SELECTION /////////////////////////////////////////////
		
		int mX1=model.getmX1();
		int mY1=model.getmY1();
		int mX2=model.getmX2();
		int mY2=model.getmY2();
		if(mX1!=mX2 && mY1!=mY2) {
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
			g.setColor(new Color(150,150,255,50));
			g.fillRect(mX1, mY1, mX2-mX1, mY2-mY1);
			g.setColor(new Color(100,100,200,150));
			g.drawRect(mX1, mY1, mX2-mX1, mY2-mY1);
			
		}
		
		
	}
	
	public void drawIcon(Graphics g, int x, int y, String name) {
		// UNITS /////////////////////////////////
		if(g==null) {
			System.out.println("graphics are null");
		} else {
			if(name.equals("SCV")) {						g.drawImage(scvImg, x, y, null);
			} else if(name.equals("Mule")) {				g.drawImage(muleImg, x, y, null);
			} else if(name.equals("Marine")) {				g.drawImage(marineImg, x, y, null);
			} else if(name.equals("Marauder")) {			g.drawImage(marauderImg, x, y, null);
			} else if(name.equals("Ghost")) {				g.drawImage(ghostImg, x, y, null);
			} else if(name.equals("Reaper")) {				g.drawImage(reaperImg, x, y, null);
			} else if(name.equals("Hellion")) {				g.drawImage(hellionImg, x, y, null);
			} else if(name.equals("SiegeTank")) {			g.drawImage(siegetankImg, x, y, null);
			} else if(name.equals("Thor")) {				g.drawImage(thorImg, x, y, null);
			} else if(name.equals("Viking")) {				g.drawImage(vikingImg, x, y, null);
			} else if(name.equals("Medivac")) {				g.drawImage(medivacImg, x, y, null);
			} else if(name.equals("Raven")) {				g.drawImage(ravenImg, x, y, null);
			} else if(name.equals("Banshee")) {				g.drawImage(bansheeImg, x, y, null);
			} else if(name.equals("Battlecruiser")) {		g.drawImage(battlecruiserImg, x, y, null);
			
			} else if(name.equals("Barracks")) {				g.drawImage(barracksImg, x, y, null);
			} else if(name.equals("Refinery")) {				g.drawImage(refineryImg, x, y, null);
			} else if(name.equals("Factory")) {					g.drawImage(factoryImg, x, y, null);
			} else if(name.equals("Starport")) {				g.drawImage(starportImg, x, y, null);
			} else if(name.equals("Armory")) {					g.drawImage(armoryImg, x, y, null);
			} else if(name.equals("EngineeringBay")) {			g.drawImage(engineeringbayImg, x, y, null);
			} else if(name.equals("FusionCore")) {				g.drawImage(fusioncoreImg, x, y, null);
			} else if(name.equals("TechLab")) {					g.drawImage(techlabImg, x, y, null);
			} else if(name.equals("Reactor")) {					g.drawImage(reactorImg, x, y, null);
			} else if(name.equals("CommandCenter")) {			g.drawImage(commandcenterImg, x, y, null);
			} else if(name.equals("OrbitalCommand")) {			g.drawImage(orbitalcommandImg, x, y, null);
			} else if(name.equals("MissileTurret")) {			g.drawImage(missileturretImg, x, y, null);
			} else if(name.equals("Bunker")) {					g.drawImage(bunkerImg, x, y, null);
			} else if(name.equals("GhostAcademy")) {			g.drawImage(ghostacademyImg, x, y, null);
			} else if(name.equals("PlanetaryFortress")) {		g.drawImage(planetaryfortressImg, x, y, null);
			} else if(name.equals("SupplyDepot")) {				g.drawImage(supplydepotImg, x, y, null);
			
			} else if(name.equals("CombatShield")) {			g.drawImage(combatShieldImg, x, y, null);
			} else if(name.equals("StimPack")) {				g.drawImage(stimPackImg, x, y, null);
			} else if(name.equals("ConcussiveShells")) {		g.drawImage(concussiveShellsImg, x, y, null);
			} else if(name.equals("NitroPack")) {				g.drawImage(nitroPackImg, x, y, null);
			} else if(name.equals("InfernalPreigniter")) {		g.drawImage(infernalPreIgniterImg, x, y, null);
			} else if(name.equals("SiegeTech")) {				g.drawImage(siegetankImg, x, y, null);
			} else if(name.equals("250mmStrikeCannons")) {		g.drawImage(strikeCannonsImg, x, y, null);
			} else if(name.equals("CaduceusReactor")) {			g.drawImage(caduceusReactorImg, x, y, null);
			} else if(name.equals("CorvidReactor")) {			g.drawImage(corvidReactorImg, x, y, null);
			} else if(name.equals("DurableMaterials")) {		g.drawImage(durableMaterialsImg, x, y, null);
			} else if(name.equals("SeekerMissile")) {			g.drawImage(seekerMissileImg, x, y, null);
			} else if(name.equals("CloakingField")) {			g.drawImage(cloakingFieldImg, x, y, null);
			} else if(name.equals("InfantryWeaponsLevel1")) {	g.drawImage(infantryWeaponsLevel1Img, x, y, null);
			} else if(name.equals("InfantryWeaponsLevel2")) {	g.drawImage(infantryWeaponsLevel2Img, x, y, null);
			} else if(name.equals("InfantryWeaponsLevel3")) {	g.drawImage(infantryWeaponsLevel3Img, x, y, null);
			} else if(name.equals("infantryArmorLevel1Img")) {	g.drawImage(infantryArmorLevel1Img, x, y, null);
			} else if(name.equals("infantryArmorLevel2Img")) {	g.drawImage(infantryArmorLevel2Img, x, y, null);
			} else if(name.equals("infantryArmorLevel3Img")) {	g.drawImage(infantryArmorLevel3Img, x, y, null);
			} else if(name.equals("NeosteelFrame")) {			g.drawImage(neosteelFrameImg, x, y, null);
			} else if(name.equals("BuildingArmor")) {			g.drawImage(buildingArmorImg, x, y, null);
			} else if(name.equals("HiSecAutoTracking")) {		g.drawImage(hiSecAutoTrackingImg, x, y, null);
			} else if(name.equals("VehicleWeaponsLevel1")) {	g.drawImage(vehicleWeaponsLevel1Img, x, y, null);
			} else if(name.equals("VehicleWeaponsLevel2")) {	g.drawImage(vehicleWeaponsLevel2Img, x, y, null);
			} else if(name.equals("VehicleWeaponsLevel3")) {	g.drawImage(vehicleWeaponsLevel3Img, x, y, null);
			} else if(name.equals("VehicleArmorLevel1")) {		g.drawImage(vehiclePlatingLevel1Img, x, y, null);
			} else if(name.equals("VehicleArmorLevel2")) {		g.drawImage(vehiclePlatingLevel2Img, x, y, null);
			} else if(name.equals("VehicleArmorLevel3")) {		g.drawImage(vehiclePlatingLevel3Img, x, y, null);
			} else if(name.equals("ShipWeaponsLevel1")) {		g.drawImage(shipWeaponsLevel1Img, x, y, null);
			} else if(name.equals("ShipWeaponsLevel2")) {		g.drawImage(shipWeaponsLevel2Img, x, y, null);
			} else if(name.equals("ShipWeaponsLevel3")) {		g.drawImage(shipWeaponsLevel3Img, x, y, null);
			} else if(name.equals("ShipArmorLevel1")) {			g.drawImage(shipPlatingLevel1Img, x, y, null);
			} else if(name.equals("ShipArmorLevel2")) {			g.drawImage(shipPlatingLevel2Img, x, y, null);
			} else if(name.equals("ShipArmorLevel3")) {			g.drawImage(shipPlatingLevel3Img, x, y, null);
			} else if(name.equals("PersonalCloaking")) {		g.drawImage(personalCloakingImg, x, y, null);
			} else if(name.equals("Nuke")) {					g.drawImage(nukeImg, x, y, null);
			} else if(name.equals("MoebiusReactor")) {			g.drawImage(moebiusReactorImg, x, y, null);
			} else if(name.equals("BehemothReactor")) {			g.drawImage(behemothReactorImg, x, y, null);
			} else if(name.equals("WeaponRefit")) {				g.drawImage(weaponRefitImg, x, y, null);

			} else if(name.equals("CalldownSupply")) {			g.drawImage(calldownsupplyImg, x, y, null);
			} else if(name.equals("Scan")) {					g.drawImage(scanImg, x, y, null);
			} else if(name.equals("LiftBarracks")) {					g.drawImage(liftImg, x, y, null);
			} else if(name.equals("LiftFactory")) {					g.drawImage(liftImg, x, y, null);
			} else if(name.equals("LiftStarport")) {					g.drawImage(liftImg, x, y, null);
			} else if(name.equals("LandBarracks")) {					g.drawImage(landImg, x, y, null);
			} else if(name.equals("LandFactory")) {					g.drawImage(landImg, x, y, null);
			} else if(name.equals("LandStarport")) {					g.drawImage(landImg, x, y, null);
			} else if(name.equals("+1 on gas")) {			g.drawImage(gasImg, x, y, null);
			} else if(name.equals("1 off gas")) {			g.drawImage(mineralsImg, x, y, null);
			} else {
				System.out.println("\""+name + "\" not found in <drawIcon>");
			}
		}
	}
	public void drawAllIcons(Graphics g) {
		// UNITS /////////////////////////////////
		int x=0;
		int y=0;
		if(g==null) {
			System.out.println("graphics are null");
		} else {
			g.drawImage(scvImg, x, y, null);
			g.drawImage(muleImg, x, y, null);
			g.drawImage(marineImg, x, y, null);
			g.drawImage(marauderImg, x, y, null);
			g.drawImage(ghostImg, x, y, null);
			g.drawImage(reaperImg, x, y, null);
			g.drawImage(hellionImg, x, y, null);
			g.drawImage(siegetankImg, x, y, null);
			g.drawImage(thorImg, x, y, null);
			g.drawImage(vikingImg, x, y, null);
			g.drawImage(medivacImg, x, y, null);
			g.drawImage(ravenImg, x, y, null);
			g.drawImage(bansheeImg, x, y, null);
			g.drawImage(battlecruiserImg, x, y, null);
			
			g.drawImage(barracksImg, x, y, null);
			g.drawImage(refineryImg, x, y, null);
			g.drawImage(factoryImg, x, y, null);
			g.drawImage(starportImg, x, y, null);
			g.drawImage(armoryImg, x, y, null);
			g.drawImage(engineeringbayImg, x, y, null);
			g.drawImage(fusioncoreImg, x, y, null);
			g.drawImage(techlabImg, x, y, null);
			g.drawImage(reactorImg, x, y, null);
			g.drawImage(commandcenterImg, x, y, null);
			g.drawImage(orbitalcommandImg, x, y, null);
			g.drawImage(missileturretImg, x, y, null);
			g.drawImage(bunkerImg, x, y, null);
			g.drawImage(ghostacademyImg, x, y, null);
			g.drawImage(planetaryfortressImg, x, y, null);
			g.drawImage(supplydepotImg, x, y, null);
			
			
			
			g.drawImage(combatShieldImg, x, y, null);
			g.drawImage(stimPackImg, x, y, null);
			g.drawImage(concussiveShellsImg, x, y, null);
			g.drawImage(nitroPackImg, x, y, null);
			g.drawImage(infernalPreIgniterImg, x, y, null);
			g.drawImage(siegetankImg, x, y, null);
			g.drawImage(strikeCannonsImg, x, y, null);
			g.drawImage(caduceusReactorImg, x, y, null);
			g.drawImage(corvidReactorImg, x, y, null);
			g.drawImage(durableMaterialsImg, x, y, null);
			g.drawImage(seekerMissileImg, x, y, null);
			g.drawImage(cloakingFieldImg, x, y, null);
			g.drawImage(infantryWeaponsLevel1Img, x, y, null);
			g.drawImage(infantryWeaponsLevel2Img, x, y, null);
			g.drawImage(infantryWeaponsLevel3Img, x, y, null);
			g.drawImage(infantryArmorLevel1Img, x, y, null);
			g.drawImage(infantryArmorLevel2Img, x, y, null);
			g.drawImage(infantryArmorLevel3Img, x, y, null);
			g.drawImage(neosteelFrameImg, x, y, null);
			g.drawImage(buildingArmorImg, x, y, null);
			g.drawImage(hiSecAutoTrackingImg, x, y, null);
			g.drawImage(vehicleWeaponsLevel1Img, x, y, null);
			g.drawImage(vehicleWeaponsLevel2Img, x, y, null);
			g.drawImage(vehicleWeaponsLevel3Img, x, y, null);
			g.drawImage(vehiclePlatingLevel1Img, x, y, null);
			g.drawImage(vehiclePlatingLevel2Img, x, y, null);
			g.drawImage(vehiclePlatingLevel3Img, x, y, null);
			
			g.drawImage(shipWeaponsLevel1Img, x, y, null);
			g.drawImage(shipWeaponsLevel2Img, x, y, null);
			g.drawImage(shipWeaponsLevel3Img, x, y, null);
			g.drawImage(shipPlatingLevel1Img, x, y, null);
			g.drawImage(shipPlatingLevel2Img, x, y, null);
			g.drawImage(shipPlatingLevel3Img, x, y, null);
			g.drawImage(personalCloakingImg, x, y, null);
			g.drawImage(nukeImg, x, y, null);
			g.drawImage(moebiusReactorImg, x, y, null);
			g.drawImage(behemothReactorImg, x, y, null);
			g.drawImage(weaponRefitImg, x, y, null);

			g.drawImage(calldownsupplyImg, x, y, null);
			g.drawImage(scanImg, x, y, null);
			g.drawImage(liftImg, x, y, null);
			g.drawImage(liftImg, x, y, null);
			g.drawImage(liftImg, x, y, null);
			g.drawImage(landImg, x, y, null);
			g.drawImage(landImg, x, y, null);
			g.drawImage(landImg, x, y, null);
			g.drawImage(gasImg, x, y, null);
			g.drawImage(mineralsImg, x, y, null);

		}
	}
	

	public void setUpImages() {
		// UNITS /////////////////////////////////
		//System.out.println("setting up images");
		URL url;
//
		try {url = getClass().getResource("/assets/scv.png");			scvImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/mule.png");			muleImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/marine.png");		marineImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/marauder.png");		marauderImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/ghost.png");			ghostImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/reaper.png");		reaperImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/hellion.png");		hellionImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/siegetank.png");		siegetankImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/thor.png");			thorImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/viking.png");		vikingImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/medivac.png");		medivacImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/raven.png");			ravenImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/banshee.png");		bansheeImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/battlecruiser.png");	battlecruiserImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
////
		try {url = getClass().getResource("/assets/barracks.png");	barracksImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/refinery.png");	refineryImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/factory.png");	factoryImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/starport.png");	starportImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/armory.png");	armoryImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/engineeringbay.png");	engineeringbayImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/fusioncore.png");	fusioncoreImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/techlab.png");	 techlabImg= Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/reactor.png");	reactorImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/commandcenter.png");	commandcenterImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/orbitalcommand.png");	orbitalcommandImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/missileturret.png");	missileturretImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/bunker.png");	bunkerImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/ghostacademy.png");	ghostacademyImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/planetaryfortress.png");	 planetaryfortressImg= Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/supplydepot.png");	supplydepotImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
////		
		try {url = getClass().getResource("/assets/combatshield.png");		combatShieldImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/stimpack.png");			stimPackImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/concussiveshells.png");	concussiveShellsImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/nitropacks.png");		nitroPackImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/infernalpreigniter.png");	infernalPreIgniterImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/siegetech.png");			siegeTechImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/250mmstrikecannons.png");	strikeCannonsImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/caduceusreactor.png");	caduceusReactorImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/corvidreactor.png");		corvidReactorImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/durablematerials.png");	durableMaterialsImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/seekermissile.png");		seekerMissileImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/cloakingfield.png");		cloakingFieldImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/infantryweapons1.png");	infantryWeaponsLevel1Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/infantryweapons2.png");	infantryWeaponsLevel2Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/infantryweapons2.png");	infantryWeaponsLevel3Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/infantryarmor1.png");	infantryArmorLevel1Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/infantryarmor2.png");	infantryArmorLevel2Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/infantryarmor3.png");	infantryArmorLevel3Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/neosteelframe.png");		neosteelFrameImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/buildingarmor.png");		buildingArmorImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/hisecautotracking.png");	hiSecAutoTrackingImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/vehicleweapons1.png");	vehicleWeaponsLevel1Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/vehicleweapons2.png");	vehicleWeaponsLevel2Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/vehicleweapons3.png");	vehicleWeaponsLevel3Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/vehicleplating1.png");	vehiclePlatingLevel1Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/vehicleplating2.png");	vehiclePlatingLevel2Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/vehicleplating3.png");	vehiclePlatingLevel3Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/shipweapons1.png");		shipWeaponsLevel1Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/shipweapons2.png");		shipWeaponsLevel2Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/shipweapons3.png");		shipWeaponsLevel3Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/shipplating1.png");		shipPlatingLevel1Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/shipplating2.png");		shipPlatingLevel2Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/shipplating3.png");		shipPlatingLevel3Img = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/personalcloaking.png");	personalCloakingImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/nuke.png");				nukeImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/moebiusreactor.png");	moebiusReactorImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/behemothreactor.png");	behemothReactorImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/weaponrefit.png");		weaponRefitImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
//		
//
		try {url = getClass().getResource("/assets/transferoffgas.png");		mineralsImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/transfertogas.png");		gasImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/scan.png");		scanImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/calldownsupply.png");		calldownsupplyImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/lift.png");		liftImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
		try {url = getClass().getResource("/assets/land.png");		landImg = Toolkit.getDefaultToolkit().getImage(url);} catch (NullPointerException e) {e.printStackTrace();}
	}
}
