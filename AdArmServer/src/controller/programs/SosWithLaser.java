package controller.programs;

import model.elements.Robot;

public class SosWithLaser {

	private Robot robot = new Robot();

	public static void main(String[] args) {
		new SosWithLaser();
	}

	public SosWithLaser() {
		robot.laser.on();
		robot.system.wait(100);
		robot.laser.off();
		robot.system.wait(300);
		robot.laser.on();
		robot.system.wait(100);
		robot.laser.off();
		robot.system.wait(300);
		robot.laser.on();
		robot.system.wait(100);
		robot.laser.off();
		robot.system.wait(1000);

		robot.laser.on();
		robot.system.wait(300);
		robot.laser.off();
		robot.system.wait(300);
		robot.laser.on();
		robot.system.wait(300);
		robot.laser.off();
		robot.system.wait(300);
		robot.laser.on();
		robot.system.wait(300);
		robot.laser.off();
		robot.system.wait(1000);

		robot.laser.on();
		robot.system.wait(100);
		robot.laser.off();
		robot.system.wait(300);
		robot.laser.on();
		robot.system.wait(100);
		robot.laser.off();
		robot.system.wait(300);
		robot.laser.on();
		robot.system.wait(100);
		robot.laser.off();
		robot.system.wait(1000);

	}
}
