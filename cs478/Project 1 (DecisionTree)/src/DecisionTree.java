import java.util.ArrayList;
import java.util.HashMap;

// The contents of this file are distributed under the CC0 license.
// See http://creativecommons.org/publicdomain/zero/1.0/
// ----------------------------------------------------------------

/**
 * For nominal labels, this model simply returns the majority class. For
 * continuous labels, it returns the mean value.
 * If the learning model you're using doesn't do as well as this one,
 * it's time to find a new learning model.
 */
public class DecisionTree extends SupervisedLearner {

	private boolean ACCURACY;
	private boolean D_ACCURACY;
	static boolean DISCRITIZE = true;
	
	public DecisionTree(){
		ACCURACY = true;
//		ACCURACY = false;
//		D_ACCURACY = true;
	}

	private Node root;
	private Node current;

	public void train(Matrix features, Matrix labels) throws Exception {
		root = new Node("root");
		current = root;
		ArrayList<Integer> attributesUsed = new ArrayList<Integer>();
		trainAttributes(features, labels, attributesUsed);
	}

	public void discritize(Matrix features){
		for(int r = 0; r < features.rows(); r++){
			for(int c = 0; c < features.cols(); c++)
				discretize(features, r, c);
		}
	}

	private void discretize(Matrix features, int r, int c){
		double min = features.columnMin(c);
		double max = features.columnMax(c);
		double bucketInteval = (double)(max - min) / 3;
		double value = features.get(r, c);
		double v = 0;
		String LOW = "Low";
		String MEDIUM = "Medium";
		String HIGH = "High";
		String vs = LOW + c;

		if(value > min + bucketInteval*2){
			v = 2;
			vs = HIGH + c;
		} else if(value > min + bucketInteval){
			v = 1;
			vs = MEDIUM + c;
		}
		features.set(r, c, v);
		features.set(r, c, vs);
	}

	private void trainAttributes(Matrix features, Matrix labels, ArrayList<Integer> columnsUsed){
		ArrayList<ArrayList<Float>> averages;
		if(ACCURACY && D_ACCURACY)
			averages = getDoubleAccuracies(features, labels);
		else if(ACCURACY)
			averages = getAllAccuracies(features, labels);
		else
			averages = getAllEntropies(features, labels);

		int colNum = findSmallestIndex(averages, columnsUsed);
		columnsUsed.add(colNum);

		HashMap<String, ArrayList<Integer>> tuples = getAttributeTuples(colNum, features);
		int i = 0;
		for(String key: tuples.keySet()){
			ArrayList<Integer> rowList = tuples.get(key);

			String attrValue = features.attrValue(colNum, (int)features.row(rowList.get(0))[colNum]);
			double attrNumber = features.get(rowList.get(0), colNum);

			String labelValue = labels.attrValue(0, (int)labels.row(rowList.get(0))[0]);
			double labelNumber = labels.get(rowList.get(0), 0);
			if(averages.get(colNum).get(++i) == 0.0 || features.cols() == columnsUsed.size()){
				Node newNode = new Node(current, attrValue, attrNumber, colNum);
				current.addChild(newNode);
				current = newNode;
				current.addChild(new Node(current, labelValue, labelNumber, -2));
				current = current.getParent();
				continue;
			}

			Node newNode = new Node(current, attrValue, attrNumber, colNum);
			current.addChild(newNode);
			current = newNode;
			trainAttributes(new Matrix(features, rowList), new Matrix(labels, rowList), columnsUsed);
			current = current.getParent();
		}
		if(columnsUsed.size() > 0)
			columnsUsed.remove(columnsUsed.size() - 1);
	}
	
	private ArrayList<ArrayList<Float>> getDoubleAccuracies(Matrix features, Matrix labels){
		ArrayList<ArrayList<Float>> entropies = new ArrayList<ArrayList<Float>>();
		
		return entropies;
	}
	
