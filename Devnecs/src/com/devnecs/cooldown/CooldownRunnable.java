package com.devnecs.cooldown;

public class CooldownRunnable {
	
	private int timer;
	private Runnable execution;
	
	public CooldownRunnable(int timer, Runnable execution) {
		System.out.println("Timer set to: " + timer);
		this.setTimer(timer);
		this.execution = execution;
	}
	
	public void onTick() {}
	
	public void onComplete() {
		this.execution.run();
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

}
