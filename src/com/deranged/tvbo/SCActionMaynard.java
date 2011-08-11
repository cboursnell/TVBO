package com.deranged.tvbo;


public class SCActionMaynard extends SCAction {
	public SCActionMaynard(Model model, int startTime, int y) {
		super(model, startTime, y, 20, "Maynard");
		// TODO Auto-generated constructor stub
	}

	public boolean execute() {
		boolean f = true;
		if(complete) {
			f=false;
		} else if(model.completedBases()<2) {
			f=false;
			errorMsg="NO BASE";
		} else if(!model.maynard()) {
			f=false;
			errorMsg="UNKNOWN";
		}
		if(f) {
			complete =true;
		}
		return f;
	}

}
