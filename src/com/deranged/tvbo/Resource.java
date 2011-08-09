package com.deranged.tvbo;

public class Resource {
	
	public int resource; // either minerals or gas
	public SCV[] scvsMining;
	public int max=3;
	public int scvs=0; // current number of probes mining
	
	public Resource(int r) {
		resource = r;
		scvsMining = new SCV[max];
	}
	
	public void update() {
		for(int i = 0;i < max;i++) {
			if(scvsMining[i]!=null) {
				scvsMining[i].update();
			}
		}
		if(resource<=0) {
			for(int i =0;i < max;i++) {
				if(scvsMining[i]!=null) {
					scvsMining[i].setJob(3); //idle
				}
			}
		}
	}
	
	public boolean reduceResource(int r) {
		if(resource>=r) {
			resource-=r;
			return true;
		} else {
			return false;
		}
	}

	public SCV removeSCV() {
		SCV s;
		if(scvs>0) {
			//System.out.println("<MineralPatch:removeDrone> "+dronesMining[0] + " " + dronesMining[1] + " " + dronesMining[2] + "." );
			s = scvsMining[scvs-1];
			scvsMining[scvs-1] = null;
			scvs--;
			s.setJob(3);
			return s;
		} else {
			return null;
		}
		 
	}
	
	public int getSCVCount() {
		return scvs;
	}

}
