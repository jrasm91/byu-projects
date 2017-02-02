package cs142.FinalExam;

public class Deque extends Storage{

	public Deque(){
		super();
	}
	public void pushLeft(String idNum){
		super.pushFront(idNum);
	}
	public void pushRight(String idNum){
		super.pushBack(idNum);
	}
	public String popLeft(){
		return super.popFront();
	}
	public String popRight(){
		return super.popBack();
	}
	public String printList(){
		return "Deque: " + super.printList();
	}
	
	// jason's test comment
}
