package com.deranged.tvbo;

public class VespeneGeyser extends Resource{
	
	private SCStructure refinery;
	
	public VespeneGeyser(int r) {
		super(r);
		scvs = 0;
		
	}
	
	public void update() {
		super.update();
		if(refinery!=null) {
			refinery.update();
		}
	}
	
	public void addRefinery(Model model) {
		refinery = new SCStructure(model, "Refinery");
	}
	
	public boolean hasRefinery() {
		if(refinery==null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean hasCompleteRefinery() {
		if(refinery==null) {
			return false;
		} else {
			if(refinery.isComplete()) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean addSCV(SCV s) {
		if(scvs<max) {
			if(scvsMining[scvs]==null) {
				s.setResource(this);
				scvsMining[scvs]=s;
				scvs++;
				setJobtimes(1);
				return true;
			} else {
				return true;
			}
		} else {
			System.out.println("<VespeneGeyser> Too many SCVs already here");
			return false;
		}
	}

	public void setJobtimes(int job) {
		double n = 0;
		if(scvs==1) {
			n = (240.0/43.0);  // 5.58
		} else if(scvs==2) {
			n = (240.0/43.0);  // 5.58
		} else if(scvs==3) {
			n = (1440.0/229.0);// 6.2882
		} else {
			System.out.println("Error: <VespeneGeyser:setJobtimes> Too many scvs on this geyser!");
		}
		for(int i = 0;i < 3;i++) {
			if(scvsMining[i]!=null) {
				scvsMining[i].setJobtime(n,job);
			}
		}
	}

}
