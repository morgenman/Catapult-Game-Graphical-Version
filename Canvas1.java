
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

public class Canvas1 extends JComponent {
	private int				x, y;					// origin for all things drawn. Located at bottom center
	private wall			wall;
	private int				bx, by;					// brick height and width (adjustable I think (untested))
	private int				x1, x2, y1, y2;			// cannon location
	private int				x3, x4, y3, y4;			// current location of cannonball at any given moment
	private int				ballsize	= 15;		// size of cannonball. Lots of things use this
	private float			m, n;					// simple toggle for player one vs player 2
	private double			angle1;					// self explanatory vv
	private double			angle2;
	private double			velocity1;
	private double			velocity2;
	private BufferedImage	img;					// used for cannonball path
	private Dimension		walls;					// dimension of wall, used in the autowall function
	private Boolean			firstrun	= true;		// first run bool
	private Boolean			donedrawing;			// toggled when done drawing path of cannonball
	private Polygon			cannon1;
	private Polygon			cannon2;
	private Polygon			arrow1;
	private Polygon			arrow2;
	private Image			bg;						// used for background
	private String			s1			= "P1: 0";	// player 1 score
	private String			s2			= "P2: 0";	// ibid
	private Timer			timer;					// main timer
	private BufferedImage	i3;						// game over canvas image
	private Image			gameover;				// game over background
	private int				speed		= 9;

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Canvas1() {
		init();// init variables
		setDoubleBuffered(true);
		try { // add images
			bg = ImageIO.read(TheWall.class.getResource("bg.png"));
		}

		catch (IOException e1) {
			System.out.println("Error, images not in root folder");
		}
		try {
			gameover = ImageIO.read(TheWall.class.getResource("gameover.png"));
		}
		catch (IOException e1) {
			System.out.println("Error, images not in root folder");
		}
		donedrawing = true; // allows the buttons to be disabled on first draw

		timer = new Timer(speed, new ActionListener() { // very frequent but difficult if not set so frequent
			@Override
			public void actionPerformed(ActionEvent e) {
				if (firstrun) autowall(); // generate wall on resize
				cannons(); // adjust cannons based on angle
				arrows();

				center();
				repaint();
				endgame();
			}
		});
		timer.start();
	}

	public void endgame() {
		if (TheWall.getPlayer(1).getDestroyed() || TheWall.getPlayer(2).getDestroyed() || wall.getRows() == 0) {
			TheWall.getPlayer(1).setTurn(false); // disable buttons
			TheWall.getPlayer(2).setTurn(false); //
			timer.stop(); // stop main loop
			TheWall.getFrame().setResizable(false); // lock size
			i3 = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g3 = i3.createGraphics(); // to draw game over to
			g3.setColor(Color.black);
			g3.fillRect(0, 0, 1920, 1080);
			g3.drawImage(gameover, (x - gameover.getWidth(null) / 4), (y - gameover.getHeight(null) / 2) / 2, gameover
			        .getWidth(null) / 2, gameover.getHeight(null) / 2, null); // center
			g3.setColor(new Color(50, 50, 50));
			g3.setFont(new Font("Impact", Font.PLAIN, 150));
			FontMetrics fontMetrics = g3.getFontMetrics();

			g3.drawString(s1, 57, y - 20);// p1 score
			g3.setColor(new Color(224, 224, 224));
			g3.drawString(s1, 55, y - 18); // p2 score
			g3.setColor(new Color(50, 50, 50));
			g3.drawString(s2, getWidth() - 57 - fontMetrics.stringWidth(s2), y - 20);
			g3.setColor(new Color(224, 224, 224));
			g3.drawString(s2, getWidth() - 54 - fontMetrics.stringWidth(s2), y - 18);
			g3.setFont(new Font("Impact", Font.PLAIN, 50));
			fontMetrics = g3.getFontMetrics();
			if (TheWall.getPlayer(1).getScore() > TheWall.getPlayer(2).getScore()) { // results
				g3.drawString("Player 1 Won!", getWidth() / 2 - fontMetrics.stringWidth("Player 1 Won!") / 2, y - 20);
			}
			else if (TheWall.getPlayer(2).getScore() > TheWall.getPlayer(1).getScore()) {
				g3.drawString("Player 2 Won!", getWidth() / 2 - fontMetrics.stringWidth("Player 2 Won!") / 2, y - 20);

			}
			else {
				g3.drawString("It was a tie!", (x) - fontMetrics.stringWidth("It was a tie!") / 2, y - 20);

			}

			repaint();

		}

	}

