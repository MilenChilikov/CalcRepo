package calc.project;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest {
	
	private App app;
	
	@Before
	public void before()	{
		app = new App();
	}

	@After
	public void after()	{
		app = null;
	}
	
	@Test
	public void roundBigDecimalTest()	{
		assertEquals( new BigDecimal(12.345), app.roundBigDecimal( new BigDecimal(12.34500)));
		assertEquals(BigDecimal.ZERO, app.roundBigDecimal( new BigDecimal(0.0000)));
		assertEquals( new BigDecimal(1200), app.roundBigDecimal( new BigDecimal(1200.0)));
		assertEquals( new BigDecimal(1.05), app.roundBigDecimal( new BigDecimal(1.0500)));
	}
	
	@Test
	public void inputAndParseTest1()	{
		assertEquals(new BigDecimal(3), app.inputAndParse("(1+3)/2+1+(0*4)/12"));
	}
	
	@Test
	public void inputAndParseTest2()	{
		assertEquals(new BigDecimal(0), app.inputAndParse("((6+4)/2-1.5*(10-6))+1"));
	}
	
	@Test
	public void getShowStepsTest()	{
		assertFalse(app.getShowSteps());
		app.setShowSteps(true);
		assertTrue(app.getShowSteps());
	}
    
    @Test
    public void getPriorityTest()	{
    	try	{
    		assertEquals((Integer) 1, app.getPriority('+'));   	
    		assertEquals((Integer) 1, app.getPriority('-'));
    		assertEquals((Integer) 2, app.getPriority('*'));
    		assertEquals((Integer) 2, app.getPriority('/'));
    	}	catch(IllegalArgumentException exc)	{
    			fail("The CharValue is illegal.");
    	}
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getPriorityTestException()	throws IllegalArgumentException	{
    	app.getPriority('=');
    }
    
    @Test
    public void getTwoNumbersAndOperationAndDoOperationTest()	{
    	try	{
    		assertEquals(BigDecimal.ONE, app.getTwoNumbersAndOperationAndDoOperation(BigDecimal.ONE, BigDecimal.ZERO, '+'));
    		assertEquals(new BigDecimal(-6), app.getTwoNumbersAndOperationAndDoOperation(new BigDecimal(-4), new BigDecimal(2), '-'));
    		assertEquals(BigDecimal.TEN, app.getTwoNumbersAndOperationAndDoOperation(new BigDecimal(4), new BigDecimal(2.5), '*'));
    		assertEquals(new BigDecimal(6.5), app.getTwoNumbersAndOperationAndDoOperation(new BigDecimal(13), new BigDecimal(2), '/'));
    	}	catch(ArithmeticException exc)	{
    			fail("Cannot divide by 0.");
    	}	catch(IllegalArgumentException exc)	{
    			fail("The CharValue is illegal.");
    	}
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getTwoNumbersAndOperationAndDoOperationTestException1()	throws IllegalArgumentException, ArithmeticException	{
    	app.getTwoNumbersAndOperationAndDoOperation(BigDecimal.TEN, BigDecimal.ONE, '=');
    }
    
    @Test(expected = ArithmeticException.class)
    public void getTwoNumbersAndOperationAndDoOperationTestException2()	throws IllegalArgumentException, ArithmeticException	{
    	app.getTwoNumbersAndOperationAndDoOperation(BigDecimal.TEN, BigDecimal.ZERO, '/');
    }
    
    @Test
    public void inputTest()	{
    	app.input("((1+7)*6.125-24/4)*2");
    	Map<Integer, BigDecimal> expectedMapOfNumbers = new HashMap<Integer, BigDecimal>();
    	Map<Integer, Integer> expectedMapOfOperations = new HashMap<Integer, Integer>();
    	Integer key = 1;
    	char expected[] = new char[app.oper.length];
    	
    	expected[1] = '+';
    	expected[2] = '*'; 
    	expected[3] = '-';
    	expected[4] = '/';
    	expected[5] = '*';
    	
    	expectedMapOfNumbers.put(key, new BigDecimal(1));
    	expectedMapOfNumbers.put(++key, new BigDecimal(7));
    	expectedMapOfNumbers.put(++key, new BigDecimal(6.125));
    	expectedMapOfNumbers.put(++key, new BigDecimal(24));
    	expectedMapOfNumbers.put(++key, new BigDecimal(4));
    	expectedMapOfNumbers.put(++key, new BigDecimal(2));
    	
    	expectedMapOfOperations.put(key = 1, 7);
    	expectedMapOfOperations.put(++key, 5);
    	expectedMapOfOperations.put(++key, 4);
    	expectedMapOfOperations.put(++key, 5);
    	expectedMapOfOperations.put(++key, 2);
    	
    	assertTrue(Arrays.equals(expected, app.oper));
    	assertTrue(compareMaps(expectedMapOfNumbers, app.numbers));
    	assertTrue(compareMaps(expectedMapOfOperations, app.operations));
    	
    	expected = null;
    	expectedMapOfNumbers = null;
    	expectedMapOfOperations = null;
    }


	private <T> boolean compareMaps(Map<Integer, T> expectedMap, Map<Integer, T> actual) {
		Iterator<Integer> it1, it2;
		it1 = expectedMap.keySet().iterator();
		it2 = actual.keySet().iterator();
		boolean result = true;
		
		while(it1.hasNext() && it2.hasNext())	{
			Integer key = (Integer) it1.next();
			if(!expectedMap.get(key).equals(actual.get(key)))
				result = false;
		}
		return result;
	}

	@Test
	public void isCloseParenthesisTest()	{
		assertTrue(app.isCloseParenthesis(')'));
		assertFalse(app.isCloseParenthesis('('));
		assertFalse(app.isCloseParenthesis('+'));
		assertFalse(app.isCloseParenthesis('6'));
		assertFalse(app.isCloseParenthesis('b'));
	}

	@Test
	public void isDot()	{
		assertTrue(app.isDot('.'));
		assertFalse(app.isDot(','));
		assertFalse(app.isDot('-'));
		assertFalse(app.isDot('8'));
		assertFalse(app.isDot('s'));
	}
	
	@Test
	public void isOpenParenthesisTest()	{
		assertTrue(app.isOpenParenthesis('('));
		assertFalse(app.isOpenParenthesis(')'));
		assertFalse(app.isOpenParenthesis('*'));
		assertFalse(app.isOpenParenthesis('e'));
		assertFalse(app.isOpenParenthesis('1'));
	}
	
	@Test
	public void  isOperationTest()	{
		assertTrue(app.isOperation('*'));
		assertTrue(app.isOperation('/'));
		assertTrue(app.isOperation('+'));
		assertTrue(app.isOperation('-'));
		assertFalse(app.isOperation('='));
		assertFalse(app.isOperation('t'));
		assertFalse(app.isOperation('7'));
	}
	
	@Test
	public void  isParenthesisTest()	{
		assertTrue(app.isParenthesis('('));
		assertTrue(app.isParenthesis(')'));
		assertFalse(app.isParenthesis('.'));
		assertFalse(app.isParenthesis('/'));
		assertFalse(app.isParenthesis('0'));
		assertFalse(app.isParenthesis('p'));
	}
	
	@Test
	public void isShowStepsTest()	{
		app.setShowSteps(true);
		assertTrue(app.isShowSteps());
		app.setShowSteps(false);
		assertFalse(app.isShowSteps());
	}
	
	@Test
	public void parseAndGetResultTest()	{
		app.input("10+4-(3+2)*2.2");
		assertEquals(new BigDecimal(3), app.parseAndGetResult());
	}
	
	@Test
	public void setShowStepsTest()	{
		app.setShowSteps(false);
		assertFalse(app.getShowSteps());
		app.setShowSteps(true);
		assertTrue(app.getShowSteps());
	}
  }