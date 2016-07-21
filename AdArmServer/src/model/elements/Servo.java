package model.elements;

import model.actions.Action;
import model.actions.Method;

public class Servo extends Element {
	public Servo(int value) {
		this.value = value;
	}

	public Action moveFast(int degree) {
		return new Action(this, Method.moveFast, degree);
	}

	public Action move(int degree) {
		return new Action(this, Method.move, degree);
	}
}