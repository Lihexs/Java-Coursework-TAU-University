package il.ac.tau.cs.sw1.bufferedIO;

import java.io.FileReader;
import java.io.IOException;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class MyBufferReader implements IBufferedReader {
	private char[] array;
	FileReader file;
	StringBuffer s = new StringBuffer();
	int newLineIndex = 0;
	int bufferSize;
	boolean foundLine = false;
	int offset = 0;
	/*
	 * @pre: bufferSize > 0
	 * @pre: fReader != null
	 */
	public MyBufferReader(FileReader fReader, int bufferSize) {
		array = new char[bufferSize];
		file = fReader;
		this.bufferSize = bufferSize;
		this.offset = array.length;
	}


	@Override
	public void close() throws IOException {
		//Leave this empty
	}


	@Override
	public String getNextLine() throws IOException {
		int size = array.length;
		for (; ; offset++) {
			if (offset >= array.length) {
				size = file.read(array);
				offset = 0;
			}
			if(size == -1)
				return null;
			if (array[offset] == '\n') {
				offset++;
					break;
			} else
				s.append(array[offset]);
		}
		String d = s.toString();
		s.delete(0, s.length());
		return d.trim();
	}
	/**
	public String getNextLine() throws IOException{
		if(foundLine){
			for(int i = newLineIndex ;)
		}
		while (!foundLine){
			file.read(array);
			for(int i = 0 ; i < array.length ; i++){
				if( array[i] == '\r'){
					foundLine = true;
					newLineIndex = i;
					break;
				}
				s.append(array[i]);
			}
		}
		String d = s.toString().trim();
		s.delete(0, s.length());
		for(int j = newLineIndex ; j < array.length ; j ++){
			s.append(array[j]);
		}
		return d;
	}
	 */
	/**

	}**/
}

