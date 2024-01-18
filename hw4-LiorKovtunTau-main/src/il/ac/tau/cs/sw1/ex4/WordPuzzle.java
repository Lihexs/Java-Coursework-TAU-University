package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';

	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		char[] array = new char[word.length()];
		for(int i = 0 ; i < array.length ; i++)
		{
			if(template[i])
				array[i] = HIDDEN_CHAR;
			else
				array[i] = word.charAt(i);
		}
		return array;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		int count = 0;
		if (word.length() < 2)
			return false;
		for(int i = 0 ; i < template.length ; i++)
		{
			if(template[i])
				count++;
		}
		if(count == 0 || count == word.length())
			return false;

		for(int i = 0 ; i < word.length(); i++)
		{
			for(int j = 0 ; j < word.length() ; j++)
			{
				if((word.charAt(i) == word.charAt(j)) &&  (i != j) && (template[i] != template[j]))
					return false;
			}
		}
		return true;
	}
	public static boolean[][] createAllTrueFalseArray(int k)
	{
		int numberOfCptions = (int)(Math.pow(2,(double)k));
		String[] array = new String[numberOfCptions];
		boolean[][] trueAndFalseArray = new boolean[numberOfCptions][k];
		for(int i = 0 ; i < numberOfCptions ; i++)
		{
			array[i] = String.format("%" + k + "s",
					Integer.toBinaryString(i)).replaceAll(" ", "0");
		}
		for(int i = 0 ; i < numberOfCptions ; i++)
			for(int j = 0 ; j < k ; j++)
			{
				if(array[i].charAt(j) == '1')
					trueAndFalseArray[i][j] = true;
				else
					trueAndFalseArray[i][j] = false;
			}

		return trueAndFalseArray;
	}
	public static boolean[][] createLegalArray(String word ,boolean[][] array)
	{
		int count = 0;
		boolean[][] array1 = new boolean[array.length][array[0].length];
		for(int i = 0 ; i < array.length ; i++)
		{
			if(checkLegalTemplate(word, array[i])) {
				array1[count] = array[i];
				count++;
			}
		}
		return array1;
	}
	public static boolean[][] createLegalArrayCounted(String word,boolean[][] array , int k)
	{
		int count1 = 0;
		int count2 = 0;
		boolean [][] array1 = new boolean[array.length][array[0].length];
		for(int i = 0 ; i < array.length ; i++)
		{
			for(int j = 0 ; j < array[i].length ; j++)
			{
				if(array[i][j])
					count1++;
			}
			if(count1 == k)
			{
				array1[count2] = array[i];
				count2++;
			}
			count1 = 0;
		}
		if(count2 == 0)
			return null;

		boolean[][] array2 = new boolean[count2][array1[0].length];
		for(int i = 0 ; i < count2 ; i++)
		{
			array2[i] = array1[i];
		}
		return array2;
	}

	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3

		return createLegalArrayCounted(word,createLegalArray(word,createAllTrueFalseArray(word.length())) , k);
	}


	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int count = 0;
		for(int i = 0 ; i < puzzle.length ; i++)
		{
			if (word.charAt(i) == guess){
				puzzle[i] = guess;
				count++;
			}
		}
		return count;
	}


	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character.
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */

	public static boolean wordContains(String w, char c)
	{
		for(int i = 0 ; i < w.length() ; i++)
		{
			if(w.charAt(i) == c)
				return true;
		}
		return false;
	}

	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		Random r = new Random();
		int count1 = 0;
		int count2 = 0;
		boolean[] arr1 = new boolean[26];
		boolean[] arr2 = new boolean[26];
		for (int i = 0; i < word.length(); i++) {
			if (puzzle[i] == HIDDEN_CHAR) {
				arr1[(int)word.charAt(i) - 97] = true;
			}
		}
		for(int i = 0 ; i < 26 ; i++)
		{
			if(arr1[i])
				count1++;
		}
		for(int i = 0 ; i < 26 ; i++)
		{
			if(!arr1[i] && !already_guessed[i] && !wordContains(word,(char)(97 + i)))
			{
				arr2[i] = true;
				count2++;
			}
		}
		char[] arr3 = new char[count1];
		char[] arr4 = new char[count2];
		int count3 = 0;
		int count4 = 0;
		for(int i = 0 ; i < 26 ; i++)
		{
			if(arr1[i])
			{
				arr3[count3] = (char)(i + 97);
				count3++;
			}
			if(arr2[i])
			{
				arr4[count4] = (char)(i + 97);
				count4++;
			}
		}
		char[] arr = new char[2];
		int randomTrue = r.nextInt(count3);
		int randomFalse = r.nextInt(count4);
		if((int)arr3[randomTrue] > (int)arr4[randomFalse])
		{
			arr[1] = arr3[randomTrue];
			arr[0] = arr4[randomFalse];
		}
		else{
			arr[0] = arr3[randomTrue];
			arr[1] = arr4[randomFalse];
		}
		return arr;
	}



	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		Random r = new Random();
		Boolean flag = true;
		char[] puzzle;
		printSettingsMessage();
		while(flag) {
			printSelectTemplate();
			int choose = inputScanner.nextInt();
			if (choose == 1) {
				printSelectNumberOfHiddenChars();
				int hiddenCharacterNumbers = inputScanner.nextInt();
				boolean[][] arr = getAllLegalTemplates(word, hiddenCharacterNumbers);
				if (arr == null) {
					printWrongTemplateParameters();
				}
				else{
					puzzle = createPuzzleFromTemplate(word,arr[r.nextInt(arr.length)]);
					return puzzle;
				}
			}
			if (choose == 2) {
				printEnterPuzzleTemplate();
				inputScanner.nextLine();
				String str = inputScanner.nextLine();
				String[] str1 = str.split(",");
				boolean[] template = new boolean[str1.length];
				for (int i = 0; i < str1.length; i++) {
					if (str1[i].equals("_"))
						template[i] = true;
					if(str1[i].equals("X"))
						template[i] = false;
				}
				if(checkLegalTemplate(word,template))
				{
					puzzle = createPuzzleFromTemplate(word,template);
					return puzzle;
				}
				printWrongTemplateParameters();
			}
		}
		return null;
	}
	public static int howManyHidden(char[] puzzle)
	{
		int count = 0;
		for(int i = 0 ; i < puzzle.length ; i++)
		{
			if(puzzle[i] == HIDDEN_CHAR)
				count++;
		}
		return count;
	}

	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		if(puzzle == null ||howManyHidden(puzzle) == 0)
			return;
		printGameStageMessage();
		boolean[] already_gussed = new boolean[26];
		final int MAX_NUM_OF_TRIES = howManyHidden(puzzle) + 3;
		int currentOfTries = MAX_NUM_OF_TRIES;
		while(currentOfTries > 0) {
			System.out.println(Arrays.toString(puzzle));
			printEnterYourGuessMessage();
			char choose = inputScanner.next().charAt(0);
			if (choose == 'H') {
				char[] hintArray = getHint(word, puzzle, already_gussed);
				System.out.println("Here's a hint for you: choose either " + hintArray[0] + " or " + hintArray[1]);
			}
			if (choose != 'H') {
				currentOfTries--;
				boolean isCorrect = false;
				int count = 0;
				already_gussed[(int) (choose - 97)] = true;
				for (int i = 0; i < puzzle.length; i++) {
					if (word.charAt(i) == choose) {
						puzzle[i] = choose;
						isCorrect = true;
					}
					if (puzzle[i] != HIDDEN_CHAR)
						count++;
				}
				if (count == word.length()) {
					printWinMessage();
					break;
				}
				if (isCorrect) {
					printCorrectGuess(currentOfTries);
				} else {
					printWrongGuess(currentOfTries);
				}
			}
		}
		printGameOver();
		return;
	}




/*************************************************************/
/********************* Don't change this ********************/
	/*************************************************************/

	public static void main(String[] args) throws Exception {
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}

	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}

	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}

	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}