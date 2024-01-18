package il.ac.tau.cs.sw1.trivia;

import java.io.*;

public class Questions {
    private String[][] arr;
    Questions(File file) throws IOException {
        String line;
        int i = 0;
        int numOfQuestions = howManyQuestions(file);
        arr = new String[numOfQuestions][4];
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        while((line = bufferedReader.readLine()) != null && i < numOfQuestions){
            arr[i] = line.split("\t");
            i++;
        }
    }
    private int howManyQuestions(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int count = 0;
        while(bufferedReader.readLine() != null){
            count++;
        }
        return count;
    }
    public String[][] getArr(){
        return arr;
    }
}
