package cs235.mindreader;

public class Counter {
	
	private int heads;
	private int tails;
	
	Counter(){
		heads = 0;
		tails = 0;
	}
	Counter(int heads, int tails){
		this.heads = heads;
		this.tails = tails;
	}
	
	public int getHeads(){
		return heads;
	}
	public int getTails(){
		return tails;
	}
	public void add(String coin){
		if(!(coin.equals("tails")||coin.equals("heads")))
			throw new IllegalArgumentException();
		if(coin.equals("tails"))
			tails++;
		else 
			heads++;
	}
	public String prediction(){
		if(heads >= tails)
			return "heads";
		else
			return "tails";
	}
	
	public String toString(){
		return " " + heads + " " + tails;
	}

}
