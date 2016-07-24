package model.programs;

import static model.elements.Robot.*;

import controller.Runner;
import model.Program;

public class Positioner extends Program {

	public static void main(String[] args) {
		new Runner(new Positioner()).start();
	}

	@Override
	protected void init() {
		// startPositionFast();

		enqueue(S2.move(45));
		enqueue(S1.move(120));
		enqueue(S2.move(5));
		enqueue(S1.move(180));

		enqueue(S1.move(120));
		enqueue(S2.move(40));
		enqueue(S1.move(30));
		enqueue(S2.move(90));

	}
}
