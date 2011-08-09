package com.deranged.tvbo;

public class SCV extends SCObject {

	private int job;
	private double jobtime;
	private double JOBTIME;
	
	private Resource resource;
	
	public SCV(Model model) {
		super(model, "SCV");
		job=3; // 0 = mining, 1 = gas, 2 = building, 3 = idle
		jobtime=0;
		JOBTIME=0;
		// TODO Auto-generated constructor stub
	}
	public void update() {
		super.update();
		if(complete) {
			if(job==0) {
				if(jobtime<=0) {
					jobtime+=JOBTIME;
					if(resource.reduceResource(5)) {
						model.addMinerals(5);
					} else {
						System.out.println(model.printTime() + " Mineral patch depleted");
						job=3;
					}
				}
			} else if(job==1) {
				if(jobtime<=0) {
					jobtime+=JOBTIME;
					if(resource.reduceResource(4)) {
						model.addGas(4);
					} else {
						System.out.println(model.printTime() + " Vespene Geyser depleted");
						job=3;
					}
				}
			} else if(job==2) {
				if(jobtime<=0) {
					job=0;
				}
			} else if(job==3) {
				
			}
			if(jobtime>0) {
				jobtime--;
			}
		}
	}
	
	public void setJobtime(double time, int job) {
		if(this.job == job) {
			JOBTIME = time;
		} else {
			this.job=job;
			jobtime=time;
			JOBTIME=time;
		}
	}


	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public void setResource(Resource r) {
		resource = r;
	}


}
