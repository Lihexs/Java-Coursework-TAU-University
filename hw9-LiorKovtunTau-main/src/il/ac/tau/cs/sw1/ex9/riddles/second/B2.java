package il.ac.tau.cs.sw1.ex9.riddles.second;

public class B2 extends A2{
    public A2 getA(Boolean bool){
        if(bool)
            return new B2();
        return new A2();
    }

    public String foo(String word){
        return word.toUpperCase();
    }
}
