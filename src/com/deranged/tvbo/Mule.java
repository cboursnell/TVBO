package com.deranged.tvbo;

public class Mule extends SCObject {
	
	private Resource resource;
	private int life;
	private int job;
	private double jobtime;
	private double JOBTIME;
	
	public Mule(Model model) {
		super(model, "Mule");
		complete=true;
		life=90;
		job=0;
		
	}
	
	public void update() {
		if(job==0 && life>0) {
			if(jobtime<=0) {
				jobtime+=JOBTIME;
				if(resource.reduceResource(30)) {
					model.addMinerals(30);
				} else {
					System.out.println(model.printTime() + "   <Mule> Mineral patch depleted");
					job=3;
				}
			}
		}
		if(jobtime>0) {
			jobtime--;
		}
		if(life>0) {
			life--;
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
	
	public int getLife() {
		return life;
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
