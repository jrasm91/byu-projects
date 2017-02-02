package cs235.xml;

public class NodeImpl implements Node{

	/**
	 * These constants represent the different kinds of XML nodes.
	 * 
	 * DOCUMENT_NODE - a node that represents an XML document (must be the root)
	 * ELEMENT_NODE - a node that represents an XML element
	 * TEXT_NODE - a node that represents text content
	 */
	private NodeImpl parent;
	private NodeImpl prevSibling;
	private NodeImpl nextSibling;
	private NodeImpl firstChild;
	private NodeImpl lastChild;

	public NodeImpl() {
		nextSibling = null;
		prevSibling = null;
		firstChild = null;
		lastChild = null;
		parent = null;
	}
	public int getType() {
		throw new UnsupportedOperationException();
	}

	public Node getParent() {
		return parent;
	}

	public Node getFirstChild() {
		return firstChild;
	}

	public Node getLastChild() {
		return lastChild;
	}

	public Node getNextSibling() {
		return nextSibling;
	}

	public Node getPreviousSibling() {
		return prevSibling;
	}

	public void insertChildBefore(Node newChild, Node child){
		if(newChild == null || child == null)
			throw new IllegalArgumentException();

		NodeImpl cChild = (NodeImpl)child;
		NodeImpl nChild = (NodeImpl)newChild;

		if(cChild.parent != this)
			throw new IllegalArgumentException();

		if(cChild.prevSibling == null){
			cChild.prevSibling = nChild;
			nChild.nextSibling = cChild;
			firstChild = nChild;
			nChild.parent = this;
			nChild.prevSibling = null;			
		}			
		else{
			cChild.prevSibling.nextSibling = nChild;
			nChild.nextSibling = cChild;
			nChild.prevSibling = cChild.prevSibling;
			cChild.prevSibling = nChild;
			nChild.parent = this;
		}
	}

	public void appendChild(Node newChild){
		if(newChild == null)
			throw new IllegalArgumentException();

		NodeImpl cChild = (NodeImpl)newChild;
		if(firstChild == null){
			firstChild = cChild;
			lastChild = cChild;
			cChild.parent = this;
		}
		else{
			lastChild.nextSibling = cChild;
			cChild.prevSibling = lastChild;
			lastChild = cChild;
			cChild.parent = this;
		}
	}

	public void removeChild(Node child){
		if(child == null)
			throw new IllegalArgumentException();
		NodeImpl cChild = (NodeImpl)child;
		if(cChild.parent != this)
			throw new IllegalArgumentException();

		if(firstChild == cChild && lastChild == cChild){
			firstChild = null;
			lastChild = null;
		}
		else{
			if(cChild == firstChild || cChild == lastChild){
				if(firstChild == cChild){
					firstChild = firstChild.nextSibling;
					firstChild.prevSibling = null;
				}
				if(lastChild == cChild){
					lastChild = cChild.prevSibling;
					lastChild.nextSibling = null;
				}
			}
			else{
				cChild.nextSibling.prevSibling = cChild.prevSibling;
				cChild.prevSibling.nextSibling = cChild.nextSibling;
			}
		}
		
		cChild.parent = null;
		cChild.firstChild = null;
		cChild.lastChild = null;
		cChild.prevSibling = null;
		cChild.nextSibling = null;

	}

	public void replaceChild(Node newChild, Node oldChild){
		if(newChild == null || oldChild == null )
			throw new IllegalArgumentException();

		NodeImpl oChild = (NodeImpl)oldChild;
		NodeImpl nChild = (NodeImpl)newChild;
		
		if(oChild.parent != this)
			throw new IllegalArgumentException();

		insertChildBefore(nChild, oChild);
		removeChild(oldChild);
	}

}
