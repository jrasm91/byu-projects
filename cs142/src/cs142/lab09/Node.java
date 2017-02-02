package cs142.lab09;
public class Node 
{
	private Node left, right;
	private int num;

	public Node(Node left, Node right, int num)
	{
		this.right = right;
		this.left = left;
		this.num = num;
	}

	public Node getLeft()
	{
		return left;
	}

	public Node getRight()
	{
		return right;
	}

	public void setLeft(Node node)
	{
		if(left == null)
		{
			left = node;
		}
	}

	public void setRight(Node node)
	{
		if(right == null)
		{
			right = node;
		}
	}

	public int getNum()
	{
		return num;
	}

	public void add(Node nodeTree, Node node)
	{
		if(node.getNum() < nodeTree.getNum())
		{
			if(nodeTree.getLeft() != null)
			{
				nodeTree.getLeft().add(nodeTree.getLeft(), node);
			}
			else
			{
				nodeTree.setLeft(node);
			}
		}
		if(node.getNum() > nodeTree.getNum())
		{
			if(nodeTree.getRight() != null)
			{
				nodeTree.getRight().add(nodeTree.getRight(), node);
			}
			else
			{
				nodeTree.setRight(node);
			}
		}
	}
	public String toString()
	{
		String leftString = "";
		String rightString = "";

		if(left != null)
			leftString = left.toString();
		if(right != null)
			rightString = right.toString();

		return leftString + " " + num + " " + rightString;
	}


}
