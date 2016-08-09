package view;

import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;

import controller.GuiController;

public class Gui {

	private JFrame frame;
	private GuiController guiController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		guiController = new GuiController();
		frame = new JFrame();
		frame.setBounds(100, 100, 502, 276);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnAttach = new JButton("Attach");
		btnAttach.addActionListener(guiController.getAttachListener());
		btnAttach.addKeyListener(guiController.getKeyListener());

		JButton btnDetach = new JButton("Detach");
		btnDetach.addActionListener(guiController.getDetachListener());

		JLabel lblS = new JLabel("S0");

		JSlider slider = new JSlider(0, 180, 90);
		slider.addChangeListener(guiController.getSliderListener(guiController.robot.S0));

		JLabel lblS_1 = new JLabel("S1");

		JSlider slider_1 = new JSlider(0, 180, 90);
		slider_1.addChangeListener(guiController.getSliderListener(guiController.robot.S1));

		JLabel lblS_2 = new JLabel("S2");

		JSlider slider_2 = new JSlider(0, 180, 90);
		slider_2.addChangeListener(guiController.getSliderListener(guiController.robot.S2));

		JLabel lblC = new JLabel("C0");

		JSlider slider_3 = new JSlider(0, 180, 30);
		slider_3.addChangeListener(guiController.getSliderListener(guiController.robot.C0));

		JLabel lblC_1 = new JLabel("C1");

		JSlider slider_4 = new JSlider(0, 180, 0);
		slider_4.addChangeListener(guiController.getSliderListener(guiController.robot.C1));

		JLabel lblC_2 = new JLabel("C2");

		JSlider slider_5 = new JSlider(0, 180, 0);
		slider_5.addChangeListener(guiController.getSliderListener(guiController.robot.C2));

		JLabel lblSpeed = new JLabel("speed");
		slider_5.addChangeListener(guiController.getSliderSpeedListener());

		JSlider slider_6 = new JSlider(1, 5, 2);

		JButton btnSavePosition = new JButton("Save position");
		btnSavePosition.addActionListener(guiController.getSaveButtonListener());

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnAttach)
												.addGap(39)
												.addComponent(lblSpeed)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(slider_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
												.addComponent(btnSavePosition))
										.addComponent(btnDetach)
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblS)
														.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblS_1)
														.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblS_2)
														.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
												.addGap(61)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(slider_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblC_2)
														.addComponent(slider_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblC_1)
														.addComponent(slider_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblC))))
								.addContainerGap()));
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(btnAttach)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(btnDetach))
														.addComponent(slider_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblS)
														.addComponent(lblC)))
										.addComponent(lblSpeed)
										.addComponent(btnSavePosition))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblS_1)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblS_2)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(slider_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblC_1)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(slider_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblC_2)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(slider_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(18, Short.MAX_VALUE)));
		frame.getContentPane().setLayout(groupLayout);
	}
}
