
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Player extends Thread {
	int PlayerXpos;
	int PlayerYpos;
	int prevPlayerXpos;
	int prevPlayerYpos;
	int width = 70;
	int height = 65;
	int size;
	int PlayerXdir;
	int PlayerYdir;
	int midPlayerXpos;
	int midPlayerYpos;
	int Score;
	int Pnum;
	double PlayerSpeed;
	double radios = 35;
	boolean isPaused = false;
	boolean flagcheck = true;
	Image PlayerImage;
	MainPanel m;
	Puck GamePuck;
	private int count;

	public Player(MainPanel MP, int num, Puck GP) {
		PlayerXpos = 160;
		PlayerYpos = 500;
		count = 0;
		// midPlayerXpos = (PlayerXpos + width) / 2;
		// midPlayerYpos = (PlayerYpos + height) / 2;
		m = MP;
		size = 70;
		ImageIcon ii = new ImageIcon("BluePlayer.png");
		PlayerImage = ii.getImage();
		Pnum = num;
		if (num == 2) {
			ii = new ImageIcon("RedPlayer.png");
			PlayerImage = ii.getImage();
			PlayerYpos = 0;
			Score = 0;
		}
		GamePuck = GP;
		synchronized (this) {
			start();
		}

	}

	public void run() {
		while (true) {
			if (isPaused) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {

				midPlayerXpos = PlayerXpos + (width / 2);
				midPlayerYpos = PlayerYpos + (height / 2);

				if (flagcheck) {
					if (DistanceBetweenPuckAndPlayer() <= radios + GamePuck.radios) {
						flagcheck = false;
						GamePuck.Collision(this);
					/*	try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
					}

				}

				if (DistanceBetweenPuckAndPlayer() > radios + GamePuck.radios) {
					flagcheck = true;
				}
			}

			Goal(Pnum); // win check
			count++;
			
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			
				m.repaint();

		}
	}

	public void drawPlayer(Graphics g) {
		g.drawImage(PlayerImage, PlayerXpos, PlayerYpos, size, size, null);
	}

	public void print() {
		System.out.println("x: " + PlayerXpos + " Y: " + PlayerYpos);
	}

	public double DistanceBetweenPuckAndPlayer() {
		return Math.hypot(midPlayerXpos - GamePuck.PuckMidx, midPlayerYpos - GamePuck.PuckMidy);
	}

	public void Goal(int Pnum) {

		if (GamePuck.PuckXpos > 80 && GamePuck.PuckXpos < 250)
			if (Pnum == 2) {
				if (GamePuck.PuckYpos <= 10) {
					Score++;
					GamePuck.ResetPuck();
				}
			} else {
				if (GamePuck.PuckYpos >= 590) {
					Score++;
					GamePuck.ResetPuck();
				}
			}
	}

}
