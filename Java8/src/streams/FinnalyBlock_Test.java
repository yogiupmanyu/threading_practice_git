package streams;

import java.io.IOException;


public class FinnalyBlock_Test {

	public static void main(String[] args) {
		
		int i = 0;
		System.out.println("Start");
		if (i > 1) {
			try {
				throw new IOException("throw exc");
			} catch (IOException e) {
				System.out.println("CatchExecute");
			} finally {
				System.out.println("FinallyExecute");
			}
		}
		System.out.println("End");

	}

}
