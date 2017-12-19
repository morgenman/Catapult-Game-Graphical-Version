
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

public class wall {
	private Brick[][]	grid;
	private Brick[][]	gridtemp;
	private Boolean		bombdet;

	public wall(int x, int y) {
		bombdet = false;
		grid = new Brick[x][y];
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[j][i] = new Brick();
				grid[j][i].setWallplace(j, i);
			}
		}
		randombomb();

	}

	public void randombomb() { // make random bomb
		Dimension bomb = new Dimension((int) (Math.random() * getColumns()), (int) (Math.random() * getRows()));
		grid[bomb.width][bomb.height].setIsbomb(true);
	}

	public void rowCheck() { // see if row is destroyed
		int rowdestroyed;
		int[] badrow = new int[grid[0].length];
		int numOfBadRows = 0;
		int badrowi = 0;
		for (int i = 0; i < grid[0].length; i++) {
			rowdestroyed = 0;
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i].getDestroyed() == true) rowdestroyed++;
			}
			if (rowdestroyed == grid.length) {
				badrow[i] = 1;
			}
		}
		for (int k = 0; k < badrow.length; k++) {
			if (badrow[k] == 1) {
				numOfBadRows++;
			}
		}
		gridtemp = new Brick[5][grid[0].length - numOfBadRows];
		for (int i = 0; i < grid[0].length; i++) {
			if (badrow[i] == 1) {
				badrowi++;
			}
			for (int j = 0; j < grid.length; j++) {
				if (badrow[i] == 0) {
					gridtemp[j][i - badrowi] = grid[j][i];
				}
			}
		}
		grid = gridtemp;
	}

	public void update() {
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[j][i].setWallplace(j, i);
				if (grid[j][i].getAlmost() == 1) grid[j][i].setAlmost(2);
				else if (grid[j][i].getAlmost() == 2) {
					grid[j][i].setDestroyed(true);
				}
			}
		}
		try {
			wait(1);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		rowCheck();
	}

	public void wait(int time) throws InterruptedException {
		if (bombdet == false && (int) (Math.random() * getRows()) == (int) (Math.random() * getColumns() * 2))
		    explode(); // randomly make bomb go off
		Thread.sleep(1000 * time);
	}

	public void explode(Brick bomb) { // check to see if bomb hit
		if (bomb.getIsbomb()) {
			bombdet = true;
			TheWall.getPlayer(TheWall.getTurn(1)).addScore(190);
			for (int j = 0; j < getColumns(); j++) {
				for (int i = bomb.getWallplace().y - 2; i <= bomb.getWallplace().y + 2; i++) {
					TheWall.getCanvas();
					if (Canvas1.range(i, 0 - 1, getRows())) grid[j][i].setAlmost(1);

				}
			}
		}
	}

	public Brick getBomb() { // find bomb and return it
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i].getIsbomb()) return grid[j][i];
			}
		}
		return null;
	}

	public void explode() { // make bomb wherever it may be explode
		bombdet = true;
		Brick bomb = getBomb();
		TheWall.getPlayer(1).addScore(-400);
		TheWall.getPlayer(2).addScore(-400);
		TheWall.getCanvas().score();
		for (int j = 0; j < getColumns(); j++) {
			for (int i = bomb.getWallplace().y - 2; i <= bomb.getWallplace().y + 2; i++) {
				TheWall.getCanvas();
				if (Canvas1.range(i, 0 - 1, getRows())) grid[j][i].setAlmost(1);

			}
		}
	}

	public int getColumns() {
		return grid.length;
	}

	public int getRows() {
		return grid[0].length;
	}

	public Brick getBrick(int x, int y) {
		return grid[x][y];
	}

	/**
	 * @return
	 */
	public boolean getBombdet() {
		// TODO Auto-generated method stub
		return bombdet;
	}

}
