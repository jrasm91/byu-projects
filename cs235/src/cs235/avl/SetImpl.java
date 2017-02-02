package cs235.avl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class SetImpl implements Tree{

	private class TreeImpl implements BinaryTreeNode{
		private Object data;
		private TreeImpl rightChild;
		private TreeImpl leftChild;
		private int height;
		public TreeImpl (Object data){
			this.data = data;
			rightChild = null;
			leftChild = null;
			height = -1;
		}		
		public Object getData() {
			return data;
		}
		public int getHeight() {
			return height;
		}
		public TreeImpl getLeftChild() {
			return leftChild;
		}
		public TreeImpl getRightChild() {
			return rightChild;
		}
		public String toString(){
			return data.toString();
		}
		private int getLeftHeight(){
			if(leftChild == null)
				return -1;
			return leftChild.height;
		}
		private int getRightHeight(){
			if(rightChild == null)
				return -1;
			return rightChild.height;
		}
	}
	private TreeImpl root;
	private int size;
	private boolean inserted;
	private boolean removed;

	public SetImpl(){
		root = null;
		size = 0;
		inserted = true;
		removed = true;
	}
	public BinaryTreeNode getRootNode() {
		return root;
	}

	public boolean add(Object arg0) {
		if(arg0 == null)
			throw new IllegalArgumentException("Cannot Add Null Object");

		inserted = true;
		root = add(arg0, root);
		if(!inserted)
			return false;
		size++;
		return true;
	}
	private TreeImpl add(Object arg0, TreeImpl node){


		if(node == null){
			node = new TreeImpl(arg0);
		}
		else{
			Comparable temp1 = (Comparable)arg0;
			Comparable temp2 = (Comparable)node.getData();
			if(temp1.compareTo(temp2) < 0)
				node.leftChild = add(arg0, node.getLeftChild());
			else if(temp1.compareTo(temp2) > 0)
				node.rightChild = add(arg0, node.getRightChild());
			else
				inserted = false;	
		}
		node = balance(node);
		setHeight(node);
		return node;
	}
	public void clear() {
		root = null;
		size = 0;
	}
	public boolean contains(Object obj) {
		if(obj == null)
			throw new IllegalArgumentException("Cannot Add Null Object");

		TreeImpl node = root;
		while(node != null){
			Comparable temp1 = (Comparable)obj;
			Comparable temp2 = (Comparable)node.data;
			if(temp1.compareTo(temp2) < 0)
				node = node.leftChild;
			else if(temp1.compareTo(temp2) > 0)
				node = node.rightChild;
			else
				return true;
		}
		return false;
	}
	public boolean isEmpty() {
		return root == null;
	}
	public Iterator<Object> iterator() {
		return new SetIterator();
	}
	public boolean remove(Object arg0) {
		root = remove(arg0, root);
		if(removed){
			size--;
			return true;
		}else{
			removed = true;
			return false;
		}

	}
	private TreeImpl remove(Object obj, TreeImpl current){

		if(current == null)
			removed = false;
		else {
			Comparable temp1 = (Comparable)obj;
			Comparable temp2 = (Comparable)current.data;
			if(temp1.compareTo(temp2) < 0)
				current.leftChild = remove(obj, current.getLeftChild());
			else if(temp1.compareTo(temp2) > 0)
				current.rightChild = remove(obj, current.rightChild);

			else if(current.leftChild != null && current.rightChild != null){
				current.data = findMin(current.rightChild).data;
				current.rightChild = removeMin(current.rightChild);		
			}
			else
				current = (current.leftChild != null) ? current.leftChild : current.rightChild;
		}
		if(current != null){
			current = balance(current);
			setHeight(current);
		}
		return current;
	}
	public int size() {
		return size;
	}
	public Object[] toArray() {
		Object[] temp = new Object[size];
		Iterator<Object> iterator = new SetIterator();
		int i = 0;
		while(iterator.hasNext())
			temp[i++] = iterator.next();
		return temp;
	}
	private void setHeight(TreeImpl current){
		current.height = getHeight(current)+1;
	}
	private int getHeight(TreeImpl current){
		if(current.leftChild == null && current.rightChild == null)
			return -1;
		if(current.rightChild == null)
			return current.leftChild.height;
		if(current.leftChild == null)
			return current.rightChild.height;
		int HEIGHT = (current.leftChild.height > current.rightChild.height) ? 
				current.leftChild.height : current.rightChild.height;
		return HEIGHT;
	}

	private TreeImpl fixLeft(TreeImpl node){
		if(node.leftChild.getLeftHeight() < node.leftChild.getRightHeight())
			node.leftChild = leftRotate(node.leftChild);	
		return rightRotate(node);
	}
	private TreeImpl fixRight(TreeImpl node){
		if(node.rightChild.getLeftHeight() > node.rightChild.getRightHeight())
			node.rightChild = rightRotate(node.rightChild);	
		return leftRotate(node);
	}
	private TreeImpl leftRotate(TreeImpl node){
		TreeImpl temp = node.rightChild;
		node.rightChild = temp.leftChild;
		temp.leftChild = node;
		setHeight(node);
		return temp;
	}
	private TreeImpl rightRotate(TreeImpl node){
		TreeImpl temp = node.leftChild;
		node.leftChild = temp.rightChild;
		temp.rightChild = node;
		setHeight(node);
		return temp;
	}

	private TreeImpl balance(TreeImpl node){
		if(node.leftChild == null && node.rightChild == null)
			return node;
		else if(Math.abs(node.getLeftHeight() - node.getRightHeight()) > 1){
			return node.getLeftHeight() > node.getRightHeight() ? fixLeft(node) : fixRight(node);
		}
		return node;
	}

	private TreeImpl removeMin(TreeImpl node){
		if(node.leftChild != null){
			node.leftChild = removeMin(node.leftChild);
			node = balance(node);
			setHeight(node);
			return node;
		}else
			return node.rightChild;
	}
	private TreeImpl findMin(TreeImpl node){
		if(node != null)
			while(node.leftChild != null)
				node = node.leftChild;
		return node;
	}
	public String printTree(){
		return printTree(root);
	}
	private String printTree(TreeImpl node){
		if(node == null)
			return "";
		String temp1 = node.leftChild == null ? "" : printTree(node.leftChild);
		String temp2 = node.rightChild == null ? "" : printTree(node.rightChild);
		return "\n"+node.data.toString() + temp1 + temp2;
	}
	public Object[] toArray(Object[] arg0) {
		throw new UnsupportedOperationException();
	}
	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection arg0) {
		throw new UnsupportedOperationException();
	}
	@SuppressWarnings("unchecked")
	public boolean retainAll(Collection arg0) {
		throw new UnsupportedOperationException();
	}
	@SuppressWarnings("unchecked")
	public boolean addAll(Collection arg0) {
		throw new UnsupportedOperationException();
	}
	@SuppressWarnings("unchecked")
	public boolean containsAll(Collection arg0) {
		throw new UnsupportedOperationException();
	}

	private class SetIterator implements Iterator<Object>{

		private Stack<TreeImpl> stack;
		public SetIterator(){
			stack = new Stack<TreeImpl>();
			add(root);
		}
		private void add(TreeImpl node){
			while(node != null){
				add(node.rightChild);
				add(node.leftChild);
				stack.push(node);
				return;
			}
		}
		public boolean hasNext() {
			return !stack.empty();
		}		
		public Object next(){
			return stack.pop().data;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
