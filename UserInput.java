
/*
 * Dylan Cole Morgen
 * dmorgen2
 * Lab TA: NOAH HELTERBRAND
 * Project 3
 * Wed 12:30 Lab
 * I did not collaborate with anyone on this assignment.
 * 
 * 
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserInput extends JComponent {
	private JButton		fire;
	private JTextField	angle;
	private JTextField	velocity;
	private JLabel		anglel;
	private JLabel		velocityl;
	private JPanel		ui;
	private JPanel		labels;
	private JPanel		inputs;
	private JPanel		button;
	private JPanel		container;
	private int			player;
	private Border		defaultBorder;		// holds borderstyle
	Boolean				visint		= true;
	Boolean				vinrange	= true;
	Boolean				aisint		= true;
	Boolean				ainrange	= true;

	public UserInput(int player) {
		this.player = player;
		ui = new JPanel(new BorderLayout());
		ui.setPreferredSize(new Dimension(640, 100));
		ui.setBackground(Color.BLUE);

		button = new JPanel(new BorderLayout());
		labels = new JPanel(new BorderLayout());
		inputs = new JPanel(new BorderLayout());
		container = new JPanel(new BorderLayout());

		labels.setMaximumSize(new Dimension(200, 30));
		inputs.setMaximumSize(new Dimension(200, 50));
		container.setMaximumSize(new Dimension(200, 100));

		angle = new JTextField("45", 10);
		velocity = new JTextField("25", 10);
		defaultBorder = angle.getBorder();

		fire = new JButton("FIRE");
		fire.setBackground(new Color(188, 15, 15));
		fire.setForeground(Color.WHITE);

		angle.getDocument().addDocumentListener(new DocumentListener() { // see if input value is changed

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				cannonRotate();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				cannonRotate();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				cannonRotate();
			}

			public void cannonRotate() {
				ainrange = false;
				try {

					if (Integer.parseInt(angle.getText().trim()) > 0 && Integer.parseInt(angle.getText().trim()) < 90)
					    ainrange = true;
					aisint = true;
				}
				catch (NumberFormatException e) {
					aisint = false;
				}

				if (aisint && ainrange) {
					angle.setBorder(defaultBorder);
					if (player == 1) {
						TheWall.getCanvas().setAngle1(Integer.parseInt(angle.getText().trim()));
					}
					else {
						TheWall.getCanvas().setAngle2(Integer.parseInt(angle.getText().trim()));
					}
				}
				else {
					angle.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}

		});
		velocity.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				vector();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				vector();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				vector();
			}

			public void vector() {
				vinrange = false;
				try {

					if (Integer.parseInt(velocity.getText().trim()) > 0 && Integer.parseInt(velocity.getText()
					        .trim()) <= 200) vinrange = true;
					visint = true;
				}
				catch (NumberFormatException e) {
					visint = false;
				}

				if (visint && vinrange) {
					velocity.setBorder(defaultBorder);
					if (player == 1) {

						TheWall.getCanvas().setVelocity1(Integer.parseInt(velocity.getText().trim()));
					}
					else {
						TheWall.getCanvas().setVelocity2(Integer.parseInt(velocity.getText().trim()));
					}
				}
				else {
					velocity.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}

		});

		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				update();
			}
		});
		timer.start();
		fire.setFont(new Font("Impact", Font.PLAIN, 40));
		fire.setFocusPainted(false);
		fire.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) { // if button pressed

				if (angle.getText().equals("") == false && velocity.getText().equals("") == false) {

					if (TheWall.getPlayer(1).getTurn()) {
						TheWall.getCanvas().setAngle1(Integer.parseInt(angle.getText()));
						TheWall.getCanvas().setVelocity1(Integer.parseInt(velocity.getText()));
						TheWall.getCanvas().start1();
						TheWall.getPlayer(1).setTurn(false);
						TheWall.getPlayer(2).setTurn(true);
					}
					else if (TheWall.getPlayer(2).getTurn()) {
						TheWall.getCanvas().setAngle2(Integer.parseInt(angle.getText()));
						TheWall.getCanvas().setVelocity2(Integer.parseInt(velocity.getText()));
						TheWall.getCanvas().start2();
						TheWall.getPlayer(2).setTurn(false);
						TheWall.getPlayer(1).setTurn(true);
					}
				}
			}

		});

		anglel = new JLabel("Angle:");
		anglel.setFont(new Font("Arial", Font.BOLD, 18));
		velocityl = new JLabel("Velocity:");
		velocityl.setFont(new Font("Arial", Font.BOLD, 18));
		angle.setPreferredSize(new Dimension(100, 40));
		angle.setFont(new Font("Arial", Font.BOLD, 18));
		velocity.setPreferredSize(new Dimension(100, 40));
		velocity.setFont(new Font("Arial", Font.BOLD, 18));
		anglel.setPreferredSize(new Dimension(175, 40));
		velocityl.setPreferredSize(new Dimension(140, 40));

		labels.add(anglel, BorderLayout.WEST);
		labels.add(velocityl, BorderLayout.CENTER);

		button.add(fire);
		button.setBorder(new EmptyBorder(10, 10, 10, 10));

		inputs.add(angle, BorderLayout.WEST);
		inputs.add(velocity, BorderLayout.EAST);

		container.add(labels, BorderLayout.CENTER);
		container.add(inputs, BorderLayout.SOUTH);
		container.setBorder(new EmptyBorder(10, 10, 10, 10));
		ui.add(container, BorderLayout.WEST);
		ui.add(button);

	}

	public void update() { // grey out buttons accordingly
		if (TheWall.getPlayer(player).getTurn() == false) {
			fire.setBackground(new Color(79, 11, 11));
			fire.setEnabled(false);
			angle.setEnabled(false);
			velocity.setEnabled(false);
		}
		else if (TheWall.getCanvas().getDonedrawing()) {
			if (visint && vinrange && aisint && ainrange) {
				fire.setEnabled(true);
				TheWall.getFrame().getRootPane().setDefaultButton(fire);// makes enter key toggle this button

			}
			else fire.setEnabled(false);
			fire.setBackground(new Color(188, 15, 15));
			angle.setEnabled(true);
			velocity.setEnabled(true);
		}

	}

	public JTextField getVelocity() {
		return velocity;
	}

	public JTextField getAngle() {
		return angle;
	}

	public JButton getButton() {
		return fire;
	}

	public JPanel getUI() {
		return ui;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
