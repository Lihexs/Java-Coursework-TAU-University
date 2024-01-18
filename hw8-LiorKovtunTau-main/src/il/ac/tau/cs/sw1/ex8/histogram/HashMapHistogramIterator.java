package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> 
							implements Iterator<Map.Entry<T, Integer>>{

	//add members here
	private Iterator<Map.Entry<T, Integer>> iterator;
	//add constructor here, if needed
	public HashMapHistogramIterator(Map<T,Integer> map) {
		TreeMap<T,Integer> sortedMap = new TreeMap<>(map);
		iterator = sortedMap.entrySet().iterator();
		}


	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Map.Entry<T, Integer> next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
	
	//add private methods here, if needed
}
