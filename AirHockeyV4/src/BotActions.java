import java.awt.Point;

import javax.swing.JOptionPane;

public class BotActions extends Thread {

	Player Bot;
	Puck GamePuck;
	MainPanel m;
	int Score;
	int choose;
	int[] levels = { 3, 10, 20 };
	private int count;
	private boolean isPaused = false;

	public BotActions(Player player2, Puck gamePuck, MainPanel mainPanel) {
		Bot = player2;
		Score = 0;
		Bot.PlayerYpos = 200;
		GamePuck = gamePuck;
		m = mainPanel;
		choose = 0;
		menu();
	}

	public void run() { // checks array of points and hits the closest one
		int level = levels[choose];
		while (true) {
			if (isPaused) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				Point hit = bestattack(Bot, GamePuck.Parray);
				if (GamePuck.PuckYpos < 290) {
					if (GamePuck.PrevPuckYpos > Bot.PlayerYpos) // ATTACK
					{
						if (Bot.PlayerXpos > 1 && Bot.PlayerXpos <= 311 && Bot.PlayerYpos < 290) {
							if (Bot.PlayerXpos < hit.x && Bot.PlayerXpos + 1 < 310)
								Bot.PlayerXpos++;
							else if (Bot.PlayerXpos - 1 > 1)
								Bot.PlayerXpos--;
							if (Bot.PlayerYpos < hit.y)
								Bot.PlayerYpos++;

							if (Bot.DistanceBetweenPuckAndPlayer() < 40) {
								if (Bot.PlayerYpos > 1 || !Bot.flagcheck) {
									Bot.PlayerYpos--;
								}

							}

						}
					} else // Defense
					{
						if (Bot.PlayerXpos >= 1 && Bot.PlayerXpos <= 311 && Bot.PlayerYpos < 290) {
							if (Bot.DistanceBetweenPuckAndPlayer() < 45) {
								if (Bot.PlayerYpos >= 1 || !Bot.flagcheck) {
									Bot.PlayerYpos--;
								}
							} else {
								if (Bot.PlayerXpos >= hit.x && Bot.PlayerXpos - 1 > 1)
									Bot.PlayerXpos--;
								else if (Bot.PlayerXpos + 1 > 311)
									Bot.PlayerXpos++;

								if (Bot.PlayerYpos >= hit.y && Bot.PlayerYpos - 1 > 1)
									Bot.PlayerYpos--;
								else if (Bot.PlayerYpos <= hit.y && Bot.PlayerYpos + 1 < 290)
									Bot.PlayerYpos++;

							}
						}
					}

				}

				else {
					if (Bot.PlayerYpos > 1 || !Bot.flagcheck) {
						Bot.PlayerYpos--;
					}
					if (Bot.PlayerXpos > 310 || !Bot.flagcheck) {
						Bot.PlayerXpos--;
					}

				}
				m.repaint();
				try {
					sleep(level);
				} catch (InterruptedException e) {
				}

			}
		}
	}

	private Point bestattack(Player p, Point[] Parray) {
		GamePuck.ParrayCheck();
		Double Distance, MinDistance;
		int c = 0;
		// TODO Auto-generated method stub
		MinDistance = Math.hypot(p.midPlayerXpos - Parray[0].x, p.midPlayerYpos - Parray[0].y);
		for (int i = 1; i < 8; i++) {
			Distance = Math.hypot(p.midPlayerXpos - Parray[i].x, p.midPlayerYpos - Parray[i].y); // player+
			if (MinDistance > Distance) {
				c = i;
				MinDistance = Distance;
			}
		}
		return Parray[c];
	}

	public void menu() {

		String[] options = { "hard", "medium", "easy", "exit" };
		int response = JOptionPane.showOptionDialog(null, "Choose Level", "Starting Game Level",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		switch (response) {
		case -1:
			System.exit(0);

		case 0:
			choose = 0;
			break;
		case 1:
			choose = 1;
			break;

		case 2:
			choose = 2;
			break;

		case 3:
			System.exit(0);
			break;

		default:
			break;
		}

	}

}
