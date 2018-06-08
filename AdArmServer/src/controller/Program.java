package controller;

import model.elements.Robot;

public abstract class Program implements Runnable{
	protected final Robot robot = new Robot();

	public void run() {
		robot.system.attach();
		start();
		robot.system.wait(500);
		robot.system.detach();
	}

	protected abstract void start();
}
