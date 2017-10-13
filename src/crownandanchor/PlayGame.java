package crownandanchor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class PlayGame {
	
	private static final Map<String, DiceValue> DICE_VALUES = new HashMap<String, DiceValue>();
	static {
		DICE_VALUES.put("CR", DiceValue.CROWN);
		DICE_VALUES.put("A", DiceValue.ANCHOR);
		DICE_VALUES.put("H", DiceValue.HEART);
		DICE_VALUES.put("D", DiceValue.DIAMOND);
		DICE_VALUES.put("CL", DiceValue.CLUB);
		DICE_VALUES.put("S", DiceValue.SPADE);
	}
	
	public static void main(String[] args) throws Exception {
		
	   BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

	   System.out.println("Enter a name: \n");
	   	String name = console.readLine();
	   
	   	int balance = 0;
	   	while (balance <= 0) {
	   		System.out.println("Enter a starting balance: ");
	   		String strBalance = console.readLine();
	   		try {
	   			balance = Integer.valueOf(strBalance);
	   		} catch (NumberFormatException ex) {
	   			System.out.println("Invalid balance entered");
	   		}
	   		if (balance  < 0) {
	   			System.out.println("Invalid balance entered");
	   		}
	   	}
	   	
	   	int limit = -1;
	   	while (limit < 0) {
	   		System.out.println("Enter a betting limit greater than or equal to zero: ");
	   		String strLimit = console.readLine();
	   		try {
	   			limit = Integer.valueOf(strLimit);
	   		} catch (NumberFormatException ex) {
	   			System.out.println("Invalid limit entered");
	   		}
	   		if (limit  < 0) {
	   			System.out.println("Invalid limit entered");
	   		}
	   	}
	   	
	   

        int totalWins = 0;
        int totalLosses = 0;

        boolean endGame = false;
        while (true)
        {
            int winCount = 0;
            int loseCount = 0;
            
            Dice d1 = new Dice();
            Dice d2 = new Dice();
            Dice d3 = new Dice();

            Player player = new Player(name, balance);
            player.setLimit(limit);
            
            Game game = new Game(d1, d2, d3);
            List<DiceValue> cdv = game.getDiceValues();
            
                System.out.println(String.format("%s starts with balance %d, limit %d", 
                		player.getName(), player.getBalance(), player.getLimit()));
              
                int turn = 0;
                while (player.balanceExceedsLimit())
                {
                    int bet = -1;
            	   	while (bet <= 0) {
            	   		System.out.println("Enter a bet greater than zero (q to quit):");
            	   		String strBet = console.readLine();
            	   		if (strBet.equals("q")) {
            	   			endGame = true;
            	   			break;
            	   		}
            	   		try {
            	   			bet = Integer.valueOf(strBet);
            	   		} catch (NumberFormatException ex) {
            	   			System.out.println("Invalid bet entered");
            	   		}
            	   		if (bet < 0) {
            	   			System.out.println("Invalid bet entered");
            	   		} else if (!player.balanceExceedsLimitBy(bet)) {
            	   			System.out.println("Insufficient balance");
            	   			bet = 0;
            	   		}
            	   	}
            	   	if (endGame) break;
                	
                    turn++;      
                    
                    System.out.println("Enter your pick from the following options:\n"
                    		+ "'CR': Crown\n"
                    		+ "'A': Anchor\n"
                    		+ "'H': Heart\n"
                    		+ "'D': Diamond\n"
                    		+ "'CL': Club\n"
                    		+ "'S': Spade\n"
                    		+ "Any other key: Random\n");
                    
                    String pickStr = console.readLine();
                    DiceValue pick = DICE_VALUES.get(pickStr.trim().toUpperCase());
                    if (pick == null) {
                    	pick = DiceValue.getRandom();
                    }

                	System.out.printf("Turn %d: %s bet %d on %s\n",
                			turn, player.getName(), bet, pick); 
                	
                	int winnings = game.playRound(player, pick, bet);
                    cdv = game.getDiceValues();
                    
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
	                	loseCount++;
                    }
//                    console.readLine();
                } //while
                endGame = false;
                
                System.out.print(String.format("%d turns later.\nEnd Game", turn));
                System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));
//                console.readLine();
            
            System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f", winCount, loseCount, (float) winCount/(winCount+loseCount)));
            totalWins += winCount;
            totalLosses += loseCount;

            System.out.println("Enter to play again, 'q' to quit");
            String ans = console.readLine();
            if (ans.equals("q")) break;
        } //while true
        
        System.out.println(String.format("Overall win rate = %.1f%%", (float)(totalWins * 100) / (totalWins + totalLosses)));
	}

}
