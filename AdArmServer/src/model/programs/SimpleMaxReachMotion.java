package model.programs;

import static model.elements.Robot.*;

import controller.Runner;
import model.Program;

public class SimpleMaxReachMotion extends Program {

	public static void main(String[] args) {
		new Runner(new SimpleMaxReachMotion()).start();
	}

	@Override
	protected void init() {
		enqueue(system.setStepDelay(5));

		enqueue(C0.move(180));
		enqueue(C0.move(20));
		enqueue(C1.move(180));
		enqueue(C1.move(0));
		enqueue(C2.move(180));
		enqueue(C2.move(0));

		for (int i = 0; i < 256; i += 10) {
			enqueue(laser.on(i));
		}
		for (int i = 255; i >= 0; i -= 10) {
			enqueue(laser.on(i));
		}
		enqueue(laser.off());
	}
}
