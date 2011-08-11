package com.deranged.tvbo;

public class MineralPatch extends Resource {

	private int distance;
	private Mule mule;

	public MineralPatch(int r, int d) {
		super(r);
		scvsMining = new SCV[max];
		distance = d;
		// TODO Auto-generated constructor stub
	}

	public void update() {
		super.update();
		if(mule!=null) {
			mule.update();
			if(mule.getLife()==0) {
				mule=null;
			}
		}
	}

	public boolean addSCV(SCV s) {
		if(scvs<max) {
			if(scvsMining[scvs]==null) {
				//s.setJob(0);
				s.setResource(this);
				scvsMining[scvs]=s;
				scvs++;
				setJobtimes();
				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public void setJobtimes() {
		double n=0;
		if(distance==1) {
			if(scvs==1) {
				n = (20.0/3.0);  // 6.67
			} else if(scvs==2) {
				n = (20.0/3.0);  // 6.67
			} else if(scvs==3) {
				n = (270.0/31.0);// 8.7097
			} else {
				System.out.println("Error: <MineralPatch:setJobtimes> Too many scvs on this patch!");
			}
		} else if(distance==2) {
			if(scvs==1) {
				n = (15.0/2.0);  // 7.5
			} else if(scvs==2) {
				n = (15.0/2.0);  // 7.5
			} else if(scvs==3) {
				n = (270.0/31.0);// 8.7097
			} else {
				System.out.println("Error: <MineralPatch:setJobtimes> Too many scvs on this patch!");
			}
		} else if(distance==3) {
			if(scvs==1) {
				n = (360.0/47.0); // 7.6
			} else if(scvs==2) {
				n = (360.0/47.0); // 7.6
			} else if(scvs==3) {
				n = (270.0/31.0); // 8.7097
			} else {
				System.out.println("Error: <MineralPatch:setJobtimes> Too many scvs on this patch!");
			}
		} else {
			System.out.println("Error: <MineralPatch:setJobtimes> Invalid distance parameter");
		}
		for(int i = 0;i < max;i++) {
			if(scvsMining[i]!=null) {
				scvsMining[i].setJobtime(n, 0);
			}
		}
	}

	public boolean hasMule() {
		if(mule!=null && mule.getLife()>0) {
			return true;
		} else {
			return false;
		}
	}

	public void addMule(Model model) {
		mule = new Mule(model);
		mule.setResource(this);
		if(distance==1) {
			mule.setJobtime(9, 0);
		} else if(distance==2) {
			mule.setJobtime(9.5, 0);
		} else if(distance==3) {
			mule.setJobtime(10, 0);
		}

	}
}
