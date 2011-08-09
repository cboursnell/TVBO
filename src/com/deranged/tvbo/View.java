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
	private int thickness=15;
	
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
		SCAction action;
		int left;
		int len;
		int top;
		for (int i = 0; i < s;i++) {
			action = model.getAction(i);
			actionY = action.getY();
			t = action.getStartTime();
			aName = action.toString();
			left = (int)(border+scale*(t-scroll));
			len = (int)(scale*action.getDuration());
			top = border+(spacing*actionY);
			if(action.isComplete()) {
				if(action.isSelected()) {
					g.setColor(new Color(150,220,150,150));
					g.fillRect(left, top, len, thickness);
					g.setColor(new Color(0,100,0,255));
					g.drawRect(left, top, len, thickness);
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
			} else {
				if(action.isSelected()) {
					g.setColor(new Color(220,150,150,150));
					g.fillRect(left, top, len, thickness);
					g.setColor(new Color(100,0,0,255));
					g.drawRect(left, top, len, thickness);
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
		
	}
}
