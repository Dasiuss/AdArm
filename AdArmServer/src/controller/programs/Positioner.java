package controller.programs;

import controller.Program;

public class Positioner extends Program {

	public static void main(String[] args) {
		new Positioner().run();
	}

	@Override
	public void start() {
		robot.C0.move(90);
	}
}
