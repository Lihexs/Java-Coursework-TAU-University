package il.ac.tau.cs.sw1.ex8.tfIdf;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogramIterator;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	private boolean isInitialized = false;
	private HashMap<String, HashMapHistogram<String>> map;
	//Add members here
	public FileIndex(){
		map = new HashMap<String,HashMapHistogram<String>>();
	}
	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
  	public void indexDirectory(String folderPath) { //Q1
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				try {
					List<String> list = FileUtils.readAllTokens(file);
					Iterator<String> iterator = list.iterator();
					HashMapHistogram<String> histogram = new HashMapHistogram<>();
					while(iterator.hasNext())
					{
						histogram.addItem(iterator.next());
					}
					map.put(file.getName(),histogram);
				}catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		/*******************/

		/*******************/
		isInitialized = true;
	}
	
	
	
	// Q2
  	
	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{ 
		if(!map.containsKey(fileName))
			throw new FileIndexException("File not found");
		return map.get(fileName).getCountForItem(word.toLowerCase());
	}
	
	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{
		if(!map.containsKey(fileName))
			throw new FileIndexException("File not found");
		return map.get(fileName).getItemsSet().size();
	}
	
	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		return map.size();
	}

	
	/* @pre: isInitialized() */
	public double getTF(String word, String fileName) throws FileIndexException{ // Q3
		if(!map.containsKey(fileName))
			throw new FileIndexException("File not found");
		HashMapHistogram<String> histogram = map.get(fileName);
		return calcTF(histogram.getCountForItem(word), histogram.getCountsSum());
	}
	
	/* @pre: isInitialized() 
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		int count = 0;
		for(HashMapHistogram<String> fileMap : map.values())
		{
			if(fileMap.getCountForItem(word) > 0)
				count++;
		}
		return calcIDF(map.size(), count);
	}

	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k)  throws FileIndexException{
		if(!map.containsKey(fileName))
			throw new FileIndexException("File not found");
		HashMapHistogram<String> histogram = map.get(fileName);
		List<Map.Entry<String, Double>> list1 = new LinkedList<>();
		List<Map.Entry<String, Double>> list2 = new LinkedList<>();
		for (String word : histogram.getItemsSet()) {
			list1.add(new AbstractMap.SimpleEntry<String, Double>(word,getTFIDF(word,fileName)));
		}
		Comparator<Map.Entry<String, Double>> comparator = new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				int compare = Double.compare(o1.getValue() , o2.getValue());
				if(compare == 0)
					return o1.getKey().compareTo(o2.getKey());
				return -compare;
			}
		};
		Collections.sort(list1,comparator);
		int i = 0;
		Iterator<Map.Entry<String, Double>> iterator = list1.iterator();
		while(i < k && iterator.hasNext())
		{
			list2.add(iterator.next());
			i++;
		}
		return list2;
	}


	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
		double count1 = 0;
		double count2 = 0;
		double count3 = 0;
		if(!map.containsKey(fileName1) || !map.containsKey(fileName2))
			throw new FileIndexException("File not found");
		Set<String> dictionary = new HashSet<>();
		dictionary.addAll(map.get(fileName1).getItemsSet());
		dictionary.addAll(map.get(fileName2).getItemsSet());
		for (String word : dictionary) {
			double num1 = getTFIDF(word, fileName1);
			double num2 = getTFIDF(word, fileName2);
			count1 += (num1 * num2);
			count2 += Math.pow(num1, 2);
			count3 += Math.pow(num2, 2);
		}

		return count1/ Math.sqrt(((count2) * (count3)));
	}
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k) 
			throws FileIndexException{ //Q6
		if(!map.containsKey(fileName))
			throw new FileIndexException("File not found");
		List<Map.Entry<String, Double>> list = new ArrayList<>();

		for(Map.Entry<String,HashMapHistogram<String>> entry : map.entrySet())
		{
			String otherFile = entry.getKey();
			list.add(new AbstractMap.SimpleEntry<String,Double>(otherFile, getCosineSimilarity(fileName, otherFile)));

		}
		Comparator<Map.Entry<String, Double>> comparator = new Comparator<Map.Entry<String,Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return -Double.compare(o1.getValue(),o2.getValue());
			}
		};
		Collections.sort(list,comparator);
		List<Map.Entry<String,Double>> list1 = new LinkedList<>();
		Iterator<Map.Entry<String, Double>> iterator1 = list.iterator();
		int i = 0;
		while(i < k && iterator1.hasNext())
		{
			AbstractMap.Entry<String,Double> tmp = iterator1.next();
			if(!tmp.getKey().equals(fileName)) {
				list1.add(tmp);
				i++;
			}
		}
		return list1; //replace this with the correct value
	}

	
	
	//add private methods here, if needed

	
	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}
	
	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}
	
	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}

}
