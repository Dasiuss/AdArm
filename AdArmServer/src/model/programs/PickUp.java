package model.programs;

import static model.elements.Robot.*;

import controller.Runner;
import model.Program;

public class PickUp extends Program {

	public static void main(String[] args) {
		new Runner(new PickUp()).start();
	}

	@Override
	protected void init() {
		enqueue(system.setStepDelay(10));

		enqueue(C0.move(180));
		enqueue(C2.move(90));
		enqueue(C1.move(45));

		enqueue(C0.move(0));
		enqueue(C1.moveFast(0));
		enqueue(C2.move(0));

		enqueue(system.wait(1000));
		enqueue(C0.move(100));
		enqueue(system.wait(500));
		startPosition();
	}
}
