

import java.util.Random;
import java.util.Scanner;

public class randomnumber{
 {

		
	}
	public static int randomNum(int min, int max)
	{
		Random rand =new Random();
		int d= rand.nextInt(max- min);
		return d;
	}
}





