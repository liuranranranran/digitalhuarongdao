package digitalhuarongdao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

public class RecordsLoader {
	public static Vector<Records> read(String filename) throws FileNotFoundException,
		IOException, NoSuchElementException{
		Vector<Records> vector = new Vector<Records>();
		
		FileReader in = new FileReader(filename);
		BufferedReader bfr = new BufferedReader(in);
		String line;
		while((line = bfr.readLine()) != null) {
			StringTokenizer stz = new StringTokenizer(line,"_",false);
			String[] a = new String[4];
			for(int i = 0;i < 4;i++) {
				a[i] = stz.nextToken();
			}
			Records record = new Records(a[0],a[1],a[2],a[3]);
			vector.addElement(record);
		}
		in.close();
		return vector;
	}
	public static void write(String filename, Vector<Records> vector)
			throws IOException  {

		FileWriter out = new FileWriter(filename);
		PrintWriter pfr = new PrintWriter(out);
		String line;
		for(Iterator<Records> i = vector.iterator();i.hasNext();) {
			line = i.next().toString();
			pfr.write(line+"\n");
		}
		out.close(); 
	}
}
