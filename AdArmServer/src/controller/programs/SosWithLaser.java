package controller.programs;

import controller.Program;

public class SosWithLaser extends Program {

	public static void main(String[] args) {
		new SosWithLaser().start();
	}

	@Override
	protected void run() {
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
