//0change

package game;

import model.PokerLabel;

public class Game {
	
	public static void move(PokerLabel pl, int a, int b) {
		pl.setLocation(a, b);
		
		
		try {
			Thread.sleep(80);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	

}
