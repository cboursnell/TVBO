package com.deranged.tvbo;

public class SCActionScout extends SCAction {

	public SCActionScout(Model model, int startTime, int y) {
		super(model, startTime, y, 30, "Scout");
		option="30";
		options.add("15");
		options.add("30");
		options.add("45");
		options.add("60");
	}
	public SCActionScout(Model model, int startTime, int y, String option) {
		super(model, startTime, y, 30, "Scout");
		this.option = option;
		options.add("15");
		options.add("30");
		options.add("45");
		options.add("60");
	}
	
	public boolean execute() {
		boolean f=true;
		if(option.equals("")) {
			f = false;
			errorMsg = "SET OPTION";
		} else if(!model.scout(option)) {
			f = false;
			errorMsg = "UNKNOWN";
		}
		if(f) {
			complete = true;
		}
		return f;
		
	}
	
	public void setOption(int i) {
		option = options.get(i);
		duration = Integer.parseInt(option);
		//System.out.println("<SCActionBuildAddon> Building set to " + option);
		model.reset();
		model.play();
	}

}
