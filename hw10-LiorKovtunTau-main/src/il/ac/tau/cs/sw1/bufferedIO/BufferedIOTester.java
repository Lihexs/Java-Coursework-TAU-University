package il.ac.tau.cs.sw1.bufferedIO;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BufferedIOTester {
	public static final String INPUT_FOLDER = "resources/";

	public static void main(String[] args) throws IOException{

		String inputFileName = INPUT_FOLDER + "rocky1.txt";
		FileReader fReader = new FileReader(new File(inputFileName));
		IBufferedReader bR = new MyBufferReader(fReader, 3);
		if (!bR.getNextLine().equals("Now somewhere in the Black mining Hills of Dakota")){
			System.out.println("Reader Error: wrong firstLine");
		}
		if(!bR.getNextLine().equals("There lived a young boy named Rocky Raccoon,"))
			System.out.println("problem");
		if(!bR.getNextLine().equals("And one day his woman ran off with another guy,"))
			System.out.println("problem 2");
		fReader.close();
		bR.close();
		System.out.println("Done!");
	}
}
