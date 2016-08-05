package controller.programs;

import controller.Program;
import model.elements.Robot;

public class SimpleMaxReachMotion extends Program {

	private Robot robot = new Robot();

	public static void main(String[] args) {
		new SimpleMaxReachMotion().start();
	}

	@Override
	protected void run() {
		robot.system.setStepDelay(5);

		robot.C0.move(180);
		robot.C0.move(20);
		robot.C1.move(180);
		robot.C1.move(0);
		robot.C2.move(180);
		robot.C2.move(0);

		for (int i = 0; i < 256; i += 10) {
			robot.laser.on(i);
		}
		for (int i = 255; i >= 0; i -= 10) {
			robot.laser.on(i);
		}
		robot.laser.off();

	}
}
