package cs142.lab09;
public class BSTsimple {
	
	Node node;

	public BSTsimple()
	{
		
	}
	
	public static void main(String[] args)
	{
		Node node1 = new Node(null, null, 1);
		Node node4 = new Node(null, null, 4);
		Node node7 = new Node(null, null, 7);
		Node node13 = new Node(null, null, 13);
		Node node14 = new Node(node13, null, 14);
		Node node10 = new Node(null, node14, 10);
		Node node6 = new Node(node4, node7, 6);
		Node node3 = new Node(node1, node6, 3);
		Node node8 = new Node(node3, node10, 8);
		
		System.out.println(node8);
	}
	
	public String toString()
	{
		String returner = new String("");
		
		returner += node.getLeft().toString() + node.getNum() + node.getRight().toString();
		
		return returner;
	}
}