	public void init() {
		img = new BufferedImage(1280, 640, BufferedImage.TYPE_INT_ARGB);
		bx = 40;
		by = 30;
		walls = new Dimension(5, 21);
		wall = new wall(walls.width, walls.height);
		angle1 = 45;
		velocity1 = 25;
		angle2 = 45;
		velocity2 = 25;
		m = 0;
		n = 0;

	}

	public void autowall() {
		Dimension size = getSize();
		walls.width = 5;
		if (walls.height != (int) Math.ceil((double) size.height / by) && size.height != 0) {
			walls.height = (int) Math.ceil((double) size.height / by);// round up to make sure wall is at least as tall
			                                                          // as the window
			wall = new wall(walls.width, walls.height - 1);

		}
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	public void start1() { // start ball one
		m = 1;

		firstrun = false;
		donedrawing = false;
		resetTrail();
	}

	public void start2() { // start ball two
		n = 1;
		firstrun = false;
		donedrawing = false;
		resetTrail();
	}

	/**
	 * @return the angle1
	 */
	public double getAngle1() {
		return angle1;
	}

	/**
	 * @param angle1
	 *            the angle1 to set
	 */
	public void setAngle1(double angle1) {
		this.angle1 = angle1;
	}

	/**
	 * @return the angle2
	 */
	public double getAngle2() {
		return angle2;
	}

	/**
	 * @param angle2
	 *            the angle2 to set
	 */
	public void setAngle2(double angle2) {
		this.angle2 = angle2;
	}

	/**
	 * @return the velocity1
	 */
	public double getVelocity1() {
		return velocity1;
	}

	/**
	 * @param velocity1
	 *            the velocity1 to set
	 */
	public void setVelocity1(double velocity1) {
		this.velocity1 = velocity1;
	}

	/**
	 * @return the velocity2
	 */
	public double getVelocity2() {
		return velocity2;
	}

	/**
	 * @param velocity2
	 *            the velocity2 to set
	 */
	public void setVelocity2(double velocity2) {
		this.velocity2 = velocity2;
	}

	private void center() { // center everything
		Dimension size = getSize();
		x = (int) (size.width / 2);
		y = (int) (size.height);

		x1 = x - 600;
		x2 = x + 600;
		y1 = y - 35;
		y2 = y - 35;
		if (m == 0 && n == 0) {
			resetBalls();
		}
		else {
			position1();
			position2();
		}
	}

	private void drawWall(Graphics g) {
		g.setColor(Color.WHITE);
		wall.rowCheck(); // see if the row is empty
		for (int i = 0; i < wall.getRows(); i++) { // iterate through 2d array that is wall
			for (int j = 0; j < wall.getColumns(); j++) {
				wall.getBrick(j, i).setPos(x + j * (bx + 1) - ((bx + 1) * wall.getColumns()) / 2, y - ((i + 1) * (by
				        + 1)));
				if (wall.getBrick(j, i).getAlmost() > 0) {
					g.setColor(new Color((float) .619, (float) .2784, (float) .2784, (float) .7));

				}
				else if (wall.getBrick(j, i).getIsbomb() == true) {
					g.setColor(Color.RED);
				}
				else g.setColor(new Color(158, 36, 36));
				if (wall.getBrick(j, i).getDestroyed() == false) g.fillRect(wall.getBrick(j, i).getPos().width, wall
				        .getBrick(j, i).getPos().height, bx, by);
			}
		}
	}

	private void resetTrail() {
		img = new BufferedImage(1280, 640, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * @return the wall
	 */
	public wall getWall() {
		return wall;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = img.createGraphics();
		Dimension size = getSize();
		g.setColor(new Color(73, 73, 73));
		g.fillRect(0, 0, size.width, size.height);
		g.drawImage(bg, x - bg.getWidth(null) / 2, y - bg.getHeight(null), null);
		if (donedrawing == false) {
			g.setColor(new Color(1, 1, 1, (float) .5));

		}
		else if (TheWall.getPlayer(1).getTurn()) {
			g.setColor(Color.white);
			g.fillPolygon(arrow1);
			g.setColor(new Color(1, 1, 1, (float) .5));
		}
		else {
			g.setColor(new Color(1, 1, 1, (float) .5));
			g.setColor(Color.white);

			g.fillPolygon(arrow2);
		}

		drawWall(g);
		g.setColor(Color.black);
		if (img != null) { // trail, centered
			g.drawImage(img, x - 1280 / 2, y - 640, null);
		}
		g.fillOval(x3 - ballsize / 2, y3 - ballsize / 2, ballsize, ballsize);
		g.fillOval(x4 - ballsize / 2, y4 - ballsize / 2, ballsize, ballsize);
		g2.setColor(Color.darkGray.brighter());
		g2.fillOval(x3 - (x - 1280 / 2), y3 - (y - 640), 3, 3);
		g2.fillOval(x4 - (x - 1280 / 2), y4 - (y - 640), 3, 3);
		g.setColor(Color.black);
		g.fillPolygon(cannon1);

		g.fillPolygon(cannon2);

		g.setFont(new Font("Impact", Font.PLAIN, 80)); // scores
		g.setColor(new Color(50, 50, 50));
		g.drawString(s1, 57, 82);
		g.setColor(Color.white);
		g.drawString(s1, 55, 80);
		g.setColor(new Color(50, 50, 50));
		FontMetrics fontMetrics = g.getFontMetrics();
		g.drawString(s2, getWidth() - 57 - fontMetrics.stringWidth(s2), 82);
		g.setColor(Color.white);
		g.drawString(s2, getWidth() - 54 - fontMetrics.stringWidth(s2), 80);
		if (i3 != null) {
			g.drawImage(i3, 0, 0, null);
		}
	}

	public void score() { // score update
		s1 = "P1: " + Integer.toString(TheWall.getPlayer(1).getScore());
		s2 = "P2: " + Integer.toString(TheWall.getPlayer(2).getScore());
	}

	private void position1() { // ball one
		if (m > 0) {

			m += 8;
			y3 = y1 - (int) (40 * ((m * .025) * Math.tan(Math.toRadians(angle1)) - 98.1 * (m * .025) * (m * .025) / (2
			        * Math.pow(velocity1 * 1.5 * Math.cos(Math.toRadians(angle1)), 2))));
			x3 = x1 + (int) m;
			hitwall(x3, y3);
			if (y3 >= y - ballsize / 2) {
				TheWall.getPlayer(TheWall.getTurn(1)).addScore(-10);
				resetBalls();
				wall.update();
			}
			else if ((y3 + (ballsize / 2)) < (y - walls.height * by)) {
				TheWall.getPlayer(TheWall.getTurn(1)).addScore(-10);
				resetBalls();
				wall.update();
			}
		}
	}

	private void position2() { // ball 2
		if (n > 0) {
			n += 8;
			y4 = y2 - (int) (40 * ((n * .025) * Math.tan(Math.toRadians(angle2)) - 98.1 * (n * .025) * (n * .025) / (2
			        * Math.pow(velocity2 * 1.5 * Math.cos(Math.toRadians(angle2)), 2))));
			x4 = x2 - (int) n;
			hitwall(x4, y4);
			if (y4 >= y - ballsize / 2) {
				TheWall.getPlayer(TheWall.getTurn(1)).addScore(-10);
				resetBalls();
				wall.update();
				score();
			}
			else if ((y4 + (ballsize / 2)) < (y - walls.height * by)) {
				TheWall.getPlayer(TheWall.getTurn(1)).addScore(-10);
				resetBalls();
				wall.update();
				score();
			}
		}

	}

	private void hitwall(int x_, int y_) {
		if (TheWall.getTurn() == 1 && range(x_, x1 - 30, x1 + 30) && range(y_, y1 - 30, y1 + 30)) { // check to see if
		                                                                                            // it hit cannon
			TheWall.getPlayer(TheWall.getTurn()).setDestroyed(true);
			if (wall.getBombdet()) TheWall.getPlayer(TheWall.getTurn(1)).addScore(150);
			else TheWall.getPlayer(TheWall.getTurn(1)).addScore(500);
			score();
			wall.update();
			resetBalls();

		}
		else if (TheWall.getTurn() == 2 && range(x_, x2 - 30, x2 + 30) && range(y_, y2 - 30, y2 + 30)) {// same for
		                                                                                                // other user
			// System.out.println("p2");
			TheWall.getPlayer(TheWall.getTurn()).setDestroyed(true);
			if (wall.getBombdet()) TheWall.getPlayer(TheWall.getTurn(1)).addScore(150);
			else TheWall.getPlayer(TheWall.getTurn(1)).addScore(500);
			score();
			wall.update();
			resetBalls();
		}
		else {
			breakpoint: for (int i = 0; i < wall.getRows(); i++) { // iterate through each brick,
				for (int j = 0; j < wall.getColumns(); j++) {
					if (wall.getBrick(j, i).getAlmost() == 0 && range(x_, wall.getBrick(j, i).getPos().width - ballsize
					        / 2, wall.getBrick(j, i).getPos().width + bx + ballsize / 2) && range(y_, wall.getBrick(j,
					                i).getPos().height - ballsize / 2, wall.getBrick(j, i).getPos().height + by
					                        + ballsize / 2)) {
						wall.explode(wall.getBrick(j, i)); // see if it's a bomb
						Graphics g = getGraphics();

						g.setColor(new Color((float) .619, (float) .2784, (float) .2784, (float) .7));
						g.fillRect(wall.getBrick(j, i).getPos().width, wall.getBrick(j, i).getPos().height, bx, by); // paint
						                                                                                             // current
						                                                                                             // rectange
						                                                                                             // as
						                                                                                             // hit
						TheWall.getPlayer(TheWall.getTurn(1)).addScore(10);
						score();
						wall.getBrick(j, i).addAlmost();
						wall.update();
						resetBalls();
						break breakpoint;
					}
				}
			}
		}
	}

	public static boolean range(int x, int min, int max) {
		return x > min && x < max;
	}

	private void resetBalls() {
		m = 0;
		n = 0;
		x3 = x1;
		y3 = y1;
		x4 = x2;
		y4 = y2;
		donedrawing = true;

	}

	private void cannons() {
		Point p1, p2, p3, p4, p5;
		int l = 3;

		p1 = new Point(-10 * l, 0 * l);
		p2 = new Point(-7 * l, 5 * l);
		p3 = new Point(10 * l, 4 * l);
		p4 = new Point(10 * l, -4 * l);
		p5 = new Point(-7 * l, -5 * l);
		int x1Poly[] = { p1.x, p2.x, p3.x, p4.x, p5.x };
		int y1Poly[] = { p1.y, p2.y, p3.y, p4.y, p5.y };
		int x2Poly[] = { -p1.x, -p2.x, -p3.x, -p4.x, -p5.x };
		int y2Poly[] = { p1.y, p2.y, p3.y, p4.y, p5.y };

		cannon1 = new Polygon(x1Poly, y1Poly, x1Poly.length);
		cannon2 = new Polygon(x2Poly, y2Poly, x2Poly.length);
		cannon1 = rotate(x1, y1, -angle1, cannon1);
		cannon2 = rotate(x2, y2, angle2, cannon2);
	}

	private void arrows() {
		Point p1, p2, p3, p4, p5, p6, p7;
		int l = 3;
		long v1 = (long) (velocity1);
		long v2 = (long) (velocity2);
		p1 = new Point(10 * l, 2);
		p2 = new Point(10 * l + (int) (v1 * l), 2);
		p3 = new Point(10 * l + (int) (v1 * l), 3 * l);
		p4 = new Point(13 * l + (int) (v1 * l), 0);

		p5 = new Point(10 * l + (int) (v2 * l), 2);
		p6 = new Point(10 * l + (int) (v2 * l), 3 * l);
		p7 = new Point(13 * l + (int) (v2 * l), 0);
		int x1Poly[] = { p1.x, p2.x, p3.x, p4.x, p3.x, p2.x, p1.x };
		int y1Poly[] = { p1.y, p2.y, p3.y, p4.y, -p3.y, -p2.y, -p1.y };
		int x2Poly[] = { -p1.x, -p5.x, -p6.x, -p7.x, -p6.x, -p5.x, -p1.x };
		int y2Poly[] = { p1.y, p5.y, p6.y, p7.y, -p6.y, -p5.y, -p1.y };

		arrow1 = new Polygon(x1Poly, y1Poly, x1Poly.length);
		arrow2 = new Polygon(x2Poly, y2Poly, x2Poly.length);
		arrow1 = rotate(x1, y1, -angle1, arrow1);
		arrow2 = rotate(x2, y2, angle2, arrow2);
	}

	private Polygon rotate(int cx, int cy, double a, Polygon p) { // rotate and translate any polygon around a point.
	                                                              // current plolygon origin (0,0) is axis for rotation,
	                                                              // then moved to set points
		Point pt;
		for (int i = 0; i < p.npoints; i++) {
			pt = new Point(p.xpoints[i], p.ypoints[i]);
			double tx, ty;
			double c = Math.cos(Math.toRadians(a));
			double s = Math.sin(Math.toRadians(a));
			tx = pt.x * c - pt.y * s;
			ty = pt.x * s + pt.y * c;
			pt.x = (int) (tx + cx);
			pt.y = (int) (ty + cy);
			p.xpoints[i] = pt.x;
			p.ypoints[i] = pt.y;
		}

		return p;
	}

	/**
	 * @return the donedrawing
	 */
	public Boolean getDonedrawing() {
		return donedrawing;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
