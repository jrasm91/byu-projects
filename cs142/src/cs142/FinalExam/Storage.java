package cs142.FinalExam;
import java.util.ArrayList;


public class Storage {
	protected ArrayList<String> cars;
	
	public Storage(){
		cars = new ArrayList<String>();
	}
	
	public ArrayList<String> getList(){
		return cars;
	}
	public void pushFront(String newID){
		cars.add(0, newID);
	}
	public void pushBack(String newID){
		cars.add(newID);
	}
	public String popFront(){
		return cars.remove(0);
	}
	public String popBack(){
		return cars.remove(cars.size()-1);
	}
	public String printList(){
		String returner = "(Size: "+ cars.size()+")";
		for(int i = 0; i < cars.size(); i++){
			returner += " , " + cars.get(i);
		}
		return returner;
	}
	public int getSize(){
		return cars.size();
	}
	public boolean contains(String id){
		for(int i = 0; i < cars.size(); i++){
			if(cars.get(i).equals(id))
				return true;
		}
		return false;
	}
}
