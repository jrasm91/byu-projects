package cs142.FinalExam;

public class Stack extends Storage{
	
	public Stack(){
		super();
	}
	public void push(String idNum){
		super.pushFront(idNum);
	}
	public String pop(){
		return super.popFront();
	}
	public String printList(){
		return "Stack: " + super.printList();
	}
}
