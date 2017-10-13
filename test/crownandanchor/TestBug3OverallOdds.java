package crownandanchor;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import crownandanchor.Dice;
import crownandanchor.DiceValue;
import crownandanchor.Game;
import crownandanchor.Player;

public class TestBug3OverallOdds {

	private static final String NAME = "Sam";
	private static final int STARTING_BALANCE = 1000;
	private static final int BETTING_LIMIT = 0;
	private static final DiceValue PICK_CROWN = DiceValue.CROWN;
	private static final int BET_AMOUNT = 5;
	
	private static final double WIN_RATE = 0.42;
	private static final double WIN_RATE_VARIANCE = 0.03;
	private static final double MAX_WIN_RATE = WIN_RATE + WIN_RATE_VARIANCE;
	private static final double MIN_WIN_RATE = WIN_RATE - WIN_RATE_VARIANCE;
	
	private Player player;
	private Dice d1;
	private Dice d2;
	private Dice d3;
	private Game game;
	
	@Before
	public void setUp() throws Exception {
		//Create dice
		d1 = new Dice();
		d2 = new Dice();
		d3 = new Dice();
		
		//Create player
		player = new Player(NAME, STARTING_BALANCE);
		player.setLimit(BETTING_LIMIT);
		
		//Create game
		game = new Game(d1, d2, d3);
	}

	@After
	public void tearDown() throws Exception {
		player = null;
		d1 = null;
		d2 = null;
		d3 = null;
		game = null;
	}

	@Test
	public void test() {
		
		//Play round
		int winCount = 0;
		int lossCount = 0;
		
		
		//Assume 1000 rounds is sufficient to get accurate win/loss ratio
		int numRounds = 1000;
		
		for (int round = 1; round <= numRounds; round++) { 
	        	
			if (player.balanceExceedsLimitBy(BET_AMOUNT)) {
				
	        	int winnings = game.playRound(player, PICK_CROWN, BET_AMOUNT);
	            List<DiceValue> cdv = game.getDiceValues();
	            
	            System.out.printf("Rolled %s, %s, %s\n",
	            		cdv.get(0), cdv.get(1), cdv.get(2));
	            
	            if (winnings > 0) {
	                System.out.printf("%s won %d, balance now %d\n\n",
	                		player.getName(), winnings, player.getBalance());
	                winCount++;
	            }
	            else {
	                System.out.printf("%s lost, balance now %d\n\n",
	                		player.getName(), player.getBalance());
	                lossCount++;
	            }
			}
		}
		
		double winRate = (winCount * 1.0)/(winCount + lossCount*1.0);
		assertTrue(String.format("Win loss ratio too great: %.2f", winRate), winRate <= MAX_WIN_RATE);
		assertTrue(String.format("Win loss ratio too little: %.2f", winRate), winRate >= MIN_WIN_RATE);
		
		//Check result
		
	}
	

}
