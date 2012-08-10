package calc.project;

import java.math.BigDecimal;

public class Calculator {

    public static void main(String[] args )
    {
    	//Scanner sc = new Scanner(System.in);
    	BigDecimal result = new BigDecimal(0);
    	boolean flag = true;
        String str = "((1+7)*6.125-24/4)*2";
   
        App myApp = new App(flag);
          
        try	{
        	
        result = myApp.function(str);
        System.out.println("The result is " + result);
        
        }	catch	(IllegalArgumentException exc)	{
        		System.out.println(exc.getMessage());
        		
        }	catch(ArithmeticException exc)	{
        		System.out.println(exc.getMessage());
        }
        myApp.input("((1+7)*6.125-24/4)*2");
        //myApp.show();
        System.out.println(myApp.oper.length);
        myApp.it = myApp.operations.keySet().iterator();	
        //Integer i = 0;
    	/*while(i < myApp.oper.length){
    		
    		System.out.println("Position " + i + " with operation " + myApp.oper[i] + ".");
    		i++;
        }*/
       // myApp.show();
    	myApp = null;
    }
}
