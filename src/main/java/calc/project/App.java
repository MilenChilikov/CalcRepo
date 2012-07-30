package calc.project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class App 
{
	public Map<Integer, BigDecimal> numbers = new HashMap<Integer, BigDecimal>();
	public Map<Integer, Integer> operations = new HashMap<Integer, Integer>();
	public Iterator<Integer> it;
	public char oper[];
	private boolean showSteps;
	
	App()	{
		showSteps = false;
	}
	
	App(boolean flag)	{
		showSteps = flag;
	}
	
	public boolean get()	{
		return showSteps;
	}
	
	public void set(boolean b)	{
		showSteps = b;
	}
	
	public boolean isShowSteps()	{
		return showSteps;
	}
	
	private static boolean  isOperation(char ch){
		boolean flag = false;
		switch(ch){
			case '+':
			case '-':
			case '*':
			case '/':
				flag = true;
				break;
		}
		
		return flag;
	}
	
	private static boolean isDot(char ch) {
		if(ch == '.')
			return true;
		else
			return false;
	}
	
	private static boolean  isParenthesis(char ch){
		boolean flag = false;
		switch(ch){
			case '(':
			case ')':
				flag = true;
				break;
		}
		
		return flag;
	}
	
	private static boolean isOpenParenthesis(char ch){
		if(ch == '(')
			return true;
		else 
			return false;
	}
	
	private static boolean isCloseParenthesis(char ch){
		if(ch == ')')
			return true;
		else 
			return false;
	}
		
	
	private static Integer getPriority(char ch){

		switch(ch)	{
			case '+':
			case '-':
				return 1;
			default:
				return 2;
		}
	}
	
	private static BigDecimal getResultOf(BigDecimal a, BigDecimal b, char c)	{
		
		switch(c)	{
			case '+':
				return a.add(b);
			case '-':
				return a.subtract(b);
			case '*':
				return a.multiply(b);
			default:
				return  a.divide(b, 20, RoundingMode.HALF_UP);
		}
	}
	
	private void input(String str)	{
		
		char ch;
    	int flag = 1, countP = 0;
    	boolean openP = false, signFlag = false,  dotFlag = false;
    	
    	Integer numKey = 1;
    	String temp = "";
    	
    	oper = new char[str.length()/2 + 1];
   
        for(int i = 0; i < str.length(); ++i)	{
        	ch = str.charAt(i);
        	
        	if(Character.isDigit(ch) || isOperation(ch) || isParenthesis(ch) || isDot(ch))	{
        		if(isCloseParenthesis(ch))	{
        			if(flag == 1 && openP && signFlag)	{
           				numbers.put(numKey, new BigDecimal(temp).negate());
        				temp = "";
        				flag = 0;
        				dotFlag = openP = signFlag = false;
        			}
          			
        				--countP;
        			
        			if(countP < 0)	{
  
        				throw new IllegalArgumentException("Position " + (i+1) + ": Incorrect input of Close Parenthesis.");
        			}
        			
        		}
        		else if(flag == 1 && openP && signFlag && (isDot(ch) || Character.isDigit(ch))){
        			if(isDot(ch))	{
        				if(dotFlag)	{
            				throw new IllegalArgumentException("Position " + (i+1) + ": Incorrect input of Dot.");
            			}
            			dotFlag = true;
            			temp += ch;
        			}
        			else	{
        				
            			temp += ch;
        			}
        		}
        		else if(flag == 1 && openP && ch == '-')	{
        			if(signFlag)	{
        				throw new IllegalArgumentException("Incorrect input. Expect number.");
        			}
        			signFlag = true;
        		}
        		else if(flag == 1 && Character.isDigit(ch))	{
        			if(i  != str.length()-1 && (Character.isDigit(str.charAt(i+1)) || isDot(str.charAt(i+1))))	{
        				temp += ch;
        				continue;
        			}
        			
        			temp += ch;
        			numbers.put(numKey, new BigDecimal(temp));
        			temp = "";
        			flag = 0;
        			dotFlag = openP = signFlag = false;
        		}
        		
        		else if(flag == 1 && isDot(ch))	{
        			if(dotFlag)	{
        				throw new IllegalArgumentException("Position " + (i+1) + ": Incorrect input of Dot. ");
        			}
        			dotFlag = true;
        			temp += ch;
        		}
        		
        		else if(flag == 1 && isOpenParenthesis(ch))	{
        			++countP;
        			openP = true;
        		}
        		
        		else if(flag == 0 && isOperation(ch))	{
        			
        			Integer prior = 0;
        			prior = getPriority(ch) + 3*countP;
        			operations.put(numKey, prior);
        			flag = 1;
        			oper[numKey] = ch;
        			dotFlag = openP = signFlag = false;
        			++numKey;
        		}
        		
        		else {
        			if(flag == 0)
        				throw new IllegalArgumentException("Position " + (i+1) + ": Expect operation.");
        			else if (flag == 1)
        				throw new IllegalArgumentException("Position " + (i+1) + ": Expect number.");
        			else
        				throw new IllegalArgumentException("Invalid input.");
        		}
        	}
        	
        	else	{
        		
        		throw new IllegalArgumentException(" Input correct expression. For example  3*4-2");
        	}
        }
        
        if(countP != 0)	{
        	
        	throw new IllegalArgumentException("You haven't close all Parentheses.");
        }
        
        if(flag == 1)	{
        	
        	throw new IllegalArgumentException("You haven't input number after last operation.");
        }
        
		
		return ;
        
	}
	

	private BigDecimal parse(){
		
        it = operations.keySet().iterator();
        int privKey, currKey, nextKey, len = numbers.size();
        BigDecimal result = null;
        
        privKey = currKey = (Integer) it.next();
        
        if(it.hasNext())
        	nextKey = (Integer) it.next();
        else	{
        	
        	if(numbers.get(currKey+1).doubleValue() == 0 && oper[currKey] == '/')	{
        		System.out.println("Trying to compute " + numbers.get(currKey) + oper[currKey] + numbers.get(currKey+1));
        		throw new ArithmeticException("Can't divide by zero.");
        	}
        	
        	if(isShowSteps())	{
				System.out.println("Compute " + numbers.get(currKey) + " " + oper[currKey] + " " + numbers.get(currKey+1) + 
					" = " + getResultOf(numbers.get(currKey), numbers.get(currKey+1), oper[currKey]));
			}
        	
        	result = getResultOf(numbers.get(currKey), numbers.get(currKey+1), oper[currKey]);
        	
        	return result;
        }
     
        while(!operations.isEmpty())	{
        
        	while(it.hasNext())	{
        		
        		if(operations.get(privKey) <= operations.get(currKey) && operations.get(currKey) >= operations.get(nextKey))	{
        			
        			if(numbers.get(nextKey).doubleValue() == 0 && oper[currKey] == '/')	{
        				System.out.println("Trying to compute " + numbers.get(currKey) + oper[currKey] + numbers.get(nextKey));
        				throw new ArithmeticException("Can't divide by zero.");
                	}
        			
        			if(isShowSteps())	{
        				System.out.println("Compute " + numbers.get(currKey) + " " + oper[currKey] + " " + numbers.get(nextKey) + 
        					" = " + getResultOf(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));
        			}
        				
        			numbers.put(nextKey, getResultOf(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));	
        			numbers.remove(currKey);
        			operations.remove(currKey);
        			
        			it = operations.keySet().iterator();
        			privKey = currKey = it.next();
                    nextKey = (Integer) it.next();
        		}
        		
        		else 	{
        			privKey = currKey = nextKey;
        			nextKey = (Integer) it.next();
        		}
        	}
        	
        	if(operations.size() == 1)	{
        		
        		it = operations.keySet().iterator();
    			currKey = it.next();
    			
    			if(numbers.get(len).doubleValue() == 0 && oper[currKey] == '/')	{
    				System.out.println("Trying to compute " + numbers.get(currKey) + oper[currKey] + numbers.get(len));
    				throw new ArithmeticException("Can't divide by zero.");
            	}
    			
    			if(isShowSteps())	{
    				System.out.println("Compute " + numbers.get(currKey) + " " + oper[currKey] + " " + numbers.get(len) + 
    					" = " + getResultOf(numbers.get(currKey), numbers.get(len), oper[currKey]));
    			}
    				
            	result = getResultOf(numbers.get(currKey), numbers.get(len), oper[currKey]);
            	
            	return result;
        	}
        	
        	else if(operations.get(currKey) >= operations.get(nextKey) && operations.get(currKey) >= operations.get(privKey))	{ 
        		
        		if(numbers.get(nextKey).doubleValue() == 0 && oper[currKey] == '/')	{
        			System.out.println("Trying to compute " + numbers.get(currKey) + oper[currKey] + numbers.get(currKey));
        			throw new ArithmeticException("Can't divide by zero.");
            	}
        		
        		if(isShowSteps())	{
        			System.out.println("Compute " + numbers.get(currKey) + " " + oper[currKey] + " " + numbers.get(nextKey) + 
       					" = " + getResultOf(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));
        		}
        			
       			numbers.put(nextKey, getResultOf(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));
       			numbers.remove(currKey);
       			operations.remove(currKey);
       			
       			it = operations.keySet().iterator();
       			privKey = currKey = it.next();
       			
       			if(operations.size() != 1)
       				nextKey = it.next();
       		}

        	else if(operations.get(nextKey) >= operations.get(currKey))	{
        		
        		if(numbers.get(len).doubleValue() == 0 && oper[nextKey] == '/')	{
        			System.out.println("Trying to compute " + numbers.get(nextKey) + oper[nextKey] + numbers.get(len));
            		throw new ArithmeticException("Can't divide by zero.");
            		
            	}
        		
        		if(isShowSteps())	{
        			System.out.println("Compute " + numbers.get(nextKey) + " " + oper[nextKey] + " " + numbers.get(len) + 
    					" = " + getResultOf(numbers.get(nextKey), numbers.get(len), oper[nextKey]));
        		}
        			
        		numbers.put(len, getResultOf(numbers.get(nextKey), numbers.get(len), oper[nextKey]));
        		numbers.remove(nextKey);
        		operations.remove(nextKey);
        		
        		it = operations.keySet().iterator();
        		privKey = currKey = it.next();
        		
        		if(operations.size() != 1)
       				nextKey = it.next();
        	}
        }
        
        return result;	
	}
	
	private BigDecimal f(BigDecimal b)	{
		
		String str = b.toString();
		
		if(str.indexOf('.') == -1)
			return b;
		if(b.subtract(b.setScale(0, BigDecimal.ROUND_FLOOR)) == new BigDecimal(0))
				return b.setScale(0, BigDecimal.ROUND_FLOOR);
	
		int len = str.length()-1 - str.indexOf('.');

		for(int i = str.length()-1; i >= 0; --i)	{
			if(str.charAt(i) == '0')
				--len;
			else 
				break;
		}
		
		b = b.setScale(len, BigDecimal.ROUND_HALF_UP);
		
		return b;
	}
	
	private BigDecimal function(String str)	{
		input(str);
		return parse();
	}
	
	private void show()	{
		it = numbers.keySet().iterator();
		System.out.println("Numbers: ");
		
		while(it.hasNext()){
			Integer i = it.next();
			System.out.println("Key " + i + ", Value " + numbers.get(i));
		}
		System.out.println("Operators ");
		it = operations.keySet().iterator();
		while(it.hasNext()){
			Integer i = it.next();
			System.out.println("Key " + i + ", operation " + oper[i] + ", Priority " + numbers.get(i));
		}
	}
	
	
    public static void main( String[] args )
    {
    	BigDecimal result;
    	boolean flag = true;
    	
    	Scanner sc = new Scanner(System.in);
        String str;
        
        
//        System.out.println("Show steps true/false: ");
//        flag = sc.nextBoolean();
        
        System.out.println("Enter expression: ");
        str = sc.next();
        
        App myApp = new App(flag);
          
        try	{
        	
        result = myApp.function(str);
        System.out.println("The result is " + myApp.f(result));
  
        
        }	catch	(IllegalArgumentException exc)	{
        		System.out.println(exc.getMessage());
        		
        }	catch(ArithmeticException exc)	{
        		System.out.println(exc.getMessage());
        }
    }
}