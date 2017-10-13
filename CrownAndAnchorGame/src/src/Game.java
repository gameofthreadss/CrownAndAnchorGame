package src;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Game {

	private List<Dice> dice;
	private List<DiceValue> values;
	
	public Game(Dice die1, Dice die2, Dice die3) {
		if (die1 == null || die2 == null || die3 == null) throw new IllegalArgumentException("Dice cannot be null.");
		dice = new ArrayList<Dice>();
		dice.add(die1);
		dice.add(die2);
		dice.add(die3);
		values = new ArrayList<DiceValue>();
	}

	public List<DiceValue> getDiceValues() {
		values.clear();
		for (Dice d : dice) {               
                    //values.add( d.getValues());//original bug 3
		    values.add( d.roll());//bug 3 corrected
		}
		return Collections.unmodifiableList(values);
	}	
	
	public int playRound(Player player, DiceValue pick, int bet ) {		
		if (player == null) throw new IllegalArgumentException("Player cannot be null.");
		if (pick == null) throw new IllegalArgumentException("Pick cannot be negative.");
		if (bet < 0) throw new IllegalArgumentException("Bet cannot be negative.");
		
		//  player.takeBet(bet);//bug 1
		int matches = 0;
		for ( Dice d : dice) {
			d.roll();
			if (d.getValue().equals(pick)) { 
				matches += 1;
			}
		}
                
		// System.out.println("Matches :" + matches + "+ Bet Amount: +"+ bet );
                 
		int winnings = matches * bet;
                //To calculate wiinig amount
                // System.out.println("Winning  :" + winnings );
                //System.out.println("balance before bet :\n\n" + player.getBalance());
		if (matches > 0) {			
			player.receiveWinnings(winnings);                       
                        //System.out.println("balance after bet :\n\n" + player.getBalance());
		}
                else
                {
                player.takeBet(bet);//bug 1 correected                   
                }
        return winnings;		
	}
	
}
