package controller.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.GuiController;
import model.elements.Robot;

public class NumPadKeyListener implements KeyListener {

	private Robot robot;
	private GuiController controller;

	public NumPadKeyListener(Robot robot, GuiController controller) {
		this.robot = robot;
		this.controller = controller;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int delta = 1;

		// grasper arms
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
			robot.C0.moveRelativ(-delta);
		} else if (e.getKeyCode() == KeyEvent.VK_DECIMAL) {
			robot.C0.moveRelativ(+delta);

			// base rotation
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			robot.S0.moveRelativ(-delta);
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
			robot.S0.moveRelativ(+delta);

			// R2 up/down
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			robot.S2.moveRelativ(-delta);
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			robot.S2.moveRelativ(+delta);

			// R1 forward/back
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
			robot.S1.moveRelativ(+delta);
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			robot.S1.moveRelativ(-delta);

			// grasper rotation
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
			robot.C1.moveRelativ(-delta);
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
			robot.C1.moveRelativ(+delta);

			// grasper up/down
		} else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			robot.C2.moveRelativ(-delta);
		} else if (e.getKeyCode() == KeyEvent.VK_ADD) {
			robot.C2.moveRelativ(+delta);

			// laser on/off
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
			robot.laser.toggle();

			// save
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			controller.save();

			// else - unknown
		} else {
			System.out.println(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
