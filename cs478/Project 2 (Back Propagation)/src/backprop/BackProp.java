package backprop;

import java.util.Stack;

public class BackProp extends SupervisedLearner{

	private Layer[] layers;
	
	public static float RATE = .3f;
	public static int EPOCHS = 100;
	public static float MOMENTUM = 0;
	public static boolean VOWEL = false;
	
	
	public static void setEpochs(int epochs){
		EPOCHS = epochs;
	}
	
	public static void setRate(float rate){
		RATE = rate;
	}
	
	public static void setMomentum(float mom){
		MOMENTUM = mom;
	}
	
	private Stack<float[]> st;
	private Stack<Layer> st_l;

	private float[][] inputs;
	private float[][] targets;

//	public BackProp(){
//		this(3);
//	}

	public BackProp(){
		int[] schema;
		if(VOWEL)
			schema = new int[]{12, 40, 11};
		else
			schema = new int[]{4, 7, 3};
		st = new Stack<float[]>();
		st_l = new Stack<Layer>();
		int size = schema.length - 1;
		layers = new Layer[size];
		for(int i = 0; i < size; i++)
			layers[i] = new Layer(schema[i], schema[i+1]);
	}

	private void prop(float[] input, float[] target){
		assert(st.size() == 0);
		st.push(input);
		for(int i = 0; i < layers.length; i++){
			st.push(getOutput(layers[i], st.peek()));
			st_l.push(layers[i]);
		}
		st.push(target);
	}

	private void backProp(){
		updateError();
		for(int i = 0; i < layers.length; i++)
			updatePrevLevel();
		st.clear();
	}

	private float[] getOutput(Layer layer, float[] input){
		float[] result = new float[layer.getmSize()];
		for(int i = 0; i < result.length; i++)
			result[i] = Utility.sigmoid(getWeightedSum(layer, i, input));
		return result;
	}

	private float getWeightedSum(Layer layer, int row, float[] input){
		float total = 0;
		for(int pRow = 0; pRow < layer.getpSize(); pRow++)
			total += (input[pRow] * layer.getWeight(pRow, row));
		return total;
	}

	private void updateError(){
		float[] target = st.pop();
		float[] actual = st.pop();
		float[] errors = new float[target.length];
		for(int i = 0; i < errors.length; i++)
			errors[i] = (target[i] - actual[i]) * actual[i] * (1 - actual[i]);
		st.push(errors);
	}

	private void updatePrevLevel(){
		Layer layer = st_l.pop();
		float[] errors = st.pop();
		float[] actual = st.pop();
		nextErrors(layer, actual, errors);
		updateWeights(layer, actual, errors);
	}

	public void updateWeights(Layer layer, float[] output, float[] errors){
		for(int pRow = 0; pRow < layer.getpSize(); pRow++){
			for(int mRow = 0; mRow < layer.getmSize(); mRow++){
				layer.updateWeight(pRow, mRow, (BackProp.RATE * output[pRow] * errors[mRow]));
			}
		}
	}

	private void nextErrors(Layer layer, float[] output, float[] errors){
		float[] pErrors = new float[layer.getpSize()];
		for(int pRow = 0; pRow < layer.getpSize(); pRow++){
			float weight = 0f;
			for(int mRow = 0; mRow < layer.getmSize(); mRow++)
				weight += (layer.getWeight(pRow, mRow) * errors[mRow]);
			pErrors[pRow] = weight * (output[pRow] * (1 - output[pRow]));
		}
		st.push(pErrors);
	}

	@Override
	public void train(Matrix features, Matrix labels) throws Exception {
		inputs = features.toFloatArray();
		targets = labels.getTargets();

		for(int epoch = 0; epoch < EPOCHS; epoch++){
			for(int i = 0; i < inputs.length; i++){
				prop(inputs[i], targets[i]);
				backProp();
			}}

	}

	@Override
	public void predict(double[] features, double[] labels) throws Exception {

		float[] result = new float[features.length];
		for(int i = 0; i < features.length; i++)
			result[i] = (float)features[i];

		for(int j = 0; j < layers.length; j++)
			result = getOutput(layers[j], result);
		labels[0] = Utility.getMaxIndex(result);
	}
}
