package crownandanchor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import crownandanchor.Dice;
import crownandanchor.DiceValue;

public class TestBug4DiceValues {
	

	private Dice d1;
	
	@Before
	public void setUp() throws Exception {
		//Create dice
		d1 = new Dice();
		
	}

	@After
	public void tearDown() throws Exception {

		d1 = null;

	}

	@Test
	public void testRollAllValues() {

		List<DiceValue> results = new ArrayList<DiceValue>();
		
		int numRolls = 5000;
		
		for (int roll = 0; roll < numRolls; roll++) {
			DiceValue rolled = d1.roll();
			System.out.printf("Roll #%d, rolled %s\n", roll, rolled.toString());
			results.add(rolled);
		}
		
		//For 1500 rolls, results should contain at least one of each value
		for (DiceValue value : DiceValue.values()) {
			assertTrue(String.format("Results do not contain: %s", value.toString()), results.contains(value));
		}
	}
	
	@Test
	public void testGetValue() {
		
		int numRolls = 100;
		
		for (int roll = 0; roll < numRolls; roll++) {
			DiceValue result = d1.roll();
			System.out.printf("Rolled %s, GetValue(): %s\n", result.toString(), d1.getValue().toString());
			assertEquals(result, d1.getValue());
		}
	}
	

}
