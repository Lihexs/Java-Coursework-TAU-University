package il.ac.tau.cs.sw1.ex9.riddles.forth;


import java.util.Iterator;

public class B4 implements Iterator<String> {
    private String[] s;
    private int randomNum;
    private int currWord;
    private int indx;
    public B4(String[] strings, int k) {
        randomNum = k;
        s = strings;
        currWord = 0;
        indx = 0;
    }

    @Override
    public boolean hasNext() {
        indx++;
        return indx <= randomNum * s.length;
    }

    @Override
    public String next() {
        return s[currWord++ % s.length];
    }
}
