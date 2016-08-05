package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.listener.SliderListener;
import model.elements.Robot;
import model.elements.Servo;

public class Controller {

	public Robot robot;

	public Controller() {
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
}
