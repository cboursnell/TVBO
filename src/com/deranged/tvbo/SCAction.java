package com.deranged.tvbo;

import java.util.ArrayList;

public class SCAction {
	
	protected Model model;
	protected String name;
	
	protected String supplyPoint;
	
	private int startTime;
	protected int preactionTime;
	private int y;
	protected boolean complete;
	protected boolean preactionComplete;
	
	protected int duration;
	//private final int DURATION;
	private boolean selected;
	protected String errorMsg;
	private boolean popup;
	protected String option; // used in scactionbuildaddon
	protected ArrayList<String> options;
	
	public SCAction(Model model, int startTime, int y, int duration, String name) {
		this.model = model;
		this.startTime = startTime;
		this.y = y;
		this.duration = duration;
		//DURATION = duration;
		this.name = name;
		errorMsg = "";
		supplyPoint = "";
		complete=false;
		selected=false;
		preactionTime=0;
		preactionComplete=false;
		popup=false;
		options = new ArrayList<String>();
	}
	
	public void reset() {
		complete=false;
		errorMsg = "";
		supplyPoint = "";
		//selected=false;
	}
	
	public void addStartTime(int x) {
		startTime+=x;
		if(startTime<0) {
			startTime-=x;
		}
	}
	public void moveY(int dy) {
		y+=dy;
		if(y<1 || y > (model.getHeight()/model.getSpacing())) {
			y-=dy;
		}
	}
	
	public boolean execute() {
		return true;
	}
	
	public boolean preaction() {
		return true;
	}

	public Model getModel() {
		return model;
	}

	public String getName() {
		return name;
	}

	public int getStartTime() {
		return startTime;
	}
	public int getPreactionTime() {
		return getStartTime()-preactionTime;
	}

	public int getY() {
		return y;
	}

	public boolean isComplete() {
		return complete;
	}

	public int getDuration() {
		return duration;
	}

	/*public int getDURATION() {
		return DURATION;
	}*/

	public boolean isSelected() {
		return selected;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void select() {
		selected = true;
	}
	public void deselect() {
		selected = false;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSupplyPoint() {
		return supplyPoint;
	}
	public void setSupplyPoint(String supplyPoint) {
		this.supplyPoint = supplyPoint;
	}
	public boolean getPopup() {
		return popup;
	}
	public int getOptionsSize() {
		return options.size();
	}
	public String getOption(int i) {
		return options.get(i);
	}
	public void setOption(int i) {
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public void togglePopup() {
		if(popup) {
			popup=false;
		} else {
			popup=true;
		}
	}
	public void setPopup(boolean f) {
		popup = f;
	}
	
	public String toString() {
		return name;
	}
	
}
