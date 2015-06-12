package ixn.snakegame;

import java.io.IOException;
import java.io.RandomAccessFile;

public class WorkWithFile {//говнокод короче
	
	public static RandomAccessFile file;
	
	public static void writeFile(int number1, int number2) throws IOException
	{
		String[] str = new String[2];
		str[0] = Integer.toString(number1);
		str[1] = Integer.toString(number2);
		file = new RandomAccessFile("res//Results.txt", "rw");
		file.write(str[0].getBytes());
		file.write(" ".getBytes());
		file.write(str[1].getBytes());
		file.write(" ".getBytes());
		file.close();
	}
	public static int[] readFile() throws IOException
	{
		int i[] = new int [2];
		int k;
		String str = "";
		char[] ch;
		file = new RandomAccessFile("res//Results.txt", "r");
		k = file.read();
		while (k != -1)
		{
			str = str + (char)k;
			k = file.read();
		}
		file.close();
		ch = str.toCharArray();
		str = "";
		boolean b = false;
		for(int t = 0, g = 0; b == false; t++)
		{
			if(ch[t] != ' ')
				str += ch[t];
			if(ch[t] == ' ')
			{
				if(g == 1)
					b = true;
				i[g] = Integer.parseInt(str);
				g++;
				str = "";
			}
		}
		return i;
	}
}