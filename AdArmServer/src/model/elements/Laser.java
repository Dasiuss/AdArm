package model.elements;

import model.actions.Action;
import model.actions.Method;

public class Laser extends Element {
	public Laser() {
		value = 21;
	}

	public Action on(int intensity) {
		return new Action(this, Method.setIntensity, intensity);
	}

	public Action on() {
		return on(255);
	}

	public Action off() {
		return on(0);
	}

}