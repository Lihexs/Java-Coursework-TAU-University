package il.ac.tau.cs.sw1.ex5;


import java.io.*;
import java.util.Arrays;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	public static final String ALL_YOU_NEED_FILENAME = "resources/hw5/all_you_need.txt";
	public static final String EMMA_FILENAME = "resources/hw5/emma.txt";
	public static final String ALL_YOU_NEED_MODEL_DIR = "resources/hw5/all_you_need_model";
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
	}
	
	
	public static String convertToWord(String word)
	{
		StringBuilder s = new StringBuilder();
		for(int i = 0 ; i < word.length() ; i++)
		{
			int k = (int)word.charAt(i);
			if((122 >= k && k >= 97) || (57 >= k && k >= 48))
				s.append(word.charAt(i));
		}
		return s.toString();
	}

	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public static boolean checkWordContainsLetter(String word){
		for(int i = 0 ; i< word.length() ; i++)
		{
			if(122 >= (int)word.charAt(i) && (int)word.charAt(i) >= 97)
				return true;
		}
		return false;
	}
	public static String[] buildStringArryFromFile(String fileName) throws IOException
	{
		FileReader reader = new FileReader(new File(fileName));
		BufferedReader buffer = new BufferedReader(reader);
		StringBuilder s = new StringBuilder();
		String currentLine = buffer.readLine();
		while(currentLine != null)
		{
			s.append(currentLine);
			s.append(" new_line ");
			currentLine = buffer.readLine();
		}
		buffer.close();
		reader.close();
		String[] wordArray = s.toString().split("\\s+");
		for(int i = 0 ; i < wordArray.length ; i++)
		{
			wordArray[i] = convertToWord(wordArray[i].toLowerCase());
		}
		return wordArray;
	}
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		String[] arr = new String[MAX_VOCABULARY_SIZE];
		int count = 1;
		int wordsAdded = 0;
		boolean foundNumber = false;
		String[] wordsArray = buildStringArryFromFile(fileName);
		for(int i = 0 ; (i < wordsArray.length) && (wordsAdded < MAX_VOCABULARY_SIZE); i++)
		{
			boolean foundWord = false;
			String word = wordsArray[i].toLowerCase();
			if(word.equals(""))
				continue;
			if(word.equals("new_line"))
				continue;
			if(word.equals("newline"))
				continue;
			char firstLetter = word.charAt(0);
			if(57 >= firstLetter && firstLetter >= 48 && !foundNumber)
			{
				foundNumber = true;
				arr[0] = SOME_NUM;
				wordsAdded++;
			}
			if(checkWordContainsLetter(word))
			{
				String newWord = convertToWord(word);
				for(int j = 0 ; j < count ; j++)
				{
					if(newWord.equals(arr[j]))
					{
						foundWord = true;
						break;
					}
				}
				if(foundWord)
					continue;
				arr[count] = convertToWord(word);
				count++;
				wordsAdded++;
			}
		}
		if(arr[0] == null)
			return Arrays.copyOfRange(arr,1,count);
		else
			return Arrays.copyOfRange(arr,0,count);

	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		String[] words = buildStringArryFromFile(fileName);
		int[][] arr = new int[vocabulary.length][vocabulary.length];

		for(int i = 0; i < words.length - 1; i++)
		{
			String currentWord = convertToWord(words[i].toLowerCase());
			String nextWord = convertToWord(words[i+1].toLowerCase());
			int currentIdx = -1;
			int nextIdx = -1;
			for(int j = 0 ; j < vocabulary.length ; j++)
			{
				if(currentWord.equals(vocabulary[j]))
					currentIdx = j;
				if(nextWord.equals(vocabulary[j]))
					nextIdx = j;
			}
			if(currentIdx >= 0 && nextIdx >= 0)
				arr[currentIdx][nextIdx]++;
		}
		return arr;

	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		FileWriter writeVoc = new FileWriter(new File(fileName + VOC_FILE_SUFFIX));
		BufferedWriter bufferVoc = new BufferedWriter(writeVoc);
		bufferVoc.write(mVocabulary.length + "words");
		for(int i = 0 ; i < mVocabulary.length ; i++)
		{
			bufferVoc.newLine();
			bufferVoc.write(i + "," + mVocabulary[i]);
		}
		bufferVoc.close();
		writeVoc.close();
		FileWriter writeCount = new FileWriter(new File(fileName + COUNTS_FILE_SUFFIX));
		BufferedWriter bufferCount = new BufferedWriter(writeCount);
		for(int i = 0 ; i < mBigramCounts.length ; i++)
		{
			for(int j = 0 ; j < mBigramCounts[0].length ; j++)
			{
				bufferCount.write(i + "," + j + ":" + mBigramCounts[i][j]);
				bufferCount.newLine();
			}
		}
		bufferCount.close();
		writeCount.close();
	}

	public static String wordToIntengerString(String s)
	{
		StringBuilder word = new StringBuilder();
		int i = 0;
		do {
			word.append(s.charAt(i));
			i++;
		}
		while(i < s.length() && ( 57 >= s.charAt(i) && s.charAt(i) >= 48));

		return word.toString();
	}
	public static String wordToStringWithOutNumbers(String s)
	{
		StringBuilder word = new StringBuilder();
		for(int i = 0 ; i < s.length() ; i++)
		{
			if( 122 >= s.charAt(i) && s.charAt(i) >= 97)
				word.append(s.charAt(i));
		}
		return word.toString();
	}
	public static String firstIndexOfLoad(String s)
	{
		int index = s.indexOf(",");
		return s.substring(0,index);
	}
	public static String secondIndexOfLoad(String s)
	{
		int index = s.indexOf(",");
		int index1 = s.indexOf(":");
		return s.substring(index + 1,index1);
	}
	public static String arrayValueOfLoad(String s)
	{
		int index = s.indexOf(":");
		return s.substring(index + 1);
	}
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		FileReader loadVoc = new FileReader(fileName + VOC_FILE_SUFFIX);
		BufferedReader vocBuffer = new BufferedReader(loadVoc);
		String[] vocabulary = new String[Integer.parseInt(wordToIntengerString(vocBuffer.readLine()))];
		String line = vocBuffer.readLine();
		for(int i = 0 ; i < vocabulary.length && line != null ; i++)
		{
			vocabulary[i] = wordToStringWithOutNumbers(line);
			line = vocBuffer.readLine();
		}
		vocBuffer.close();
		loadVoc.close();
		int[][] bigramCount = new int[vocabulary.length][vocabulary.length];
		FileReader loadCount = new FileReader(fileName + COUNTS_FILE_SUFFIX);
		BufferedReader countBuffer = new BufferedReader(loadCount);
		line = countBuffer.readLine();
		while(line != null)
		{
			bigramCount[Integer.parseInt(firstIndexOfLoad(line))][Integer.parseInt(secondIndexOfLoad(line))] = Integer.parseInt(arrayValueOfLoad(line));
			line = countBuffer.readLine();
		}
		loadCount.close();
		countBuffer.close();
		mVocabulary = vocabulary;
		mBigramCounts = bigramCount;
	}



	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		for(int i = 0 ; i < mVocabulary.length ; i++)
		{
			if(mVocabulary[i].equals(word))
				return i;
		}
		return ELEMENT_NOT_FOUND;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int firstWord = getWordIndex(word1);
		int secondWord = getWordIndex(word2);
		if(firstWord == -1 || secondWord == -1)
			return 0;

		return mBigramCounts[firstWord][secondWord];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int wordIndex = getWordIndex(word);
		int max = 0;
		int maxIdx = -1;
		for(int i = 0 ; i < mBigramCounts[wordIndex].length ; i++)
		{
			if(max < mBigramCounts[wordIndex][i])
			{
				max = mBigramCounts[wordIndex][i];
				maxIdx = i;
			}
		}
		if(maxIdx >= 0)
			return mVocabulary[maxIdx];

		return null;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		if(sentence.equals(""))
			return true;
		String[] arr = sentence.split("\\s+");
		for(int i = 0 ; i < arr.length ; i++)
		{
			if(getWordIndex(arr[i]) == -1)
				return false;
		}
		for(int i = 0 ; i < arr.length - 1 ; i++)
		{
			int firstWord = getWordIndex(arr[i]);
			int secondWord = getWordIndex(arr[i+1]);
			if(mBigramCounts[firstWord][secondWord] == 0)
				return false;
		}
		if(arr.length == 0)
			return false;
		return true;
	}
	
	
	public static boolean checkIfAllZero(int[] arr1, int[] arr2)
	{
		int count1 = 0;
		int count2 = 0;
		for(int i = 0 ; i < arr1.length ; i++)
		{
			if(arr1[i] == 0)
				count1++;
			if(arr2[i] == 0)
				count2++;
		}
		return count1 == arr1.length || count2 == arr2.length;
	}
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		if(checkIfAllZero(arr1,arr2))
			return -1;
		double x = 0;
		double y = 0;
		double z = 0;
		for(int i = 0 ; i < arr1.length ; i++)
		{
			x += arr1[i] * arr2[i];
			y += Math.pow(arr1[i],2);
			z += Math.pow(arr2[i],2);
		}
		y = Math.sqrt(y);
		z = Math.sqrt(z);

		return x/(y*z);
	}

	public static int[] getRow(int[][] arr,int row)
	{
		int[] wordsArray = new int[arr.length];
		for(int i = 0 ; i < arr.length ; i++)
		{
			wordsArray[i] = arr[row][i];
		}
		return wordsArray;
	}
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		if(mVocabulary.length == 1)
			return word;
		int wordIndex = getWordIndex(word);
		int similarWord = -1;
		double simlarWordValue = -2;
		int[] wordVector = getRow(mBigramCounts,wordIndex);
		for(int i = 0 ; i < mBigramCounts.length ; i++)
		{
			if(i == wordIndex)
				continue;
			double value = calcCosineSim(wordVector,getRow(mBigramCounts,i));
			if( value > simlarWordValue )
			{
				similarWord = i;
				simlarWordValue = value;
			}
		}
		if(similarWord >=0)
			return mVocabulary[similarWord];

		return word;
	}

}
