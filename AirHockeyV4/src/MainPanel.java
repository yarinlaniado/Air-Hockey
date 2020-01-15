import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.*;

//TO DO: speed fix and collision test!
public class MainPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static int width = 400;
	static int height = 700;
	private int humanVShuman = 1;
	private int humanVSai = 0;

	boolean Upkey = false;
	boolean Downkey = false;
	boolean MousePause = false;
	Player Player1;
	Player Player2;
	Puck GamePuck;
	ObjSpeed OS;
	Image backGroundImage;
	Image Icon;
	MML MouseListen = new MML();
	int movement = 10;
	Timer t;
	Timer t2;
	public boolean Leftkey;
	public boolean Rightkey;
	boolean firstflagcheck = true;
	boolean secondflagcheck = true;
	private int type = 0;
	private BotActions a,b;

	public MainPanel() {
		menu();
		
		ImageIcon ii = new ImageIcon("Board.png");
		backGroundImage = ii.getImage();
		ImageIcon iii = new ImageIcon("BluePlayer.png");
		Icon = iii.getImage();
		GamePuck = new Puck(this);
		Player1 = new Player(this, 1, GamePuck);
		Player2 = new Player(this, 2, GamePuck);
		
		if(type==humanVSai) {
		
			 b = new BotActions(Player2,GamePuck,this);
				synchronized(b) {
					b.start();	
					}
			//a = new BotActions(Player1,GamePuck,this);
			// a.start();
		}
		
		OS = new ObjSpeed(GamePuck, Player1, Player2);
		synchronized(OS) {
			OS.start();	
			}
	
		addMouseMotionListener(MouseListen);
		addKeyListener(new KL());
		setFocusable(true);
		t = new Timer(500, this);
		t.start();
		synchronized(t) {
			t.start();	
			}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), null);
		Player1.drawPlayer(g);
		Player2.drawPlayer(g);
		GamePuck.drawPuck(g);
		Font myFont = new Font("DIALOG", Font.BOLD, 25);
		g.setFont(myFont);
		g.setColor(Color.black);
		String Score = Player1.Score + " - " + Player2.Score;
		g.drawString("Score", 20, 400);
		g.drawString(Score, 32, 430);
		myFont = new Font("Consolas", Font.BOLD, 10);
		g.setFont(myFont);

		if (Player1.isPaused == true || Player2.isPaused == true) {
			g.setColor(new Color(255, 255, 255, 80));
			g.fillRect(0, 0, getWidth(), getHeight());
			Font myFont2 = new Font("Lucida Calligraphy", Font.BOLD, 36);
			g.setFont(myFont2);
			g.setColor(Color.black);
			g.drawString("PAUSE", getWidth() / 2 - 60, getHeight() / 2);

		}
		Test(g); // testing draw

	}

	private void Test(Graphics g) {
		// TEST
		// g.drawString(Double.toString(GamePuck.PuckSpeed), 20, 460);
		// g.drawString(Double.toString(Player1.PlayerSpeed), 20, 470);
		// g.drawString(Double.toString(Player2.PlayerSpeed), 20, 480);
		// g.drawRect(Player1.PlayerXpos + 2, Player1.PlayerYpos + 5, 70, 65);

		// g.drawRect(Player2.PlayerXpos + 2, Player2.PlayerYpos + 5, 70, 65);

		// g.drawRect(GamePuck.PuckXpos + 2, GamePuck.PuckYpos + 3, 35, 34);

		// g.drawRect(Player1.PlayerXpos + 2 -GamePuck.radios, Player1.PlayerYpos + 5
		// -GamePuck.radios, 70+2*GamePuck.radios, 65+2*GamePuck.radios);

		// g.drawRect(Player1.PlayerXpos + 27, Player1.PlayerYpos + 2, 20, 69); // top
		// and button rect -y

		// g.drawRect(Player1.PlayerXpos + 2, Player1.PlayerYpos+26, 69, 20); // left
		// and right - -x

		// g.drawRect(Player1.PlayerXpos + 2, Player1.PlayerYpos + 5, 70, 65);

		// g.setColor(Color.red);
		// g.drawLine(GamePuck.PuckMidx, GamePuck.PuckMidy, 0, 0);
		//// g.drawLine(GamePuck.Parray[7].x, GamePuck.Parray[7].y, 0, 0);
		// g.drawLine(GamePuck.Parray[6].x, GamePuck.Parray[6].y, 0, 0);
		// g.drawLine(GamePuck.Parray[5].x, GamePuck.Parray[5].y, 0, 0);
		// g.drawLine(GamePuck.Parray[4].x, GamePuck.Parray[4].y, 500, 800);
		//  g.drawLine(GamePuck.Parray[3].x, GamePuck.Parray[3].y, 0, 0);
		// g.drawLine(GamePuck.Parray[2].x, GamePuck.Parray[2].y, 0, 0);
		// g.drawLine(GamePuck.Parray[1].x, GamePuck.Parray[1].y, 0, 0);
		// g.drawLine(GamePuck.Parray[0].x, GamePuck.Parray[0].y, 0, 0);

	}

	class KL extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP)
				Upkey = false;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				Downkey = false;
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				Leftkey = false;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				Rightkey = false;

		}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (type == humanVShuman) {
				if (Player2.isPaused == false) {
					// Sec player
					if (key == KeyEvent.VK_LEFT && Player2.isPaused == false && !MousePause) {

						if (Player2.PlayerXpos > 12) {
							GamePuck.ParrayCheck();
							Player2.PlayerXpos = Player2.PlayerXpos - movement;
							if (Upkey == true)
								Player2.PlayerYpos = Player2.PlayerYpos - movement;
							else if (Downkey == true)
								Player2.PlayerYpos = Player2.PlayerYpos + movement;

							repaint();
						}

					}

					if (key == KeyEvent.VK_RIGHT && Player2.isPaused == false && !MousePause) {
						if (Player2.PlayerXpos < 300) {
							GamePuck.ParrayCheck();
							Player2.PlayerXpos = Player2.PlayerXpos + movement;
							if (Upkey == true)
								Player2.PlayerYpos = Player2.PlayerYpos - movement;
							else if (Downkey == true)
								Player2.PlayerYpos = Player2.PlayerYpos + movement;

							repaint();
						}

					}

					if (key == KeyEvent.VK_UP && Player2.isPaused == false && !MousePause && Player2.PlayerYpos > 0) {
						Upkey = true;
						GamePuck.ParrayCheck();
						Player2.PlayerYpos = Player2.PlayerYpos - movement;
						if (Leftkey == true)
							Player2.PlayerYpos = Player2.PlayerXpos - movement;
						else if (Rightkey == true)
							Player2.PlayerYpos = Player2.PlayerYpos + movement;

						repaint();

					}
					if (key == KeyEvent.VK_DOWN && !MousePause && Player2.PlayerYpos < 250) {
						Downkey = true;
						GamePuck.ParrayCheck();
						Player2.PlayerYpos = Player2.PlayerYpos + movement;
						if (Leftkey == true)
							Player2.PlayerYpos = Player2.PlayerXpos - movement;
						else if (Rightkey == true)
							Player2.PlayerYpos = Player2.PlayerYpos + movement;

						repaint();
					}
				}


				if (key == KeyEvent.VK_M && GamePuck.speed > 0) {

					GamePuck.speed--;
				}
				if (key == KeyEvent.VK_L) {

					GamePuck.speed++;
				}

				GamePuck.ParrayCheck();

			}
			else
			{
				if (Player1.isPaused == false) {
					//if (e.getX() > 0 && e.getX() <= 310 && e.getY() >= 332 && e.getY() <= 590) {
					// Sec player
					if (key == KeyEvent.VK_LEFT && Player1.isPaused == false && !MousePause) {

						if (Player1.PlayerXpos > 0) {
							GamePuck.ParrayCheck();
							Player1.PlayerXpos = Player1.PlayerXpos - movement;
							if (Upkey == true)
								Player1.PlayerYpos = Player1.PlayerYpos - movement;
							else if (Downkey == true)
								Player1.PlayerYpos = Player1.PlayerYpos + movement;

							repaint();
						}

					}

					if (key == KeyEvent.VK_RIGHT && Player1.isPaused == false && !MousePause) {
						if (Player1.PlayerXpos < 300) {
							GamePuck.ParrayCheck();
							Player1.PlayerXpos = Player1.PlayerXpos + movement;
							if (Upkey == true)
								Player1.PlayerYpos = Player1.PlayerYpos - movement;
							else if (Downkey == true)
								Player1.PlayerYpos = Player1.PlayerYpos + movement;

							repaint();
						}

					}

					if (key == KeyEvent.VK_UP && Player1.isPaused == false && !MousePause && Player1.PlayerYpos > 331) {
						Upkey = true;
						GamePuck.ParrayCheck();
						Player1.PlayerYpos = Player1.PlayerYpos - movement;
						if (Leftkey == true)
							Player1.PlayerYpos = Player1.PlayerXpos - movement;
						else if (Rightkey == true)
							Player1.PlayerYpos = Player1.PlayerYpos + movement;

						repaint();

					}
					if (key == KeyEvent.VK_DOWN && !MousePause && Player1.PlayerYpos < 590) {
						Downkey = true;
						GamePuck.ParrayCheck();
						Player1.PlayerYpos = Player1.PlayerYpos + movement;
						if (Leftkey == true)
							Player1.PlayerYpos = Player1.PlayerXpos - movement;
						else if (Rightkey == true)
							Player1.PlayerYpos = Player1.PlayerYpos + movement;

						repaint();
					}
				}
			}
			if (key == KeyEvent.VK_L) {

			Player1.print();
			Player2.print();


			}

			if (key == KeyEvent.VK_P) {

				Player2.isPaused = true;
				Player1.isPaused = true;
				GamePuck.isPaused = true;

			}
			if (key == KeyEvent.VK_R) {
				Player2.isPaused = false;
				Player1.isPaused = false;
				GamePuck.isPaused = false;

				synchronized (Player1) {
					Player1.notify();
				}
				synchronized (Player2) {
					Player2.notify();
				}
				synchronized (GamePuck) {
					GamePuck.notify();
				}

			}
		}
	}

	class MML extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			if (!Player1.isPaused && !MousePause) {
				GamePuck.ParrayCheck();
				if (e.getX() > 0 && e.getX() <= 310 && e.getY() >= 332 && e.getY() <= 590) {
					Player1.PlayerXpos = e.getX();
					Player1.PlayerYpos = e.getY();
				}
			}
		}
	}

	public void hideMouseCursor() {
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorimg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorimg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JPanel.
		setCursor(blankCursor);
	}

	public static void main(String[] args) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame f = new JFrame("• Air Hockey - By Yarin Laniado •");
		MainPanel bp = new MainPanel();
		f.add(bp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(10, 10, width, height);
		f.setResizable(false);
		f.setVisible(true);
		f.setFocusable(false);
		f.setIconImage(bp.Icon);
		f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);
		bp.hideMouseCursor();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GamePuck.ParrayCheck();
		if (firstflagcheck) {
			firstflagcheck = false;
			if (Player1.DistanceBetweenPuckAndPlayer() <= Player1.radios + GamePuck.radios) {
				GamePuck.Collision(Player1);

			}
		} else if (Player1.DistanceBetweenPuckAndPlayer() > Player1.radios + GamePuck.radios) {
			firstflagcheck = true;
		}

		if (secondflagcheck) {
			secondflagcheck = false;
			if (Player2.DistanceBetweenPuckAndPlayer() <= Player2.radios + GamePuck.radios) {
				GamePuck.Collision(Player2);

			}
		} else if (Player2.DistanceBetweenPuckAndPlayer() > Player2.radios + GamePuck.radios) {
			secondflagcheck = true;
		}
		repaint();

	}

	public void menu() {

		String[] options = { "humanVsHuman", "humanVscomputer", "Exit" };
		int response = JOptionPane.showOptionDialog(null, "Choose Type Game", "Starting Game Options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		switch (response) {
		case -1:
			System.out.println("Option Dialog Window Was Closed");
			System.exit(0);

		case 0:
			type = humanVShuman;
			break;
		case 1:
			type = humanVSai;
			break;

		case 2:
			System.exit(0);

		default:
			break;
		}

	}

}
