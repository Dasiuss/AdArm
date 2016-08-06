package controller.programs;

import controller.Program;
import model.ProgramsUtils;

public class PickUp extends Program {

	public static void main(String[] args) {
		new PickUp().start();
	}

	@Override
	protected void run() {
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
