package calc.project;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class App 
{
	public Map<Integer, Integer> numbers = new HashMap<Integer, Integer>();
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
	
	public static boolean  isOperation(char ch){
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
	
	public static boolean  isParenthesis(char ch){
		boolean flag = false;
		switch(ch){
			case '(':
			case ')':
				flag = true;
				break;
		}
		
		return flag;
	}
	
	public static boolean isOpenParenthesis(char ch){
		if(ch == '(')
			return true;
		else 
			return false;
	}
	
	public static boolean isCloseParenthesis(char ch){
		if(ch == ')')
			return true;
		else 
			return false;
	}
		
	
	public static Integer getPriority(char ch){

		switch(ch)	{
			case '+':
			case '-':
				return 1;
			default:
				return 2;
		}
	}
	
	public static Integer getResultOf(Integer a, Integer b, char c)	{
		
		switch(c)	{
			case '+':
				return a + b;
			case '-':
				return a - b;
			case '*':
				return a * b;
			default:
				return  a / b;
		}
	}
	
	private Integer input(String str)	{
		
		char ch;
    	int flag = 1, countP = 0;
    	Integer numKey = 1;
    	String temp = "";
    	
    	oper = new char[str.length()/2 + 1];
        
        for(int i = 0; i < str.length(); ++i)	{
        	ch = str.charAt(i);
        	
        	if(Character.isDigit(ch) || isOperation(ch) || isParenthesis(ch))	{
        		if(flag == 1 && Character.isDigit(ch))	{
        			
        			if(i  != str.length()-1 && Character.isDigit(str.charAt(i+1)))	{
        				temp += ch;
        				continue;
        			}
        			
        			temp += ch;
        			numbers.put(numKey, Integer.parseInt(temp));
        			temp = "";
        			flag = 0;
        		}
        		
        		else if(flag == 1 && isOpenParenthesis(ch))	{
        			++countP;
        		}
        		
        		else if(flag == 0 && isOperation(ch))	{
        			
        			Integer prior = 0;
        			prior = getPriority(ch) + 3*countP;
        			operations.put(numKey, prior);
        			flag = 1;
        			oper[numKey] = ch;
        			++numKey;
        		}
        		
        		else if(flag == 0 && isCloseParenthesis(ch))	{
        			
        			--countP;
        			if(countP < 0)	{
        				
        				throw new IllegalArgumentException("Invalid input.");
        			}
        		}
        		
        		else {
        			
        			throw new IllegalArgumentException("Invalid input.");
        		}
        	}
        	
        	else	{
        		
        		throw new IllegalArgumentException("Invalid input.");
        	}
        }
        
        if(flag == 1)	{
        	
        	throw new IllegalArgumentException("Invalid input.");
        }
        
        if(countP != 0)	{
        	
        	throw new IllegalArgumentException("Invalid input.");
        }
		
		return 0;
	}
	
	private int parse(){
		
        it = operations.keySet().iterator();
        int privKey, currKey, nextKey, len = numbers.size(), result = 0;
        
        privKey = currKey = (Integer) it.next();
        
        if(it.hasNext())
        	nextKey = (Integer) it.next();
        else	{
        	
        	if(numbers.get(currKey+1) == 0 && oper[currKey] == '/')	{
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
        			
        			if(numbers.get(nextKey) == 0 && oper[currKey] == '/')	{
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
    			
    			if(numbers.get(len) == 0 && oper[currKey] == '/')	{
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
        		
        		if(numbers.get(nextKey) == 0 && oper[currKey] == '/')	{
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
        		
        		if(numbers.get(len) == 0 && oper[nextKey] == '/')	{
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
	
	public Integer function(String str)	{
		input(str);
		return parse();
	}
	
	
    public static void main( String[] args )
    {
    	int result;
    	boolean flag;
    	
    	Scanner sc = new Scanner(System.in);
        String str;
        
        
        System.out.println("Show steps true/false: ");
        flag = sc.nextBoolean();
        
        System.out.println("Enter expression: ");
        str = sc.next();
        
        App myApp = new App(flag);
          
        try	{
        	
        result = myApp.function(str);
        System.out.println("The result is " + result + ".");
        
        }	catch	(IllegalArgumentException exc)	{
        		System.out.println(exc.getMessage());
        		
        }	catch(ArithmeticException exc)	{
        		System.out.println(exc.getMessage());
        }
    }
}