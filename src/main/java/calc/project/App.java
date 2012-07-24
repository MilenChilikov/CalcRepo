package calc.project;

/**
 * Hello world!
 *
 */
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
	
	public static int convertToNumber(String str)	{
		int num = 0;
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
        String str = "*";
        
        System.out.println(convertToNumber(str));
        System.out.println(isNumber(str.charAt(0)));
        System.out.println(isOperation(str.charAt(0)));
        System.out.println(isParenthesis(str.charAt(0)));
    }
}
