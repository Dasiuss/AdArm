package model.elements;

import model.actions.Action;
import model.actions.Method;

public class System extends Element {
	public System() {
		value = 20;
	}

	public Action wait(int period) {
		return new Action(this, Method.delay, period);
	}

	public Action attach() {
		return new Action(this, Method.attach);
	}

	public Action detach() {
		return new Action(this, Method.detach);
	}

	public Action setStepDelay(int stepDelay) {
		return new Action(this, Method.setStepDelay, stepDelay);
	}

	public Action setStepDelayDefault() {
		return new Action(this, Method.setStepDelay, 2);
	}

}