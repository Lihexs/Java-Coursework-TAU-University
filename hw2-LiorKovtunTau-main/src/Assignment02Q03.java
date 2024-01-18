
public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfOdd = 0;
		int n = Integer.parseInt(args[0]);
		// *** your code goes here below ***
		int prevPrev = 0;
		int prev = 1;
		int curr = 1;
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		// *** your code goes here below ***
		for(int i = 0 ; i < n ; i++)
		{
			System.out.print(curr + " ");
			if(curr % 2 == 1)
				numOfOdd++;
			curr = prev + prevPrev;
			prevPrev = prev;
			prev = curr;
		}
		System.out.println("\n" + "The number of odd numbers is: "+numOfOdd);

	}

}
