import java.util.ArrayList;


public class Node {

	
	private String text;
	private double value;
	private int col;
	private Node parent;
	
	private ArrayList<Node> children;
	public Node(Node parent, String text, double value, int col) {
//		System.out.println(String.format("Node('%s') --> Node('%s') [val '%s' | col '%d']", parent.getText(), text, value, col));
		this.parent = parent;
		this.text = text;
		this.children = new ArrayList<Node>();
		this.value = value;
		this.col = col;
	}
	
	public Node(String value){
		this.parent = this;
		this.text = value;
		this.children = new ArrayList<Node>();
		this.value = -1;
		this.col = -1;
	}

	public String getText(){
		return text;
	}
	
	public double getValue(){
		return value;
	}
	
	public int getCol(){
		return col;
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public void addChild(Node child){
		children.add(child);
	}
	
	public String makeDecision(ArrayList<String> queries){
		for(Node c: children){
			if(c.getText().equals(queries.get(0)))
				queries.remove(0);
				return c.makeDecision(queries);
		}
		return text;
	}
	
	public double makeDecision(double[] queries){
//		System.out.print(String.format("(%s) --> ", text));
		if(children.size() == 1){
//			System.out.println(children.get(0).getText());
			return children.get(0).getValue();
		}
		for(Node c: children){
			if(c.getValue() == queries[c.getCol()])
				return c.makeDecision(queries);
		}
		return value;
//		throw new IllegalStateException("Should Not Be Here! --> " + this);
	}

	@Override
	public String toString() {
		String childrenS = "";
		for(Node c: children)
			childrenS += " " + c.getText();
		
		
		return "Node [text=" + text + ", value=" + value + ", col="
				+ col + ", parent=" + parent.text + ", children=" + childrenS
				+ "]";
	}
	
	
}
