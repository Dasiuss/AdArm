package model.elements;

import model.actions.Action;
import model.actions.Method;

public class Accessory extends Element {
	private Robot robot;

	public Accessory(Robot robot) {
		this.robot = robot;
		code = 21;
		lastPos = 0;
	}

	public void on(int intensity) {
		robot.execute(new Action(this, Method.setIntensity, intensity));
		lastPos = intensity;
	}

	public void on() {
		on(255);
		lastPos = 255;
	}

	public void off() {
		on(0);
		lastPos = 0;
	}

	public void toggle() {
		if (lastPos == 0) {
			on();
		} else {
			off();
		}
	}

}
