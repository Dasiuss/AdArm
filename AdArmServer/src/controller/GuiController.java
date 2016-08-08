package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.listener.NumPadKeyListener;
import controller.listener.SliderListener;
import model.elements.Robot;
import model.elements.Servo;

public class GuiController {

	public Robot robot;

	public GuiController() {
		robot = new Robot();
	}

	public ChangeListener getSliderListener(Servo s) {
		return new SliderListener(s);
	}

	public ChangeListener getSliderSpeedListener() {
		return new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int value = source.getValue();
					robot.system.setStepDelay(value * 1000);
				}
			}
		};
	}

	public ActionListener getAttachListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				robot.system.attach();
			}
		};
	}

	public ActionListener getDetachListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				robot.system.detach();
			}
		};
	}

	public ActionListener getSaveButtonListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		};
	}

	public void save() {
		List<Integer> positions = robot.getPositions();
		try {
			Files.write(Paths.get("D:/positions.txt"), (positions.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public KeyListener getKeyListener() {
		return new NumPadKeyListener(robot, this);
	}
}
