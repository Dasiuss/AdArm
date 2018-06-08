package model.elements;

import model.actions.Action;
import model.actions.Method;

public class Servo extends Element {
	private Robot robot;

	public Servo(int code, int startPos, Robot robot) {
		this.code = code;
		this.robot = robot;
		lastPos = startPos;
	}

	public void moveFast(int degree) {
		robot.execute(new Action(this, Method.moveFast, degree));
		lastPos = Math.max(Math.min(degree, 180), 0);
	}

	public void move(int degree) {
		robot.execute(new Action(this, Method.move, degree));
		lastPos = Math.max(Math.min(degree, 180), 0);
	}

	public void moveRelative(int degree) {
		robot.execute(new Action(this, Method.move, lastPos + degree));
		lastPos = Math.max(Math.min(lastPos + degree, 180), 0);
	}

}
