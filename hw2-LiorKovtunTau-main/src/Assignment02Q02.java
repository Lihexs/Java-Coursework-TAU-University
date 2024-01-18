

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		int num = Integer.parseInt(args[0]);
		for(int i = 0 ; i < num ; i++)
		{
			piEstimation += (Math.pow(-1,i)) * (1/((double)(2*i + 1)));
		}
		piEstimation *= 4;
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}

}
