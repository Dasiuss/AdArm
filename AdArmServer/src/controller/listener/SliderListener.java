package controller.listener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.elements.Servo;

public class SliderListener implements ChangeListener {

	private Servo s;

	public SliderListener(Servo s) {
		this.s = s;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			int position = source.getValue();
			s.moveFast(position);
		}
	}
}
