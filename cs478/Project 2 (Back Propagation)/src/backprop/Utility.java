package backprop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Utility {


	public static float sigmoid(float x) {
		return  (1 / (float)(1 + Math.exp(-x)));
	}

	public static void p(String format, Object... args){
		System.out.println(String.format(format, args));
	}

	public static void dump(Layer[] layers){
		for(Layer l: layers){
			System.out.println();
			printLayer(l);
		}
	}

	public static void printLayer(Layer layer){
		StringBuffer buff = new StringBuffer();
		for(int pRow = 0; pRow < layer.getpSize(); pRow++){
			for(int mRow = 0; mRow < layer.getmSize(); mRow++){
				buff.append(layer.getWeight(pRow, mRow) + ", ");
			}
			buff.append("\n");
		}
		System.out.print(buff.toString());
	}

	
	public static int getMaxIndex(float[] input) {
		int maxIndex = 0;
		for(int i = 0; i < input.length; i++)
			if(input[i] > input[maxIndex])
				maxIndex = i;
//		System.out.print(maxIndex + " --> ");
//		Utility.p(input);
		return maxIndex;
	}

}