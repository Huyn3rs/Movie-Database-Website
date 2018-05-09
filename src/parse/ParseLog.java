package parse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ParseLog {
	
	public static void main(String[] args) {
		long ts = 0;
		long tj = 0;
		int count_ts = 0, count_tj = 0;
		Scanner s = null;
		File file = new File("/home/huy/performance.txt");
		if (!file.exists())
			System.out.println("File not found.");
		if (file.exists())
		{
			try {
				s = new Scanner(file);
				while(s.hasNextLine())
				{
					String str = s.nextLine();
					System.out.println(str);
					String[] split = str.split(" ");
					if(split[0].equals("TS:"))
					{
						ts += Long.parseLong(split[1]);
						count_ts++;
					}
					
					if(split[2].equals("TJ:"))
					{
						tj += Long.parseLong(split[3]);
						count_tj++;
					}
				}
				
				System.out.println("Average TS: " + ts / count_ts);
				System.out.println("Average TJ: " + tj / count_tj);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
			
	}

}
