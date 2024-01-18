import java.util.Arrays;

public class ArrayUtils {

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {
		if(direction == 'R') {
			if(move >= 0)
				return shiftRight(array,move);
			return shiftLeft(array,Math.abs(move));
		}
		if(direction == 'L') {
			if (move >= 0)
				return shiftLeft(array, move);
			return shiftRight(array, Math.abs(move));
		}
		return array;
	}

	private static int[] shiftRight(int[] array, int move)
	{
		int[] arr1 = Arrays.copyOfRange(array, array.length - move, array.length);
		int[] arr2 = Arrays.copyOfRange(array, 0, array.length - move);
		int[] arr = new int[array.length];
		return combineArray(arr1 , arr2);
	}

	private static int[] shiftLeft(int[] array, int move)
	{
		int[] arr1 = Arrays.copyOfRange(array,move , array.length);
		int[] arr2 = Arrays.copyOfRange(array,0,move);
		int[] arr = new int[array.length];
		return combineArray(arr1,arr2);
	}

	private static int[] combineArray(int[] arr1, int[] arr2) {
		int arrayLength = arr1.length + arr2.length;
		int[] arr = new int[arrayLength];
		for (int i = 0; i < arr.length; i++) {
			if (i < arr1.length) {
				arr[i] = arr1[i];
			} else {
				arr[i] = arr2[i - arr1.length];
			}
		}
		return arr;
	}
	public static int findShortestPath(int[][] m, int i, int j) {
		return findShortestPath(m,i,findPath(m,i),0,i,j) - 1;
	}

	public static int findShortestPath(int[][] m,int i , int j , int count , int x , int y)
	{
		if( j == y)
			return count;
		int indx = findPath(m,x);
		if(indx != -1)
			return count + findShortestPath(m,i,j, count + 1,indx,y);
		return 0;
	}

	public static int findPath(int[][] m , int x)
	{
		for(int k = 0 ; k < m.length ; k++)
		{
			if(m[x][k] == 1) {
				return k;
			}
		}
		return -1;
	}
}
