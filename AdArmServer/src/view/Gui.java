package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.GuiController;
import controller.WebCamView;

public class Gui {

	private GuiController guiController;
	private final JLabel sValuesLabel;
	private final JLabel cValuesLabel;
	private final JLabel heightLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(Gui::new);
		EventQueue.invokeLater(() -> new WebCamView().start());
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		guiController = new GuiController();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JButton btnAttach = new JButton("Attach");

		ActionListener attachListener = guiController.getAttachListener();
		btnAttach.addActionListener((e)->{
			attachListener.actionPerformed(e);
			updateLabels();
		});
		btnAttach.addKeyListener(guiController.getKeyListener(this::updateLabels));

		JButton btnDetach = new JButton("Detach");
		btnDetach.addActionListener(guiController.getDetachListener());

		JButton btnSavePosition = new JButton("Save pos");
		btnSavePosition.addActionListener(guiController.getSaveButtonListener());
		btnSavePosition.addKeyListener(guiController.getKeyListener(this::updateLabels));

		GridLayout layout = new GridLayout(8, 1);
		frame.setLayout(layout);

		sValuesLabel = createLabel(" | | ");
		cValuesLabel = createLabel(" | | ");
		heightLabel = createLabel(" | | ");

		frame.add(btnAttach);
		frame.add(btnDetach);
		frame.add(btnSavePosition);
		frame.add(createLabel("S0 | S1 | S2"));
		frame.add(sValuesLabel);
		frame.add(createLabel("C0 | C1 | C2"));
		frame.add(cValuesLabel);
		frame.add(heightLabel);

		frame.pack();
		frame.setBounds(900, 300, 100, 330);
		frame.setVisible(true);
	}

	private void updateLabels() {
		setSValues(guiController.getSPositions());
		setCValues(guiController.getCPositions());
		heightLabel.setText(guiController.robot.getEffectorLevel()+"cm");
	}

	private JLabel createLabel(String text) {
		JLabel lblS = new JLabel(text);
		lblS.setHorizontalAlignment(SwingConstants.CENTER);
		return lblS;
	}

	 private void setSValues(int[] values) {
		sValuesLabel.setText(values[0] + " | " + values[1] + " | " + values[2]);
	}

	 private void setCValues(int[] values) {
		cValuesLabel.setText(values[0] + " | " + values[1] + " | " + values[2]);
	}
}
