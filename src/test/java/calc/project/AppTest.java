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
	public void fTest()	{
		assertEquals( new BigDecimal("12.345"), app.f( new BigDecimal("12.34500")));
		assertEquals( new BigDecimal("0"), app.f( new BigDecimal("0.0000")));
		assertEquals( new BigDecimal("1200"), app.f( new BigDecimal("1200.0")));
		assertEquals( new BigDecimal("1.05"), app.f( new BigDecimal("1.0500")));
	}
	
	@Test
	public void functionTest()	{
		assertEquals(new BigDecimal("3"), app.function("(1+3)/2+1"));
	}
	
	@Test
	public void getTest()	{
		assertFalse(app.get());
		app.set(true);
		assertTrue(app.get());
	}
    
    @Test
    public void getPriorityTest()	{
    	assertEquals((Integer) 1, app.getPriority('+'));   	
    	assertEquals((Integer) 1, app.getPriority('-'));
    	assertEquals((Integer) 2, app.getPriority('*'));
    	assertEquals((Integer) 2, app.getPriority('/'));
    }
    
    @Test
    public void getPriorityTestException()	{
    
    }
    
    @Test
    public void getResultOfTest()	{
    	assertEquals(BigDecimal.ONE, app.getResultOf(new BigDecimal("1"), new BigDecimal("0"), '+'));
    	assertEquals(new BigDecimal("-6"), app.getResultOf(new BigDecimal("-4"), new BigDecimal("2"), '-'));
    	assertEquals(BigDecimal.TEN, app.getResultOf(new BigDecimal("4"), new BigDecimal("2.5"), '*'));
    	assertEquals(new BigDecimal("6.5"), app.getResultOf(new BigDecimal("13"), new BigDecimal("2"), '/'));
    }
    
    @Test
    public void getResultOfTestException()	{
    
    }
    
    @Test
    public void inputTest()	{
    	app.input("((1+7)*6.125-24/4)*2");
    	Map<Integer, BigDecimal> expectedMapOfNumbers = new HashMap<Integer, BigDecimal>();
    	Map<Integer, Integer> expectedMapOfoperations = new HashMap<Integer, Integer>();
    	Iterator<Integer> it;
    	char expected[] = new char[app.oper.length];
    	expected[1] = '+';
    	expected[2] = '*'; 
    	expected[3] = '-';
    	expected[4] = '/';
    	expected[5] = '*';
    	assertTrue(Arrays.equals(expected, app.oper));
    	assertTrue(compareMaps(expectedMapOfNumbers, app.numbers));
    	assertTrue(compareMaps(expectedMapOfoperations, app.operations));
    }


	private boolean compareMaps(Map expectedMap,
			Map actual) {
		return false;
	}



  }