	private ArrayList<ArrayList<Float>> getAllEntropies(Matrix features, Matrix labels){
		ArrayList<ArrayList<Float>> entropies = new ArrayList<ArrayList<Float>>();
		ArrayList<Float> results = null;
		HashMap<String, ArrayList<Integer>> tuples;

		for(int col = 0; col < features.cols(); col++){
			results = new ArrayList<Float>();
			tuples = getAttributeTuples(col, features);
			int allTotals = getTotalCount(tuples);
			float weighted = 0;
			for(String key: tuples.keySet()){
				HashMap<String, ArrayList<Integer>> temp = getTuplesLabelTuples(tuples.get(key), labels);
				int total = getTotalCount(temp);
				float entropy = total * findSubEntropy(total, getCounts(temp))/(float)allTotals;
				weighted += entropy;
				results.add(entropy);
			}
			results.add(0, weighted);
			entropies.add(results);
		}
		return entropies;
	}

	private ArrayList<ArrayList<Float>> getAllAccuracies(Matrix features, Matrix labels){
		ArrayList<ArrayList<Float>> accuracies = new ArrayList<ArrayList<Float>>();
		ArrayList<Float> results = null;
		HashMap<String, ArrayList<Integer>> tuples;

		for(int col = 0; col < features.cols(); col++){
			results = new ArrayList<Float>();
			tuples = getAttributeTuples(col, features);
			int allTotals = getTotalCount(tuples);
			float weighted = 0;
			for(String key: tuples.keySet()){
				HashMap<String, ArrayList<Integer>> temp = getTuplesLabelTuples(tuples.get(key), labels);
				int total = getTotalCount(temp);
				float accuracy = total * findSubAccuracy(total, getCounts(temp))/(float)allTotals;
				weighted += accuracy;
				results.add(accuracy);
			}
			results.add(0, weighted);
			accuracies.add(results);
		}
		return accuracies;
	}


	private float findSubAccuracy(int total, int[] counts){
		int max = 0;
		for(int count: counts)
			if(count > max)
				max = count ;
		//		System.out.print("Finding Sub Accuracy: " + Arrays.toString(counts));
		//		System.out.println(String.format(" --> %3.3f",  (double)(1 - (double)max/total)));
		return 1 - max/total;
	}

	private float findSubEntropy(int total, int[] counts){
		float entropy = 0;
		for(int count: counts)
			entropy += -1 * count/(float)total * Math.log(count/(float)total)/Math.log(2);
		return entropy;
	}

	private int[] getCounts(HashMap<String, ArrayList<Integer>> results){
		int[] counts = new int[results.keySet().size()];
		int i = 0;
		for(String key: results.keySet())
			counts[i++] = results.get(key).size();
		return counts;
	}

	private int getTotalCount(HashMap<String, ArrayList<Integer>> results){
		int result = 0;
		for(String key: results.keySet())
			result += results.get(key).size();
		return result;	
	}

	private HashMap<String, ArrayList<Integer>> getAttributeTuples(int col, Matrix features){
		HashMap<String, ArrayList<Integer>> tupleSets = new HashMap<String, ArrayList<Integer>>();
		for(int i = 0; i < features.rows(); i++){
			double[] r = features.row(i);
			String feature = features.attrValue(col, (int)r[col]);
			if(!tupleSets.containsKey(feature))
				tupleSets.put(feature, new ArrayList<Integer>());
			tupleSets.get(feature).add(i);
		}
		return tupleSets;
	}

	private HashMap<String, ArrayList<Integer>> getTuplesLabelTuples(ArrayList<Integer> rows, Matrix labels){
		HashMap<String, ArrayList<Integer>> tupleSets = new HashMap<String, ArrayList<Integer>>();
		for(int i = 0; i < labels.rows(); i++){
			if(!rows.contains(i))
				continue;
			double[] r = labels.row(i);
			String feature = labels.attrValue(0, (int)r[0]);
			if(!tupleSets.containsKey(feature))
				tupleSets.put(feature, new ArrayList<Integer>());
			tupleSets.get(feature).add(i);
		}
		return tupleSets;
	}

	private int findSmallestIndex(ArrayList<ArrayList<Float>> numbers, ArrayList<Integer> columnsUsed){  
		int index = 0; 
		for(int i = 1; i < numbers.size(); i++)
			if(sum(numbers.get(i)) < sum(numbers.get(index)) &&
					!columnsUsed.contains(i))
				index = i;  
		return index;
	}

	private float sum(ArrayList<Float> e){
		float sum = 0;
		for(Float f: e)
			sum += f;
		return sum;
	}

	public void predict(double[] features, double[] labels) throws Exception {
		double result = root.makeDecision(features);
		labels[0] = result;
	}
}
