package com.deranged.tvbo;

public class SCAddon extends SCStructure {

	private String attachedTo;
	
	public SCAddon(Model model, String name) {
		super(model, name);
	}

	public String getAttachedTo() {
		return attachedTo;
	}

	public void setAttachedTo(String attachedTo) {
		this.attachedTo = attachedTo;
		//System.out.println("Attached to " + attachedTo);
	}

	
}
