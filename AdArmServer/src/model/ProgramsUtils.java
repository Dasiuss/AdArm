package model;

import model.elements.Robot;

public abstract class ProgramsUtils {

	public static void startPosition(Robot robot) {
		robot.C0.move(20);
		robot.C1.move(0);
		robot.C2.move(0);
	}

	public static void startPositionFast(Robot robot) {
		robot.C0.moveFast(20);
		robot.C1.moveFast(0);
		robot.C2.moveFast(0);
	}
}
