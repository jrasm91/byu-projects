
package cs142.lab09;
import java.util.ArrayList;
import java.util.Scanner;


public class BST {

	public static void main(String[] args)
	{
		ArrayList<Integer> array = new ArrayList<Integer>();
		int initialNum =  (int)(Math.random()*100 -49);
		array.add(initialNum);
		Node initialNode = new Node(null, null, initialNum);
		
		for(int i = 1; i < 50; i++)
		{
			int nextNum = (int)(Math.random()*100 -49);
			if(nextNum == 0)
				nextNum = -50;
			if(array.contains(nextNum))
				i--;
			else
			{
				initialNode.add(initialNode, new Node(null, null, nextNum));
				array.add(nextNum);
			}
		}
		
		String returner = initialNode.toString();
		
	
		
		int counter = 0;
		Scanner scan = new Scanner(returner);
		scan.useDelimiter("[ ]+");
		String temp =  "";
		while(scan.hasNext())
		{
			temp += scan.next();
			temp += "\t";
			counter++;
			if(counter % 10== 0)
				temp += "\n";
		}
		System.out.println(temp);
		scan.close();
	}
}