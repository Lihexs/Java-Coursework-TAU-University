package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	// add members here
	private HashMap<T,Integer> map;
	
	//add constructor here, if needed
	
	public HashMapHistogram(){
		map = new HashMap<T,Integer>();
	}
	
	@Override
	public void addItem(T item) {
		if(map.containsKey(item))
			map.put(item,map.get(item) + 1);
		else
			map.put(item , 1);
	}
	
	@Override
	public boolean removeItem(T item)  {
		if(!map.containsKey(item))
			return false;
		map.remove(item);
		return true;
	}
	
	@Override
	public void addAll(Collection<T> items) {
		for(T item : items)
		{
			if(map.containsKey(item))
				map.put(item, map.get(item) + 1);
			else
				map.put(item , 1);
		}
	}

	@Override
	public int getCountForItem(T item) {
		return map.getOrDefault(item, 0);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<T> getItemsSet() {
		return map.keySet();
	}
	
	@Override
	public int getCountsSum() {
		int count = 0;
		for(Integer quantity  : map.values())
			count += quantity;

		return count;
	}
	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		return new HashMapHistogramIterator<>(map);
	}

}
