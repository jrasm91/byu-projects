package backprop;

import java.util.Random;

public class Layer {

	// weights from i to j --> float[i][j] = weight
	private float[][] weights;
	private float[][] prevWeights;
	private int pSize;
	private int mSize;

	public Layer(int pSize, int mSize){
		this.pSize = pSize;
		this.mSize = mSize;
		Random r = new Random();
		
		weights = new float[pSize][mSize];
		prevWeights= new float[pSize][mSize];
		for(int i = 0; i < pSize; i++){
			for(int j = 0; j < mSize; j++){
				double weight = r.nextGaussian();
				while(true){
					if(weight > 1 || weight < -1)
						weight = r.nextGaussian();
					else
						break;
				}
				weights[i][j] = (float)weight;
			}
		}
	}
	
	public float[][] getWeights() {
		return weights;
	}

	public int getpSize() {
		return pSize;
	}

	public void setpSize(int pSize) {
		this.pSize = pSize;
	}

	public int getmSize() {
		return mSize;
	}

	public void setmSize(int mSize) {
		this.mSize = mSize;
	}

	public float getWeight(int row1, int row2){
		return weights[row1][row2];
	}
	
	public void updateWeight(int row1, int row2, float value){
		weights[row1][row2] += value + prevWeights[row1][row2] * BackProp.MOMENTUM;
		prevWeights[row1][row2] = value;
	}
}
