package backprop;

import java.util.Arrays;
import java.util.List;

public class DataSet {

	private String relation;
	private List<String> attributes;
	private List<String> targets;
	private List<String> classNames;
	private List<float[]> data;
	
	private float[] maxValues;

	public DataSet(String relation, List<String> attributes,
			List<String> targets, List<String> classNames, List<float[]> data) {
		this.relation = relation;
		this.attributes = attributes;
		this.targets = targets;
		this.classNames = classNames;
		this.data = data;
//		this.maxValues = calcMaxValues();
	}
	
	private float[] maxValues(){
		return data.get(0);
	}

	public String getRelation() {
		return relation;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public List<String> getTargets() {
		return targets;
	}

	public List<String> getClassNames() {
		return classNames;
	}

	public List<float[]> getData() {
		return data;
	}

	public int getClass(String name){
		for(int i = 0; i < classNames.size(); i++)
			if(classNames.get(i).toLowerCase().equals(name.toLowerCase()))
				return i;
		throw new IllegalArgumentException(name + " is not a valid class name");
	}

	public String getClass(int value){
		if(value >= classNames.size() || value < 0)
			throw new IllegalArgumentException(value + " is not a valid valid");
		return classNames.get(value);
	}
	
	public float[][] getFormattedTargets(){
		float[][] result = new float[targets.size()][classNames.size()];
		for(int i = 0; i < result.length; i++)
			result[i] = getTargetValues(i);
		return result;
	}

	private float[] getTargetValues(int row){
		int classValue = getClass(targets.get(row));
		float[] result = new float[classNames.size()];
		for(int i = 0; i < result.length; i++)
			result[i] = (i == classValue)? 1 : 0;
		return result;
	}

	@Override
	public String toString() {
		String result = String.format(
				"DataSet\n"
						+ "  -Relation:    %s\n"
						+ "  -Attributes:  %s\n"
						+ "  -Classes:     %s\n",
						relation, attributes, classNames);
		return result + dataToString();
	}

	public String dataToString(){
		StringBuffer buff = new StringBuffer();
		buff.append("  -Data:\n");
		for(int i = 0; i < targets.size(); i++){
			buff.append("    " + Arrays.toString(data.get(i)) + " --> " + targets.get(i) + "\n");
		}
		return buff.toString();
	}
	
	private float[] normalize(float[] in, float[] max){
		for(int i = 0; i < in.length; i++)
			in[i] /= max[i];
		return in;
	}

	public float[][] getFormattedInputs() {
		float[][] inputs = new float[data.size()][attributes.size()];
		for(int i = 0; i < data.size(); i++){
			inputs[i] = normalize(data.get(i), maxValues);
		}
		return inputs;
	}
}
