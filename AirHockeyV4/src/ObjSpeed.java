
public class ObjSpeed extends Thread {
	Player Player1;
	Player Player2;
	Puck GamePuck;
	int speed = 200;

	public ObjSpeed(Puck gamePuck2, Player player12, Player player22) {
		Player1 = player12;
		Player2 = player22;
		GamePuck = gamePuck2;
		// TODO Auto-generated constructor stub
	}

	public void run() {
		while (true) {
			GamePuck.PuckSpeed = Math.hypot(GamePuck.PrevPuckXpos - GamePuck.PuckXpos,
					GamePuck.PrevPuckYpos - GamePuck.PuckYpos) / (speed / 100);
			Player1.PlayerSpeed = Math.hypot(Player1.prevPlayerXpos - Player1.PlayerXpos,
					Player1.prevPlayerYpos - Player1.PlayerYpos) / (speed / 100);
			Player2.PlayerSpeed = Math.hypot(Player2.prevPlayerXpos - Player2.PlayerXpos,
					Player2.prevPlayerYpos - Player2.PlayerYpos) / (speed / 100);

			GamePuck.PrevPuckXpos = GamePuck.PuckXpos;
			GamePuck.PrevPuckYpos = GamePuck.PuckYpos;

			Player1.prevPlayerXpos = Player1.PlayerXpos;
			Player1.prevPlayerYpos = Player1.PlayerYpos;

			Player2.prevPlayerXpos = Player2.PlayerXpos;
			Player2.prevPlayerYpos = Player2.PlayerYpos;

			Goal(Player1);
			Goal(Player2);

			try {
				sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void Goal(Player one) {

		if (GamePuck.PuckXpos > 80 && GamePuck.PuckXpos < 250)
			if (one.Pnum == 2) {
				if (GamePuck.PuckYpos <= 10 || GamePuck.PuckYpos >= 590) {
					one.Score++;
					GamePuck.ResetPuck();
				}
			}

	}

}
