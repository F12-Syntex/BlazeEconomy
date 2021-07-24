package com.devnecs.cooldown;

public abstract class SingleUseCooldownEntity extends CooldownEntity{
	
	public int timer;
	public boolean running = true;
	
	public SingleUseCooldownEntity(int timer) {
		this.timer = timer;
	}
	
	@Override
	public void onTick() {
		if(!running) return;
		this.timer -= 1;
		this.tick();
		if(timer <= 0) {
			this.running = false;
			this.onTaskEnd();
		}
	}
	
	public abstract void tick();
	public abstract void onTaskEnd();

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
}
