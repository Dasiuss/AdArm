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
		enqueue(C0.move(40));
		enqueue(C1.move(0));
		enqueue(C2.move(90));
		// enqueue(laser.setIntensity(20));

	}
}
