
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

import java.awt.Dimension;
import java.awt.Point;

public class Brick {
	private Boolean		destroyed;
	private Boolean		isbomb;
	private int			almost;
	private Dimension	pos;
	private Point		wallplace;

	/**
	 * @return the wallplace
	 */
	public Point getWallplace() {
		return wallplace;
	}

	/**
	 * @param wallplace
	 *            the wallplace to set
	 */
	public void setWallplace(int x, int y) {
		this.wallplace = new Point(x, y);
	}

	public Brick() {
		destroyed = false;
		isbomb = false;
		almost = 0;
	}

	/**
	 * @return the pos
	 */
	public Dimension getPos() {
		return pos;
	}

	/**
	 * @param pos
	 *            the pos to set
	 */
	public void setPos(int x, int y) {
		this.pos = new Dimension(x, y);
	}

	/**
	 * @return the destroyed
	 */
	public Boolean getDestroyed() {
		return destroyed;
	}

	/**
	 * @return the almost
	 */
	public int getAlmost() {
		return almost;
	}

	/**
	 * @param almost
	 *            the almost to set
	 */
	public void setAlmost(int almost) {
		this.almost = almost;
	}

	/**
	 * @param destroyed
	 *            the destroyed to set
	 */
	public void setDestroyed(Boolean destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * @return the isbomb
	 */
	public Boolean getIsbomb() {
		return isbomb;
	}

	/**
	 * @param isbomb
	 *            the isbomb to set
	 */
	public void setIsbomb(Boolean isbomb) {
		this.isbomb = isbomb;
	}

	/**
	 * 
	 */
	public void addAlmost() {
		// TODO Auto-generated method stub
		almost++;
	}
}
