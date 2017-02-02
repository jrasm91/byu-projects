
package cs235.avl;

public class TreeImpl implements BinaryTreeNode{

	private Object data;
	private TreeImpl rightChild;
	private TreeImpl leftChild;
	private int height;

	public TreeImpl(Object data, TreeImpl lc, TreeImpl rc){
		this.data = data;
		rightChild = rc;
		leftChild = lc;
	}
	public TreeImpl (Object data){
		this.data = data;
		rightChild = null;
		leftChild = null;
	}
	public TreeImpl (){
		this.data = null;
		rightChild = null;
		leftChild = null;
	}
	public String toString(){
		return data.toString();
	}
	
	public Object getData() {
		return data;
	}
	public int getHeight() {
		return height;
	}
	public BinaryTreeNode getLeftChild() {
		return leftChild;
	}
	public BinaryTreeNode getRightChild() {
		return rightChild;
	}
}
