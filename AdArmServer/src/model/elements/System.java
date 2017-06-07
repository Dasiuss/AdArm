package model.elements;

import model.actions.Action;
import model.actions.Method;

public class System extends Element {
	private Robot robot;

	public System(Robot robot) {
		this.robot = robot;
		code = 20;
	}

	public void wait(int period) {
		robot.execute(new Action(this, Method.delay, period));
	}

	public void attach() {
		robot.execute(new Action(this, Method.attach));
	}

	public void detach() {
		robot.execute(new Action(this, Method.detach));
	}

	public void setStepDelayMicros(int stepDelay) {
		robot.execute(new Action(this, Method.setStepDelay, stepDelay));
	}

	public void setStepDelay(int stepDelay) {
		robot.execute(new Action(this, Method.setStepDelay, stepDelay * 1000));
	}

	public void setStepDelayDefault() {
		robot.execute(new Action(this, Method.setStepDelay, 2000));
	}

	public void setStepDelaySUp(int stepDelay) {
		robot.execute(new Action(this, Method.setStepDelaySUp, stepDelay * 1000));
	}

	public void setStepDelaySDown(int stepDelay) {
		robot.execute(new Action(this, Method.setStepDelaySDown, stepDelay * 1000));
	}
	// TODO default S up/down speed
}
