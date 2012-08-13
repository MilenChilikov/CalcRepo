package calc.project;

import java.math.BigDecimal;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args )
    {
    	Scanner sc = new Scanner(System.in);
    	BigDecimal result = BigDecimal.ZERO;
    	boolean flag = true;
    	String str = sc.next();
   
        App myApp = new App(flag);
          
        try	{
        	
        result = myApp.function(str);
        System.out.println("The result is " + result);
        
        }	catch	(IllegalArgumentException exc)	{
        		System.out.println(exc.getMessage());
        		
        }	catch(ArithmeticException exc)	{
        		System.out.println(exc.getMessage());
        }
    }
}
