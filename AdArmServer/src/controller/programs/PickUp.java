package controller.programs;

import model.ProgramsUtils;
import model.elements.Robot;

public class PickUp {

	private Robot robot = new Robot();

	public static void main(String[] args) {
		new PickUp();
	}

	public PickUp() {
		robot.system.setStepDelay(10);

		robot.C0.move(180);
		robot.C2.move(90);
		robot.C1.move(45);

		robot.C0.move(0);
		robot.C1.moveFast(0);
		robot.C2.move(0);

		robot.system.wait(1000);
		robot.C0.move(100);
		robot.system.wait(500);
		ProgramsUtils.startPosition(robot);
	}
}
