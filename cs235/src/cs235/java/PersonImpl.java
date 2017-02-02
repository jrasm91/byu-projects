

package cs235.java;

public class PersonImpl  implements Person, Comparable{

	private String id;
	private String name;

	public PersonImpl(String id, String name){
		setID(id);
		setName(name);
	}

	public String getID(){
		return id;
	}

	public void setID(String id){
		if(id == null)
			throw new IllegalArgumentException();
		else
			this.id = id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		if(name == null)
			throw new IllegalArgumentException();
		else
			this.name = name;
	}

	public String toString(){
		return id + "\n" + name + "\n";
	}

	public boolean equals(Object obj){
		if(((Person)obj).getID().equals(id))
			return true;
		return false;
	}
	public int compareTo(Object arg0) {
			return id.compareTo(((Person)arg0).getID());
	}
}


