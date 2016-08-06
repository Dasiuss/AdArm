package controller;

import model.elements.Robot;

public abstract class Program {
	protected final Robot robot = new Robot();

	public void start() {
		robot.system.attach();
		run();
		robot.system.wait(500);
		robot.system.detach();
	}

	abstract protected void run();
}
