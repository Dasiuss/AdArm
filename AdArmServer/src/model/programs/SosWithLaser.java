package model.programs;

import static model.elements.Robot.*;

import controller.Runner;
import model.Program;

public class SosWithLaser extends Program {

	public static void main(String[] args) {
		new Runner(new SosWithLaser()).start();
	}

	@Override
	protected void init() {
		enqueue(laser.on());
		enqueue(system.wait(100));
		enqueue(laser.off());
		enqueue(system.wait(300));
		enqueue(laser.on());
		enqueue(system.wait(100));
		enqueue(laser.off());
		enqueue(system.wait(300));
		enqueue(laser.on());
		enqueue(system.wait(100));
		enqueue(laser.off());
		enqueue(system.wait(1000));

		enqueue(laser.on());
		enqueue(system.wait(300));
		enqueue(laser.off());
		enqueue(system.wait(300));
		enqueue(laser.on());
		enqueue(system.wait(300));
		enqueue(laser.off());
		enqueue(system.wait(300));
		enqueue(laser.on());
		enqueue(system.wait(300));
		enqueue(laser.off());
		enqueue(system.wait(1000));

		enqueue(laser.on());
		enqueue(system.wait(100));
		enqueue(laser.off());
		enqueue(system.wait(300));
		enqueue(laser.on());
		enqueue(system.wait(100));
		enqueue(laser.off());
		enqueue(system.wait(300));
		enqueue(laser.on());
		enqueue(system.wait(100));
		enqueue(laser.off());
		enqueue(system.wait(1000));

	}
}
