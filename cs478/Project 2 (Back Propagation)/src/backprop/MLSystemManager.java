package backprop;

// ----------------------------------------------------------------
// The contents of this file are distributed under the CC0 license.
// See http://creativecommons.org/publicdomain/zero/1.0/
// ----------------------------------------------------------------


import java.util.Random;


public class MLSystemManager {

	/**
	 *  When you make a new learning algorithm, you should add a line for it to this method.
	 */
	public SupervisedLearner getLearner(String model, Random rand) throws Exception
	{
		if (model.equals("baseline")) return new BaselineLearner();
		// else if (model.equals("perceptron")) return new Perceptron(rand);
		else if (model.equals("nn")) return new BackProp();
		// else if (model.equals("decisiontree")) return new DecisionTree();
		// else if (model.equals("knn")) return new InstanceBasedLearner();
		else throw new Exception("Unrecognized model: " + model);
	}

	public void run(String[] args) throws Exception {

		Random rand = new Random(); // No seed for non-deterministic results

		//Parse the command line arguments
		ArgParser parser = new ArgParser(args);
		String fileName = parser.getARFF(); //File specified by the user
		String learnerName = parser.getLearner(); //Learning algorithm specified by the user
		String evalMethod = parser.getEvaluation(); //Evaluation method specified by the user
		String evalParameter = parser.getEvalParameter(); //Evaluation parameters specified by the user
		boolean printConfusionMatrix = parser.getVerbose();
		boolean normalize = parser.getNormalize();

		// Load the model
		SupervisedLearner learner = getLearner(learnerName, rand);

		// Load the ARFF file
		Matrix data = new Matrix();
		data.loadArff(fileName);
		if (normalize)
		{
			System.out.println("Using normalized data\n");
			data.normalize();
		}

		// Print some stats
		System.out.println();
		System.out.println("Dataset name: " + fileName);
		System.out.println("Number of instances: " + data.rows());
		System.out.println("Number of attributes: " + data.cols());
		System.out.println("Learning algorithm: " + learnerName);
		System.out.println("Evaluation method: " + evalMethod);
		System.out.println();

		//		float rate = .3f;
		//		BackProp.setRate(rate);
		float[] rates = {.1f, .2f, .3f, .4f, .5f, .6f, .7f, .8f, .9f};
		for(int i = 0; i < 5; i++){
			//			for(int j = 5; j < 51; j+=5){
			//				learner = new BackProp(j);
			for(float rate: rates){
				BackProp.setMomentum(rate);
//				for(int k = 100; k < 1001; k+=100){
					for(int k = 10; k < 151; k+=10){
					BackProp.setEpochs(k);
					//				System.out.println("Calculating accuracy on a random hold-out set...");
					double trainPercent = Double.parseDouble(evalParameter);
					if (trainPercent < 0 || trainPercent > 1)
						throw new Exception("Percentage for random evaluation must be between 0 and 1");
					//				System.out.println("Percentage used for training: " + trainPercent);
					//				System.out.println("Percentage used for testing: " + (1 - trainPercent));
					data.shuffle(rand);
					int trainSize = (int)(trainPercent * data.rows());
					Matrix trainFeatures = new Matrix(data, 0, 0, trainSize, data.cols() - 1);
					Matrix trainLabels = new Matrix(data, 0, data.cols() - 1, trainSize, 1);
					Matrix testFeatures = new Matrix(data, trainSize, 0, data.rows() - trainSize, data.cols() - 1);
					Matrix testLabels = new Matrix(data, trainSize, data.cols() - 1, data.rows() - trainSize, 1);
					double startTime = System.currentTimeMillis();
					learner.train(trainFeatures, trainLabels);
					double elapsedTime = System.currentTimeMillis() - startTime;
					//				System.out.println("Time to train (in seconds): " + elapsedTime / 1000.0);
					double trainAccuracy = learner.measureAccuracy(trainFeatures, trainLabels, null);
					//				System.out.println("Training set accuracy: " + trainAccuracy);
					Matrix confusion = new Matrix();
					double testAccuracy = learner.measureAccuracy(testFeatures, testLabels, confusion);
					//				System.out.println("Test set accuracy: " + testAccuracy);
					if(printConfusionMatrix) {
						System.out.println("\nConfusion matrix: (Row=target value, Col=predicted value)");
						confusion.print();
						System.out.println("\n");
					}

					if(learnerName.equals("nn")){
						String result = String.format("%d %.1f %f %f", 
								k,
								rate, 
								trainAccuracy, 
								testAccuracy);
						System.out.println(result);  
					}
				}
							}
			}
		}

		/**
		 * Class for parsing out the command line arguments
		 */
		private class ArgParser {

			String arff;
			String learner;
			String evaluation;
			String evalExtra;
			boolean verbose;
			boolean normalize;

			//You can add more options for specific learning models if you wish
			public ArgParser(String[] argv) {
				try{

					for (int i = 0; i < argv.length; i++) {

						if (argv[i].equals("-V"))
						{
							verbose = true;
						}
						else if (argv[i].equals("-N"))
						{
							normalize = true;
						}
						else if (argv[i].equals("-A"))
						{
							arff = argv[++i];
						}
						else if (argv[i].equals("-L"))
						{
							learner = argv[++i];
						}
						else if (argv[i].equals("-E"))
						{
							evaluation = argv[++i];
							if (argv[i].equals("static"))
							{
								//expecting a test set name
								evalExtra = argv[++i];
							}
							else if (argv[i].equals("random"))
							{
								//expecting a double representing the percentage for testing
								//Note stratification is NOT done
								evalExtra = argv[++i];
							}
							else if (argv[i].equals("cross"))
							{
								//expecting the number of folds
								evalExtra = argv[++i];
							}
							else if (!argv[i].equals("training"))
							{
								System.out.println("Invalid Evaluation Method: " + argv[i]);
								System.exit(0);
							}
						}
						else
						{
							System.out.println("Invalid parameter: " + argv[i]);
							System.exit(0);
						}
					}

				}
				catch (Exception e) {
					System.out.println("Usage:");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E [evaluationMethod] {[extraParamters]} [OPTIONS]\n");
					System.out.println("OPTIONS:");
					System.out.println("-V Print the confusion matrix and learner accuracy on individual class values\n");

					System.out.println("Possible evaluation methods are:");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E training");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E static [testARFF_File]");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E random [%_ForTraining]");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E cross [numOfFolds]\n");
					System.exit(0);
				}

				if (arff == null || learner == null || evaluation == null)
				{
					System.out.println("Usage:");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E [evaluationMethod] {[extraParamters]} [OPTIONS]\n");
					System.out.println("OPTIONS:");
					System.out.println("-V Print the confusion matrix and learner accuracy on individual class values");
					System.out.println("-N Use normalized data");
					System.out.println();
					System.out.println("Possible evaluation methods are:");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E training");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E static [testARFF_File]");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E random [%_ForTraining]");
					System.out.println("MLSystemManager -L [learningAlgorithm] -A [ARFF_File] -E cross [numOfFolds]\n");
					System.exit(0);
				}
			}

			//The getter methods
			public String getARFF(){ return arff; }	
			public String getLearner(){ return learner; }	 
			public String getEvaluation(){ return evaluation; }	
			public String getEvalParameter() { return evalExtra; }
			public boolean getVerbose() { return verbose; } 
			public boolean getNormalize() { return normalize; }
		}

		public static void main(String[] args) throws Exception
		{
			MLSystemManager ml = new MLSystemManager();
			ml.run(args);
		}
	}
