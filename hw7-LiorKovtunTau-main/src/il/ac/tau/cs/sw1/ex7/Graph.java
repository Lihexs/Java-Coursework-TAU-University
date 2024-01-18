package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {
        Comparator<Edge> comparator = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                if(o1.weight != o2.weight)
                return Double.compare(o1.weight,o2.weight);

                if(o1.node1 != o2.node1)
                    return Integer.compare(o1.node1,o2.node1);
                return Integer.compare(o1.node2, o2.node2);
            }
        };

        List<Edge> sortedList = new ArrayList<>(lst);
        Collections.sort(sortedList,comparator);
        return sortedList.iterator();

    }
    public boolean[][] createArray(List<Edge> lst){
        boolean[][] array = new boolean[n + 1][n + 1];
        for(Edge e : lst)
        {
            array[e.node1][e.node2] = true;
            array[e.node2][e.node1] = true;
        }
        return array;
    }

    public static boolean containsCircle(int node, boolean[][] arr , Set<Integer> set)
    {
        for(int i = 0 ; i < arr.length ; i++)
        {
            if(arr[node][i]){
                arr[node][i] = false;
                arr[i][node] = false;
                if(set.contains(node) && set.contains(i))
                    return true;
                set.add(node);
                set.add(i);
                if(containsCircle(i,arr,set))
                    return true;
            }
        }
        return false;
    }
    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        Set<Integer> set = new HashSet<>();
        if(element.node1 == element.node2)
            return false;
        boolean[][] arr = createArray(candidates_lst);
        arr[element.node1][element.node2] = true;
        arr[element.node2][element.node1] = true;
        boolean value = containsCircle(element.node1, arr,set);
        return !value;

    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        return candidates_lst.size() == n;
    }
}
