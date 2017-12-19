
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
import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class TheWall {
	private static Canvas1	draw	= new Canvas1();	// main and only instance of class canvas 1
	private static Player	player1;
	private static Player	player2;
	private static JFrame	frame;						// main frame
	private static JPanel	inputs;						// sub panel for input instances

	/**
	 * @return the inputs
	 */
	public static JPanel getInputs() {
		return inputs;
	}

	/**
	 * @return the frame
	 */
	public static JFrame getFrame() {
		return frame;
	}

	public static Canvas1 getCanvas() {
		return draw;
	}

	public static Player getPlayer(int num) {
		if (num == 1) return player1;
		else return player2;
	}

	public static int getTurn() { // returns the actual turn
		if (player1.getTurn()) return 1;
		else return 2;
	}

	public static int getTurn(int x) { // returns the inverse turn
		if (player1.getTurn()) return 2;
		else return 1;
	}

	public static void main(String[] args) {
		player1 = new Player(1);
		player2 = new Player(2);
		frame = new JFrame("The Wall");
		try {
			frame.setIconImage(ImageIO.read(TheWall.class.getResource("wall64.png")));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel main = new JPanel(new BorderLayout());
		frame.setPreferredSize(new Dimension(960, 760));
		frame.setMinimumSize(new Dimension(1295, 760)); // minimum size so inputs are formatted correctly.
		// IF window is maximized before first shot, wall will grow or shrink accordingly. After the first shot it is
		// locked
		main.add(draw);
		UserInput input = new UserInput(1);
		UserInput input2 = new UserInput(2);
		inputs = new JPanel(new BorderLayout());
		inputs.add(input.getUI(), BorderLayout.WEST);
		inputs.add(input2.getUI(), BorderLayout.EAST);
		frame.getContentPane().add(main, BorderLayout.CENTER);
		frame.getContentPane().add(inputs, BorderLayout.SOUTH);// panels within panels for layout convenience
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
