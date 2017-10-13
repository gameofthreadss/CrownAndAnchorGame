package crownandanchor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import crownandanchor.DiceValue;

public class TestDiceValue {
	
	static final int NUM_ROLLS = 500;
	static final double STANDARD_PROB = 1.0 / DiceValue.values().length;
	static final double VARIATION = 0.03;
	static final double MAX = STANDARD_PROB + VARIATION;
	static final double MIN = STANDARD_PROB - VARIATION;
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		List<DiceValue> values = new ArrayList<DiceValue>();
		
		for (int i = 0; i < NUM_ROLLS; i++) {
			values.add(DiceValue.getRandom());
		}
		
		Map<DiceValue, Integer> resultsMap = new HashMap<>();
		
		for (DiceValue value : values) {
			Integer num = resultsMap.get(value);
			if (num == null) {
				num = 1;
			} else {
				num++;
			}
			resultsMap.put(value, num);
		}
		
		for (DiceValue value : resultsMap.keySet()) {
			Integer occurences = resultsMap.get(value);
			double fraction = (occurences * 1.0) / (NUM_ROLLS * 1.0);
			assertTrue(String.format("%s ratio too great: %.2f", value.toString(), fraction), fraction <= MAX);
			assertTrue(String.format("%s ratio too little: %.2f", value.toString(), fraction), fraction >= MIN);
		}
	}

}
