package controller.programs;

import java.util.Random;

import controller.Program;
import model.ProgramsUtils;
import model.elements.Robot;
import model.elements.Servo;

public class RandomMonkey extends Program {

	private Robot robot = new Robot();
	private static final int MOVES_COUNT = 80;

	public static void main(String[] args) {
		new RandomMonkey().start();
	}

	@Override
	protected void run() {
		Servo[] allServos = new Servo[] { robot.C0, robot.C1, robot.C2 };

		Random rnd = new Random();

		for (int i = 0; i < MOVES_COUNT; i++) {
			allServos[rnd.nextInt(allServos.length)].move(rnd.nextInt(180));
		}
		ProgramsUtils.startPosition(robot);

	}
}
