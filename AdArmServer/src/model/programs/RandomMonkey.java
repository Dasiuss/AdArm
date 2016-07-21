package model.programs;

import static model.elements.Robot.*;

import java.util.Random;

import controller.Runner;
import model.Program;
import model.elements.Servo;

public class RandomMonkey extends Program {

	private static final int MOVES_COUNT = 80;

	public static void main(String[] args) {
		new Runner(new RandomMonkey()).start();
	}

	@Override
	protected void init() {
		Servo[] allServos = new Servo[] { C0, C1, C2 };

		Random rnd = new Random();

		for (int i = 0; i < MOVES_COUNT; i++) {
			enqueue(allServos[rnd.nextInt(allServos.length)].move(rnd.nextInt(180)));
		}
		startPosition();

	}
}
