package controller.programs;

import controller.Program;

public class Positioner extends Program {

	public static void main(String[] args) {
		new Positioner().start();
	}

	@Override
	protected void run() {

		robot.S0.move(180);

	}
}
