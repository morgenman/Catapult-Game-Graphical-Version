
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

public class Player {
	private int		number;
	private int		score;
	private Boolean	turn;
	private Boolean	destroyed;

	public Player(int number) {
		this.number = number;
		this.destroyed = false;
		score = 0;
		if (number == 1) turn = true;
		else turn = false;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the destroyed
	 */
	public Boolean getDestroyed() {
		return destroyed;
	}

	/**
	 * @param destroyed
	 *            the destroyed to set
	 */
	public void setDestroyed(Boolean destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * @return the score
	 */

	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public void addScore(int score) {
		this.score = this.score + score;
	}

	/**
	 * @return the turn
	 */
	public Boolean getTurn() {
		return turn;
	}

	/**
	 * @param turn
	 *            the turn to set
	 */
	public void setTurn(Boolean turn) {
		this.turn = turn;
	}
}
