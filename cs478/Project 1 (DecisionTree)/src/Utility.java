import java.util.ArrayList;
import java.util.HashMap;


public class Utility {

	public static String toStringHashMap(HashMap<String, ArrayList<Integer>> tupleSets){
		StringBuffer buff = new StringBuffer();
		for(String key: tupleSets.keySet()){
			buff.append(String.format("[%s]\n{", key));
			for(int value: tupleSets.get(key))
				buff.append(" " + value);
			buff.append(" }\n");
		}
		return buff.toString();
	}

	public static String listAttributes(Matrix matrix){
		StringBuilder buff = new StringBuilder();
		buff.append("Attributes: \n[" );
		for(int i = 0; i < matrix.cols(); i++)
			buff.append("\n " + matrix.attrName(i));
		buff.append(" ]");
		return buff.toString();
	}

	public static String printEntropies(ArrayList<ArrayList<Float>> entropies){
		StringBuffer buff = new StringBuffer("\n");
		for(ArrayList<Float> e: entropies){
			buff.append("[ ");
			for(Float f: e){
				buff.append(String.format("%5.5f ", f));
			}
			buff.append(" ]\n");
		}
		return buff.toString();
	}

}
