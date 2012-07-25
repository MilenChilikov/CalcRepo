package calc.project;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class App 
{
	public static boolean  isNumber(char ch){
		boolean flag = false;
		switch(ch){
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '0':
				flag = true;
				break;
		}
		
		return flag;
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
	
	public static boolean isP1(char ch){
		if(ch == '(')
			return true;
		else 
			return false;
	}
	
	public static boolean isP2(char ch){
		if(ch == ')')
			return true;
		else 
			return false;
	}
	
	public static Integer convertToNumber(String str)	{
		Integer num = 0;
		int m = 0;
		
		for(int i = 0; i < str.length(); ++i)	{
			switch(str.charAt(i))	{
				case '1':
					m = 1;
					break;
				case '2':
					m = 2;
					break;
				case '3':
					m = 3;
					break;
				case '4':
					m = 4;
					break;
				case '5':
					m = 5;
					break;
				case '6':
					m = 6;
					break;
				case '7':
					m = 7;
					break;
				case '8':
					m = 8;
					break;
				case '9':
					m = 9;
					break;
				case '0':
					m = 0;
					break;
			}
			num *= 10;
			num += m;
		}
		
		return num;
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
	
	public static Integer getRes(Integer a, Integer b, char c)	{
		
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
	
	
    public static void main( String[] args )
    {
    	Scanner sc = new Scanner(System.in);
    	
    	char ch;
    	int flag = 1, countP = 0;
    	Integer numKey = 1;
 
        String str, temp = "";
        Map<Integer, Integer> numbers = new HashMap<Integer, Integer>();
        Map<Integer, Integer> operations = new HashMap<Integer, Integer>();
        
        str = sc.next();
          
        
        char oper[] = new char[str.length()/2 + 1];
        
        for(int i = 0; i < str.length(); ++i)	{
        	ch = str.charAt(i);
        	
        	if(isNumber(ch) || isOperation(ch) || isParenthesis(ch))	{
        		if(flag == 1 && isNumber(ch))	{
        			
        			if(i  != str.length()-1 && isNumber(str.charAt(i+1)))	{
        				temp += ch;
        				continue;
        			}
        			
        			temp += ch;
        			numbers.put(numKey, convertToNumber(temp));
        			temp = "";
        			flag = 0;
        		}
        		
        		else if(flag == 1 && isP1(ch))	{
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
        		
        		else if(flag == 0 && isP2(ch))	{
        			
        			--countP;
        			if(countP < 0)	{
        				System.out.println("Invalid input.");
                		return;
        			}
        		}
        		
        		else {
        			System.out.println("Invalid input.");
            		return;
        		}
        	}
        	
        	else	{
        		System.out.println("Invalid input.");
        		return;
        	}
        }
        
        if(flag == 1)	{
        	System.out.println("Invalid input.");
    		return;
        }
        
        if(countP != 0)	{
        	System.out.println("Invalid input.");
        	return;
        }
       	
        
        Iterator<Integer> it = operations.keySet().iterator();
        int privKey, currKey, nextKey, len = numbers.size();
        
        privKey = currKey = (Integer) it.next();
        
        if(it.hasNext())
        	nextKey = (Integer) it.next();
        else	{
        	System.out.println("The result is " + getRes(numbers.get(currKey), numbers.get(currKey+1), oper[currKey]) + " :)");
        	return;
        }
     
        while(!operations.isEmpty())	{
        
        	while(it.hasNext())	{
        		
        		if(operations.get(privKey) <= operations.get(currKey) && operations.get(currKey) >= operations.get(nextKey))	{
        			
        			System.out.println("Compute " + numbers.get(currKey) + " " + oper[currKey] + " " + numbers.get(nextKey) + 
        					" = " + getRes(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));
        			
        			numbers.put(nextKey, getRes(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));	
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
    			
    			System.out.println("Compute " + numbers.get(currKey) + " " + oper[currKey] + " " + numbers.get(len) + 
    					" = " + getRes(numbers.get(currKey), numbers.get(len), oper[currKey]));
    			
    			System.out.println("The result is " + getRes(numbers.get(currKey), numbers.get(len), oper[currKey]) + " :)");
            	return;
        	}
        	
        	else if(operations.get(currKey) >= operations.get(nextKey) && operations.get(currKey) >= operations.get(privKey))	{ 
        		
       			System.out.println("Compute " + numbers.get(currKey) + " " + oper[currKey] + " " + numbers.get(nextKey) + 
       					" = " + getRes(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));
       			
       			numbers.put(nextKey, getRes(numbers.get(currKey), numbers.get(nextKey), oper[currKey]));
       			numbers.remove(currKey);
       			operations.remove(currKey);
       			
       			it = operations.keySet().iterator();
       			privKey = currKey = it.next();
       			
       			if(operations.size() != 1)
       				nextKey = it.next();
       		}

        	else if(operations.get(nextKey) >= operations.get(currKey))	{
        		
        		System.out.println("Compute " + numbers.get(nextKey) + " " + oper[nextKey] + " " + numbers.get(len) + 
    					" = " + getRes(numbers.get(nextKey), numbers.get(len), oper[nextKey]));
        		
        		numbers.put(len, getRes(numbers.get(nextKey), numbers.get(len), oper[nextKey]));
        		numbers.remove(nextKey);
        		operations.remove(nextKey);
        		
        		it = operations.keySet().iterator();
        		privKey = currKey = it.next();
        		
        		if(operations.size() != 1)
       				nextKey = it.next();
        	}
        }
    }
}