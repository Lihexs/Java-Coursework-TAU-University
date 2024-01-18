package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {
	private double[] polynomArray;
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial()
	{
		polynomArray = new double[]{0.0};
	} 
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		polynomArray = Arrays.copyOfRange(coefficients,0 , coefficients.length);
	}
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		double[] arr;
		int degree1 = this.getDegree();
		int degree2 = polynomial.getDegree();
		if(degree1 == 0)
			return new Polynomial(polynomial.polynomArray);
		if(degree2 == 0)
			return new Polynomial(this.polynomArray);
		if(degree1 > degree2) {
			arr = Arrays.copyOf(polynomArray, degree1 + 1);
			for(int i = 0 ; i < degree2 + 1 ; i++)
				arr[i] += polynomial.polynomArray[i];
		}
		else {
			arr = Arrays.copyOf(polynomial.polynomArray, degree2 + 1);
			for(int i = 0 ; i < degree1 + 1 ; i++)
				arr[i] += this.polynomArray[i];
		}
		return new Polynomial(arr);
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		Polynomial polynom = new Polynomial();
		polynom.polynomArray = Arrays.copyOf(polynomArray,this.polynomArray.length);
		for(int i = 0 ; i < polynomArray.length ; i++)
		{
			polynom.polynomArray[i] *= a;
		}
		return polynom;
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		int maxIndex = 0;
		for(int i = 0 ; i < polynomArray.length; i++)
		{
			if(polynomArray[i] != 0)
				maxIndex = i;
		}
		return maxIndex;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		if(polynomArray.length < n)
			return 0.0;
		return polynomArray[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		int degree = this.getDegree();
		if(c == 0)
		{
			if(degree < n)
				return;
			if(degree == n)
			{
				int max = 0;
				for(int i = 0 ; i < degree - 1 ; i++)
				{
					if(polynomArray[i] != 0)
						max = i;
				}
				polynomArray = Arrays.copyOfRange(polynomArray,0, max + 1);
			}
			return;
		}
		if(degree < n)
		{
			double[] arr = Arrays.copyOfRange(polynomArray,0, n +1);
			arr[n] = c;
			polynomArray = arr;
		}
		else{
			polynomArray[n] = c;
		}
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	 */
	public Polynomial getFirstDerivation()
	{
		if(polynomArray.length <= 1)
			return new Polynomial();
		Polynomial polynom = new Polynomial(polynomArray);
		polynom.polynomArray = Arrays.copyOfRange(polynomArray,1 ,polynomArray.length);
		for(int i = 1 ; i < polynom.polynomArray.length ; i++)
		{
			polynom.setCoefficient(i , (i + 1) * polynom.polynomArray[i]);
		}
		return polynom;
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		int count = 0;
		for(int i = 0 ; i < polynomArray.length ; i++)
			count += Math.pow(x,i) * polynomArray[i];
		return count;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		Polynomial firstDerivative = this.getFirstDerivation();
		Polynomial secondDerivative = firstDerivative.getFirstDerivation();
		return firstDerivative.computePolynomial(x) == 0 && secondDerivative.computePolynomial(x) != 0;
	}


	public static void main(String args[])
	{
		Polynomial f = new Polynomial(new double[] {0,4,5});
		f.setCoefficient(0,1);
		f.setCoefficient(1,1);
		f.setCoefficient(2,1);
		f.setCoefficient(3,1);
		f.setCoefficient(4,1);
		f.setCoefficient(5,1);
	}
}
