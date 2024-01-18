package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }
    @Override
    public Iterator<Item> selection() {
        Comparator<Item> comparator = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return -Double.compare((o1.value/o1.weight) , (o2.value/o2.weight));
            }
        };
        List<Item> sortedList = new ArrayList<>(lst);
        Collections.sort(sortedList,comparator);
        return sortedList.iterator();
     }
    public static double countWeight(List<Item> lst)
    {
        Iterator<Item> it = lst.iterator();
        double count = 0;
        while(it.hasNext())
        {
            count += it.next().weight;
        }
        return count;
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
        double count = countWeight(candidates_lst);
        return count < capacity;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
        double count = countWeight(candidates_lst);
        if(count + element.weight <= capacity){
            candidates_lst.add(element);
        }else {
            double newWeight = capacity - count;
            Item newItem = new Item(newWeight, newWeight/element.weight * element.value);
            candidates_lst.add(newItem);
        }
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
        if(countWeight(candidates_lst) == (double)capacity)
            return true;
        if(lst.size() == candidates_lst.size())
            return true;
        return false;
    }

}
