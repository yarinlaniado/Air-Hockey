
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Puck extends Thread {
	int PuckXpos;
	int PuckYpos;
	int PrevPuckXpos;
	int PrevPuckYpos;
	int PuckMidx;
	int PuckMidy;
	int PuckXdir;
	int PuckYdir;
	int width = 40;
	int height = 40;
	Point Parray[];
	int size;
	int speed = 20;
	Image PuckImage;
	MainPanel m;
	boolean isPaused = false;

	double PuckSpeed = 0;
	double radios = 17;

	public Puck(MainPanel MP) {

		PuckXpos = 210;
		PuckYpos = 370;
		PuckXdir = -10 + (int) (Math.random() * 10);
		PuckYdir = -10 + (int) (Math.random() * 10);
		Parray = new Point[8];
		for (int i = 0; i < 8; i++)
			Parray[i] = new Point(0, 0);
		ParrayCheck();
		size = 35;
		m = MP;
		ImageIcon ii = new ImageIcon("puck.png");
		PuckImage = ii.getImage();
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
						e.printStackTrace();
					}
				}
			} else if (PuckYpos < 0 || PuckYpos > 610) {// top and bottom
				PuckYdir = PuckYdir * -1;

			}
			if (PuckXpos < 0 || PuckXpos > 345) {// left and right
				PuckXdir = PuckXdir * -1;

			}

			if (PuckXdir == 0)
				PuckXdir = -5 + (int) (Math.random() * 5);
			else if (PuckYdir == 0)
				PuckYdir = -5 + (int) (Math.random() * 5);

			PuckXpos += PuckXdir;
			PuckYpos += PuckYdir;

			PuckMidx = PuckXpos + (width / 2);
			PuckMidy = PuckYpos + (height / 2);
			ParrayCheck();

			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void ResetPuck() {
		PuckXpos = 210;
		PuckYpos = 370;
		PuckMidx = PuckXpos + (width / 2);
		PuckMidy = PuckYpos + (height / 2);
		PuckXdir = -5 + (int) (Math.random() * 5);
		PuckYdir = -5 + (int) (Math.random() * 5);
		speed = 20;

	}

	public void drawPuck(Graphics g) {
		g.drawImage(PuckImage, PuckXpos, PuckYpos, size, size, null);
	}

	public Rectangle Rec() {
		return new Rectangle(PuckXpos + 2, PuckYpos + 3, 35, 34);

	}

	public void print() {
		System.out.println("x: " + PuckXpos + " Y: " + PuckYpos);
	}

	public void ParrayCheck() {

		Parray[0].x = PuckMidx;
		Parray[0].y = (int) (PuckMidy - radios) + 5;

		Parray[1].x = (int) (PuckMidx + (radios * Math.cos(45)));
		Parray[1].y = (int) (PuckMidy - radios * Math.sin(45));

		Parray[2].x = (int) (PuckMidx + radios);
		Parray[2].y = PuckMidy;

		Parray[3].x = (int) (PuckMidx + (radios * Math.cos(45)));
		Parray[3].y = (int) (PuckMidy + radios * Math.sin(45));

		Parray[4].x = (int) PuckMidx;
		Parray[4].y = (int) (PuckMidy + radios);

		Parray[5].x = (int) (PuckMidx - (radios * Math.cos(45)));
		Parray[5].y = (int) (PuckMidy + radios * Math.sin(45));

		Parray[6].x = (int) (PuckMidx - radios);
		Parray[6].y = PuckMidy;

		Parray[7].x = (int) (PuckMidx - (radios * Math.cos(45)));
		Parray[7].y = (int) (PuckMidy - radios * Math.sin(45));

	}

	public void Collision(Player p) {
		Double Distance, MinDistance;
		int c = 0;
		if (speed > p.PlayerSpeed) {
			PuckYdir -= 2;
			PuckXdir -= 2;
		}
		if (speed < p.PlayerSpeed) {
			PuckYdir += 2;
			PuckXdir += 2;
		}
		// TODO Auto-generated method stub
		MinDistance = Math.hypot(p.midPlayerXpos - Parray[0].x, p.midPlayerYpos - Parray[0].y);
		for (int i = 1; i < 8; i++) {
			Distance = Math.hypot(p.midPlayerXpos - Parray[i].x, p.midPlayerYpos - Parray[i].y); // player+
			if (MinDistance > Distance) {
				c = i;
				MinDistance = Distance;
			}
		}
		switch (c) {
		case 0:
			if (PuckYdir * -1 < 10 || PuckYdir * -1 > -10) {
				PuckYdir *= -1;
				PuckYpos += PuckYdir;
			}
			break;
		case 1:
			if ((PuckYdir * -1 < 10 || PuckYdir * -1 > -10) && PuckXdir * -1 < 10 || PuckXdir * -1 > -10) {
				PuckYdir *= -1;
				PuckXdir *= -1;
				PuckXpos += PuckXdir;
				PuckYpos += PuckYdir;

			}

			break;
		case 2:
			if (PuckXdir * -1 < 10 || PuckXdir * -1 > -10) {
				PuckXdir *= -1;
				PuckXpos += PuckXdir;
			}
			break;
		case 3:
			if ((PuckYdir * -1 < 10 || PuckYdir * -1 > -10) && PuckXdir * -1 < 10 || PuckXdir * -1 > -10) {
				PuckYdir *= -1;
				PuckXdir *= -1;
				PuckXpos += PuckXdir;
				PuckYpos += PuckYdir;
			}
			break;
		case 4:
			if (PuckYdir * -1 < 10 || PuckYdir * -1 > -10) {
				PuckYdir *= -1;
				PuckYpos += PuckYdir;
			}
			break;
		case 5:
			if ((PuckYdir * -1 < 10 || PuckYdir * -1 > -10) && PuckXdir * -1 < 10 || PuckXdir * -1 > -10) {
				PuckYdir *= -1;
				PuckXdir *= -1;
				PuckXpos += PuckXdir;
				PuckYpos += PuckYdir;
			}
			break;
		case 6:
			if (PuckXdir * -1 < 10 || PuckXdir * -1 > -10) {
				PuckXdir *= -1;
				PuckXpos += PuckXdir;
				PuckXpos += PuckXdir;
				PuckYpos += PuckYdir;

			}
			break;
		case 7:
			if ((PuckYdir * -1 < 10 || PuckYdir * -1 > -10) && PuckXdir * -1 < 10 || PuckXdir * -1 > -10) {
				PuckYdir *= -1;
				PuckXdir *= -1;
				PuckXpos += PuckXdir;
				PuckYpos += PuckYdir;
			}
			break;
		default:
			System.out.println("wtf");
			break;
		}

	}

}
