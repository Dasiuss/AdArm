package kgbotController;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8057597721376956126L;
	private JPanel contentPane;
	private Communicator polaczenie = new Communicator();
	private JTextField moveVal;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 428);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("100px:grow"),
				ColumnSpec.decode("100px"), ColumnSpec.decode("5px"), ColumnSpec.decode("100px:grow"),
				ColumnSpec.decode("5px"), ColumnSpec.decode("100px"), ColumnSpec.decode("5px"),
				ColumnSpec.decode("100px"), ColumnSpec.decode("5px"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC, RowSpec.decode("24px"),
				FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("24px"), FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("25px"), RowSpec.decode("5px"), RowSpec.decode("25px"), RowSpec.decode("5px"),
				RowSpec.decode("25px"), RowSpec.decode("5px"), RowSpec.decode("25px"), RowSpec.decode("5px"),
				RowSpec.decode("25px"), RowSpec.decode("5px"), RowSpec.decode("25px"), RowSpec.decode("5px:grow"), }));

		JLabel lblSterowanieSilnikamiKgbot = new JLabel("Sterowanie silnikami KGBot");
		lblSterowanieSilnikamiKgbot.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(lblSterowanieSilnikamiKgbot, "2, 2, 5, 2");

		JLabel lblSilnikS_2 = new JLabel("Silnik S1");
		contentPane.add(lblSilnikS_2, "1, 5, 1, 2, center, default");

		final JSlider slider_3 = new JSlider();
		slider_3.setBackground(Color.WHITE);
		contentPane.add(slider_3, "1, 7, 2, 2, center, top");

		JButton btnMove = new JButton("Move");
		btnMove.setToolTipText("Porusz o wektor");
		btnMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				polaczenie.move(0, slider_3.getValue(), Integer.parseInt(moveVal.getText()));
			}
		});

		moveVal = new JTextField();
		moveVal.setToolTipText("Wpisz warto\u015B\u0107 przesuni\u0119cia.");
		contentPane.add(moveVal, "4, 7, fill, default");
		moveVal.setColumns(10);
		contentPane.add(btnMove, "6, 7");

		JButton btnMoveto_1 = new JButton("MoveTO");
		btnMoveto_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.moveTo(0, slider_3.getValue(), Integer.parseInt(moveVal.getText()));
			}
		});
		btnMoveto_1.setToolTipText("Porusz do konkretnego miejsca");
		contentPane.add(btnMoveto_1, "8, 7");

		JLabel lblSilnikS_1 = new JLabel("Silnik S2");
		contentPane.add(lblSilnikS_1, "1, 9, 1, 2, center, default");

		final JSlider slider_1 = new JSlider();
		slider_1.setBackground(Color.WHITE);
		contentPane.add(slider_1, "1, 11, 2, 1, center, top");

		textField_1 = new JTextField();
		textField_1.setToolTipText("Wpisz warto\u015B\u0107 przesuni\u0119cia.");
		textField_1.setColumns(10);
		contentPane.add(textField_1, "4, 11, fill, default");

		JButton button = new JButton("Move");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				polaczenie.move(1, slider_1.getValue(), Integer.parseInt(textField_1.getText()));
			}
		});
		button.setToolTipText("Porusz o wektor");
		contentPane.add(button, "6, 11");

		JButton button_2 = new JButton("MoveTO");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.moveTo(1, slider_1.getValue(), Integer.parseInt(textField_1.getText()));
			}
		});
		button_2.setToolTipText("Porusz do konkretnego miejsca");
		contentPane.add(button_2, "8, 11");

		JLabel lblSilnikS = new JLabel("Silnik S3");
		contentPane.add(lblSilnikS, "1, 13, 1, 2, center, default");

		final JSlider slider_2 = new JSlider();
		slider_2.setForeground(Color.BLACK);
		slider_2.setBackground(Color.WHITE);
		contentPane.add(slider_2, "1, 15, 2, 1, center, top");

		JButton btnUndo = new JButton("<-- UNDO");
		btnUndo.setToolTipText("Cofnij ruch");
		btnUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				polaczenie.undo();
			}
		});

		textField_2 = new JTextField();
		textField_2.setToolTipText("Wpisz warto\u015B\u0107 przesuni\u0119cia.");
		textField_2.setColumns(10);
		contentPane.add(textField_2, "4, 15, fill, default");

		JButton button_1 = new JButton("Move");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.move(2, slider_2.getValue(), Integer.parseInt(textField_2.getText()));
			}
		});
		button_1.setToolTipText("Porusz o wektor");
		contentPane.add(button_1, "6, 15");

		JButton button_3 = new JButton("MoveTO");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.moveTo(2, slider_2.getValue(), Integer.parseInt(textField_2.getText()));
			}
		});
		button_3.setToolTipText("Porusz do konkretnego miejsca");
		contentPane.add(button_3, "8, 15");

		textField_3 = new JTextField();
		textField_3.setToolTipText("Wpisz warto\u015B\u0107 przesuni\u0119cia.");
		textField_3.setColumns(10);
		contentPane.add(textField_3, "1, 18, fill, default");

		JButton btnMoveup = new JButton("MoveVertical");
		btnMoveup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.moveVertical(textField_3.getText());
			}
		});
		btnMoveup.setToolTipText("Porusz konc\u00F3wk\u0105 pionowo w g\u00F3r\u0119.");
		contentPane.add(btnMoveup, "2, 18");
		contentPane.add(btnUndo, "6, 18");

		JButton btnRedo = new JButton("REDO -->");
		btnRedo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.redo();
			}
		});
		btnRedo.setToolTipText("Pon\u00F3w cofni\u0119ty ruch");
		contentPane.add(btnRedo, "8, 18");

		textField_4 = new JTextField();
		textField_4.setToolTipText("Wpisz warto\u015B\u0107 przesuni\u0119cia.");
		textField_4.setColumns(10);
		contentPane.add(textField_4, "1, 20, fill, default");

		JButton btnMovehorizontal = new JButton("MoveSides");
		btnMovehorizontal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.moveSides(Integer.parseInt(textField_4.getText()));
			}
		});
		btnMovehorizontal.setToolTipText("Porusz konc\u00F3wk\u0105 pionowo w g\u00F3r\u0119.");
		contentPane.add(btnMovehorizontal, "2, 20");

		JLabel lblEfector = new JLabel("Efector");
		lblEfector.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblEfector, "6, 20, 3, 1, center, default");

		JButton btnKalibracja = new JButton("Kalibracja");
		btnKalibracja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				polaczenie.kalibracja();
			}
		});

		JButton btnMoveto = new JButton("Von");
		btnMoveto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.Von(textField_5.getText());
			}
		});

		textField_5 = new JTextField();
		textField_5.setToolTipText("Wpisz warto\u015B\u0107 przesuni\u0119cia.");
		textField_5.setColumns(10);
		contentPane.add(textField_5, "4, 22, fill, default");
		contentPane.add(btnMoveto, "6, 22, center, default");

		JButton btnVoff = new JButton("Voff");
		btnVoff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.Voff();
			}
		});
		contentPane.add(btnVoff, "8, 22, center, default");
		contentPane.add(btnKalibracja, "1, 24");

		JButton btnWtyCzka = new JButton("Plug ON");
		btnWtyCzka.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.plugOn();

			}
		});
		contentPane.add(btnWtyCzka, "6, 24, center, default");

		JButton btnPlugOff = new JButton("Plug OFF");
		btnPlugOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polaczenie.plugOff();
			}
		});
		contentPane.add(btnPlugOff, "8, 24, center, default");
	}

}
