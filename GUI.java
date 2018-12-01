package minesweeper;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {
	public boolean resetter = false;

	Date startDate = new Date();

	Date endDate;

	int spacing = 5;

	int neighs = 0;

	String vicMes = "Nothing Yet.";

	public int mx = -100;

	public int my = -100;

	public int smileyX = 605;// smiley adjustment
	public int smileyY = 5;

	public int faceCenterX = smileyX + 35;
	public int faceCenterY = smileyY + 35;

	public int timeX = 900;// number position adjustment
	public int timeY = 5;

	public int vicMesX = 700;

	public int vicMesY = -50;

	public int sec = 0;

	public boolean face = true;

	public boolean victory = false; // when this is false, we havent won yet

	public boolean defeat = false;// when this is false, we havent lost

	Random rand = new Random();

	int[][] bombs = new int[8][8];// Set all 8x8 for blocks and references to the blocks
	int[][] neighbours = new int[8][8];
	boolean[][] revealed = new boolean[8][8];
	boolean[][] flagged = new boolean[8][8];

	public GUI() {
		this.setTitle("Minesweeper");
		this.setSize(1240, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);// Sets window to visible
		this.setResizable(true);// Makes the window adjustable

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (rand.nextInt(100) < 30) {
					bombs[i][j] = 1;
				} else {
					bombs[i][j] = 0;
				}
				revealed[i][j] = false;
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				neighs = 0;
				for (int b = 0; b < 8; b++) {
					for (int n = 0; n < 8; n++) {
						if (!(b == i && n == j)) {
							if (isN(i, j, b, n) == true)
								neighs++;
						}
					}
				}
				neighbours[i][j] = neighs;
			}
		}

		Board board = new Board();
		this.setContentPane(board);

		Move move = new Move();
		this.addMouseMotionListener(move);

		Click click = new Click();
		this.addMouseListener(click);

	}

	public class Board extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, 1280, 800);
			g.setColor(Color.DARK_GRAY);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					g.setColor(Color.BLUE);

					if (revealed[i][j] == true) {
						g.setColor(Color.WHITE);
						if (bombs[i][j] == 1) {
							g.setColor(Color.RED);
						}
					}
					if (mx >= spacing + i * 80 && mx < spacing + i * 80 + 80 - 2 * spacing
							&& my >= spacing + j * 80 + 80 + 26 && my < spacing + j * 80 + 80 + 80 - 2 * spacing) {
						g.setColor(Color.lightGray);
					}
					g.fillRect(spacing + i * 80, spacing + j * 80 + 80, 80 - 2 * spacing, 80 - 2 * spacing);
					if (revealed[i][j] == true) {
						g.setColor(Color.BLACK);
						if (bombs[i][j] == 0 && neighbours[i][j] != 0) {
							if (neighbours[i][j] == 1) {
								g.setColor(Color.blue);
							} else if (neighbours[i][j] == 2) {
								g.setColor(Color.green);
							} else if (neighbours[i][j] == 3) {
								g.setColor(Color.red);
							} else if (neighbours[i][j] == 4) {
								g.setColor(new Color(0, 0, 128));
							} else if (neighbours[i][j] == 5) {
								g.setColor(new Color(178, 34, 34));
							} else if (neighbours[i][j] == 6) {
								g.setColor(new Color(72, 209, 204));
							} else if (neighbours[i][j] == 7) {
								g.setColor(Color.black);
							} else if (neighbours[i][j] == 8) {
								g.setColor(Color.darkGray);
							}

							g.setFont(new Font("Tahoma", Font.BOLD, 20));
							g.drawString(Integer.toString(neighbours[i][j]), i * 80 + 20, j * 80 + 80 + 55);

						} else if (bombs[i][j] == 1) {
							g.fillRect(i * 80 + 10, j * 80 + 80, 10, 5);
							g.fillRect(i * 80, j * 80 + 80, 10, 10);
							g.fillRect(+i * 80 + 5, j * 80 + 80 + 5, 10, 10);
							g.fillRect(i * 80, j * 80 + 80, 4, 30);
							g.fillRect(i * 80, j * 80 + 80, 30, 4);

						}

					}
				}

			}

			// Face painting

			g.setColor(Color.yellow);
			g.fillOval(smileyX, smileyY, 70, 70);
			g.setColor(Color.BLACK);
			g.fillOval(smileyX + 15, smileyY + 20, 10, 10);
			g.fillOval(smileyX + 45, smileyY + 20, 10, 10);
			if (face == true) {
				g.fillRect(smileyX + 20, smileyY + 50, 30, 5);
				g.fillRect(smileyX + 17, smileyY + 45, 5, 5);
				g.fillRect(smileyX + 48, smileyY + 45, 5, 5);
			} else {
				g.fillRect(smileyX + 20, smileyY + 45, 30, 5);
				g.fillRect(smileyX + 17, smileyY + 35, 5, 5);
				g.fillRect(smileyX + 48, smileyY + 35, 5, 5);

			}
			// time counter method

			g.setColor(Color.black);
			g.fillRect(timeX, timeY, 140, 70);
			if (defeat == false && victory == false) {
				sec = (int) ((new Date().getTime() - startDate.getTime()) / 1000);
			}
			if (sec > 999) {
				sec = 999;
			}
			g.setColor(Color.white);
			if (victory == true) {
				g.setColor(Color.green);
			} else if (defeat == true) {
				g.setColor(Color.red);
			}
			g.setFont(new Font("Tahoma", Font.ITALIC, 80));
			if (sec < 10) {
				g.drawString("00" + Integer.toString(sec), timeX, timeY + 65);
			} else if (sec < 100) {
				g.drawString("0" + Integer.toString(sec), timeX, timeY + 65);
			} else {
				g.drawString(Integer.toString(sec), timeX, timeY + 65);
			}

			// Victory message painting
			if (victory == true) {
				g.setColor(Color.green);
				vicMes = "You win!";
			} else if (defeat == true) {
				g.setColor(Color.red);
				vicMes = "You Lose!";
			}

			if (victory == true || defeat == true) {
				vicMesY = -50 + (int) (new Date().getTime() - endDate.getTime()) / 10;
				if (vicMesY < 65) {
					vicMesY = 65;
				}
				g.setFont(new Font("Tahoma ", Font.PLAIN, 70));
				g.drawString(vicMes, vicMesX, vicMesY);
			}

		}
	}

	public class Move implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {

			mx = e.getX();// Gets the coordinates of the mouse
			my = e.getY();
			// System.out.println("X: " + mx + ", Y: " + my);// returning the coordinates of
			// the mouse

		}

	}

	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			if (inBoxX() != -1 && inBoxY() != -1) {
				revealed[inBoxX()][inBoxY()] = true;
			}

			if (inBoxX() != -1 && inBoxY() != -1) {
				System.out.println("The mouse is in the [" + inBoxX() + "," + inBoxY() + "], Number of bomb neighbors: "
						+ neighbours[inBoxX()][inBoxY()]);
			} else {
				System.out.println("The mouse is'nt in any box.");
			}

			if (inFace() == true) {
				System.out.println("The pointer is in the face!");
			} else {
				System.out.println("The pointer is not in the face!");
			}
			if (inFace() == true) {
				resetAll();
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

	public void checkVicitoryStatus() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (revealed[i][j] == true && bombs[i][j] == 1) {
					defeat = false;
					face = true;
					//endDate = 
				}
			}
		}
		if (totalBoxesRevealed() >= 81 - totalBombs() ) {
			victory = true;
		}
	}

	public int totalBombs() {
		int total = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (bombs[i][j] == 1) {
					total++;
				}
			}
		}
		return total;

	}

	public int totalBoxesRevealed() {
		int total = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (revealed[i][j] == true) {

				}
			}

		}
		return total;
	}

	public void resetAll() {
		resetter = true;

		startDate = new Date();

		vicMesY = -50;

		String vicMes = "Nothing yet.";

		face = true;
		victory = false;
		defeat = false;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (rand.nextInt(100) < 30) {
					bombs[i][j] = 1;
				} else {
					bombs[i][j] = 0;
				}
				revealed[i][j] = false;
				flagged[i][j] = false;
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				neighs = 0;
				for (int b = 0; b < 8; b++) {
					for (int n = 0; n < 8; n++) {
						if (!(b == i && n == j)) {
							if (isN(i, j, b, n) == true)
								neighs++;
						}
					}
				}
				neighbours[i][j] = neighs;
			}
		}
		
		resetter = false;
	}

	public boolean inFace() {
		int diff = (int) Math.sqrt(Math.abs(mx - faceCenterX) * Math.abs(mx - faceCenterX)
				+ Math.abs(my - faceCenterY) * Math.abs(my - faceCenterY));
		if (diff < 35) {
			return true;
		}
		return false;
	}

	public int inBoxX() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (mx >= spacing + i * 80 && mx < spacing + i * 80 + 80 - 2 * spacing
						&& my >= spacing + j * 80 + 80 + 26 && my < spacing + j * 80 + 80 + 80 - 2 * spacing)
					return i;
			}
		}
		return -1;
	}

	public int inBoxY() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (mx >= spacing + i * 80 && mx < spacing + i * 80 + 80 - 2 * spacing
						&& my >= spacing + j * 80 + 80 + 26 && my < spacing + j * 80 + 80 + 80 - 2 * spacing)
					return j;
			}
		}
		return -1;

	}

	public boolean isN(int mX, int mY, int cX, int cY) {
		if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mX - cY > -2 && bombs[cX][cY] == 1) {
			return true;
		}
		return false;
	}

}
