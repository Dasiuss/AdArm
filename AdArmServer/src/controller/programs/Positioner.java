package controller.programs;

import controller.Program;
import model.elements.Robot;

public class Positioner extends Program {

	private Robot robot = new Robot();

	public static void main(String[] args) {
		new Positioner().start();
	}

	@Override
	protected void run() {
		robot.system.attach();
		for (int i = 50; i < 110; i++) {
			robot.S2.move(i);
			robot.S1.move(120 - (i * 2 / 3));
		}
		for (int i = 110; i > 50; i--) {
			robot.S2.move(i);
			robot.S1.move(120 - (i * 2 / 3));
		}

		robot.S2.move(90);
		robot.S1.move(90);
		robot.system.detach();

	}
}
