package controller.programs;

import controller.Program;

public class SosWithLaser extends Program {

	public static void main(String[] args) {
		new SosWithLaser().run();
	}

	@Override
	public void start() {
		robot.accessory.on();
		robot.system.wait(100);
		robot.accessory.off();
		robot.system.wait(300);
		robot.accessory.on();
		robot.system.wait(100);
		robot.accessory.off();
		robot.system.wait(300);
		robot.accessory.on();
		robot.system.wait(100);
		robot.accessory.off();
		robot.system.wait(1000);

		robot.accessory.on();
		robot.system.wait(300);
		robot.accessory.off();
		robot.system.wait(300);
		robot.accessory.on();
		robot.system.wait(300);
		robot.accessory.off();
		robot.system.wait(300);
		robot.accessory.on();
		robot.system.wait(300);
		robot.accessory.off();
		robot.system.wait(1000);

		robot.accessory.on();
		robot.system.wait(100);
		robot.accessory.off();
		robot.system.wait(300);
		robot.accessory.on();
		robot.system.wait(100);
		robot.accessory.off();
		robot.system.wait(300);
		robot.accessory.on();
		robot.system.wait(100);
		robot.accessory.off();
		robot.system.wait(1000);

	}
}
