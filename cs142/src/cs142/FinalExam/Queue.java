package cs142.FinalExam;

public class Queue extends Storage{

	public Queue(){
		super();
	}
	
	public void push(String idNum){
		super.pushFront(idNum);
	}
	
	public String pop(){
		return super.popBack();
	}
	public String printList(){
		return "Queue: " + super.printList();
	}

}
