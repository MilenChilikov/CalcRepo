package calc.project;

import java.util.HashMap;
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
	
	
    public static void main( String[] args )
    {
    	Scanner sc = new Scanner(System.in);
    	
    	char ch;
    	int flag = 1, countP = 0, numKey = 1;
 
        String str;
        Map numbers = new HashMap();
        Map operations = new HashMap();
        
        str = sc.next();
        char oper[] = new char[str.length()/2 + 1];
        
        for(int i = 0; i < str.length(); ++i)	{
        	ch = str.charAt(i);
        	
        	if(isNumber(ch) || isOperation(ch) || isParenthesis(ch))	{
        		if(flag == 1 && isNumber(ch))	{
        			
        		}
        		else if(flag == 1 && isP1(ch))	{
        			countP++;
        		}
        		else if(flag == 0 && isOperation(ch))	{
        			
        		}
        		else if(flag == 0 && isP2(ch))	{
        			countP--;
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
        
        
        /*System.out.println(convertToNumber(str));
        System.out.println(isNumber(str.charAt(0)));
        System.out.println(isOperation(str.charAt(0)));
        System.out.println(isParenthesis(str.charAt(0)));*/
    }
}
