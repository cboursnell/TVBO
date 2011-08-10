package com.deranged.tvbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

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
	
	public View(Model model) {
		super();
		this.model = model;
		font = new Font("Tahoma", 0, 11);
		littleFont = new Font("Tahoma", 0, 9);
	}
	
	public void paint(Graphics g) {
		width=this.getWidth();
		height=this.getHeight();
		//Font defaultFont = g.getFont();
		//System.out.println("Fontname:"+defaultFont.getFontName() + " family:" + defaultFont.getFamily()
		//		+ " size:" +defaultFont.getSize() + " style:"+defaultFont.getStyle());
		//System.out.println(width +" x " + height);
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
					if(aName.equals("TechLab") || aName.equals("Reactor")) {
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
					if(aName.equals("TechLab") || aName.equals("Reactor")) {
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
}
