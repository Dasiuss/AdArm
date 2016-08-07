package model.elements;

import model.actions.Action;
import model.actions.Method;

public class Servo extends Element {
	private Robot robot;

	public Servo(int value, Robot robot) {
		this.value = value;
		this.robot = robot;
	}

	public void moveFast(int degree) {
		robot.execute(new Action(this, Method.moveFast, degree));
		lastPos = degree;
	}

	public void move(int degree) {
		robot.execute(new Action(this, Method.move, degree));
		lastPos = degree;
	}

}