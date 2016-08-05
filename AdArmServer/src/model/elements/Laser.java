package model.elements;

import model.actions.Action;
import model.actions.Method;

public class Laser extends Element {
	private Robot robot;

	public Laser(Robot robot) {
		this.robot = robot;
		value = 21;
	}

	public void on(int intensity) {
		robot.execute(new Action(this, Method.setIntensity, intensity));
	}

	public void on() {
		on(255);
	}

	public void off() {
		on(0);
	}

}