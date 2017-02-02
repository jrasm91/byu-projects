package backprop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileLoader {

	// Loads from an ARFF file
	public static DataSet loadArff(String filename) {

		boolean read_data = false;
		String relation = "";
		List<String> attributes = new ArrayList<String>();
		List<float[]> data = new ArrayList<float[]>();
		List<String> targets = new ArrayList<String>();
		List<String> classNames = new ArrayList<String>();

		Scanner s;
		try {
			s = new Scanner(new File(filename));
			while (s.hasNext()) {
				String line = s.nextLine().trim();
				if (line.length() <= 0 || line.charAt(0) == '%') 
					continue;
				if(!read_data){
					Scanner t = new Scanner(line);
					String token = t.next().toUpperCase();
					switch(token){
					case "@RELATION":
						relation = t.nextLine();
						break;
					case "@ATTRIBUTE":
						String attr = t.nextLine();
						if(attr.contains("class")){
							String classes = attr.split("[ \t]+")[2];
							String[] parts = classes.substring(1, classes.length() - 1).split(",");
							classNames.addAll(Arrays.asList(parts));
						} else {
							attributes.add(attr.split("[ \t]+")[1]);
						}
						break;
					case "@DATA":
						read_data = true;
						break;
					}
					t.close();
				}
				else {
					String[] parts = line.split(",");
					float[] values = new float[parts.length - 1];
					for(int i = 0; i < values.length; i++)
						values[i] = Float.parseFloat(parts[i]);
					data.add(values);
					targets.add(parts[parts.length - 1]);
				}
			} 
			s.close();
		}
		catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return new DataSet(relation, attributes, targets, classNames, data);
	}

	public static void main(String[] args){
		DataSet iris = FileLoader.loadArff("../data/iris.arff");
		System.out.println(iris);
	}
}
