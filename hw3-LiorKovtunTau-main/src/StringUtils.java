import java.util.Arrays;

public class StringUtils {

		public static String findSortedSequence(String str) {
			String[] words = str.split(" ");

			int maxTotal = 0;
			int startTotal = -1;
			int start = 0;
			int max = 1;
			for (int i = 1; i < words.length; i++) {
				if (words[i].compareTo(words[i - 1]) >= 0) {
					max += 1;
					if (max >= maxTotal) {
						maxTotal = max;
						startTotal = start;
					}
				} else {
					start = i;
					max = 1;
				}
			}

			if (maxTotal == 0) {
				return words[words.length - 1];
			}
			String[] joined = Arrays.copyOfRange(words, startTotal, startTotal + maxTotal);

			return String.join(" ", joined);
		}



	public static boolean isEditDistanceOne(String a, String b){
		if(a.length() == b.length()) {
			int count = 0;
			for (int i = 0; i < a.length(); i++) {
				if (a.charAt(i) != b.charAt(i))
					count++;
			}
			return (count < 2);
		}
		if(Math.abs(a.length() - b.length()) >= 2)
			return false;

		return true;
	}
}
