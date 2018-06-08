package controller.listener;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.GuiController;
import model.ProgramsUtils;
import model.elements.Robot;

public class NumPadKeyListener implements KeyListener {

	private Robot robot;
	private GuiController controller;
	private Runnable callback;

	public NumPadKeyListener(Robot robot, GuiController controller, Runnable callback) {
		this.robot = robot;
		this.controller = controller;
		this.callback = callback;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int delta = 1;

		// grasper arms
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
			if (isCtrlPressed(e)) {
				robot.C0.moveFast(0);
			} else {
				robot.C0.moveRelative(-delta);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DECIMAL) {
			if (isCtrlPressed(e)) {
				robot.C0.moveFast(90);
			} else {
				robot.C0.moveRelative(+delta);
			}

			// base rotation
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			robot.S0.moveRelative(-delta);
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
			robot.S0.moveRelative(+delta);

			// R2 up/down
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			robot.S2.moveRelative(-delta);
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			robot.S2.moveRelative(+delta);

			// R1 forward/back
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
			robot.S1.moveRelative(+delta);
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			robot.S1.moveRelative(-delta);

			// grasper rotation
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
			if (isCtrlPressed(e)) {
				robot.C1.moveFast(0);
			} else {
				robot.C1.moveRelative(-delta);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
			if (isCtrlPressed(e)) {
				robot.C1.moveFast(90);
			} else {
				robot.C1.moveRelative(+delta);
			}

			// grasper up/down
		} else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			if (isCtrlPressed(e)) {
				robot.C2.moveFast(0);
			} else {
				robot.C2.moveRelative(-delta);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ADD) {
			if (isCtrlPressed(e)) {
				robot.C2.moveFast(90);
			} else {
				robot.C2.moveRelative(+delta);
			}

			// accessory on/off
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
			robot.accessory.toggle();

			// save
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (isCtrlPressed(e)) {
				controller.save();
			} else {
				returnToStartPosition();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			//do nothing

			// else - unknown
		} else {
			System.out.println(e);
		}

		callback.run();
	}

	private void returnToStartPosition() {
		ProgramsUtils.startPosition(robot);
		robot.C0.moveFast(100);
		robot.S1.moveFast(80);
		robot.S2.moveFast(90);
	}

	private boolean isCtrlPressed(KeyEvent e) {
		return e.getModifiers() == InputEvent.CTRL_MASK;
	}


	@Override
	public void keyReleased(KeyEvent e) {

	}

}
