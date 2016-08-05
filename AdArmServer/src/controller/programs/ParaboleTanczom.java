package controller.programs;

import controller.Program;
import model.ProgramsUtils;
import model.elements.Robot;

public class ParaboleTanczom extends Program {

	private Robot robot;

	public static void main(String[] args) {
		new ParaboleTanczom().start();
	}

	@Override
	protected void run() {
		robot = new Robot();
		ProgramsUtils.startPositionFast(robot);

		robot.C0.move(80);
		robot.C2.move(0);
		robot.system.wait(1000);

		part1();

		part2();

	}

	private void part2() {
		zwrotka();

		robot.system.setStepDelayMicros(6000);
		robot.C0.moveFast(60);
		robot.C1.moveFast(90);
		robot.C2.moveFast(45);

		robot.C1.move(10);
		robot.system.wait(100);
		robot.C1.move(90);
		robot.system.wait(100);
		robot.C1.move(10);
		robot.system.wait(100);
		robot.C1.move(90);
		robot.system.wait(100);
		robot.C1.move(160);
		robot.system.wait(100);
		robot.C1.move(90);
		robot.system.wait(100);
		robot.C1.move(160);
		robot.system.wait(100);
		robot.C1.move(90);
		robot.system.wait(100);
	}

	private void part1() {
		zwrotka();

		robot.system.setStepDelayMicros(5500);
		robot.C0.moveFast(180);
		robot.C0.move(20);
		robot.C0.move(180);
		robot.C0.move(20);
		robot.C0.move(180);
		robot.C2.moveFast(90);
		robot.C0.move(20);
		robot.C0.move(180);
		robot.C0.move(20);
		robot.C0.move(180);
	}

	private void zwrotka() {
		robot.C0.moveFast(80);
		robot.C2.moveFast(0);

		robot.system.setStepDelayMicros(5000);
		robot.C2.move(90);
		robot.system.wait(100);
		robot.C2.move(0);
		robot.system.wait(100);
		robot.C2.move(90);
		robot.system.wait(100);
		robot.C2.move(0);
		robot.system.wait(100);
		robot.C2.move(90);
		robot.system.wait(100);
		robot.C2.move(0);
		robot.system.wait(100);
		robot.C2.move(90);
		robot.system.wait(100);
		robot.C2.move(0);
		robot.system.wait(100);
	}
}
