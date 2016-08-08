package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
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
				List<Integer> positions = robot.getPositions();
				try {
					FileWriter fw = new FileWriter("D:/positions.txt");
					fw.append(positions.toString());
					fw.append("/n");
					fw.flush();
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		};
	}

	public KeyListener getKeyListener() {
		return new NumPadKeyListener(robot);
	}
}
