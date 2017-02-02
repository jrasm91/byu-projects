package cs235.hash;

public class Package {
	
	private Object key;
	private Object value;
	
	public Package(Object key, Object value){
		this.key = key;
		this.value = value;
	}
	public Object getKey(){
		return key;
	}
	public Object getValue(){
		return value;
	}
	public boolean equals(Object obj){
		if(!(obj instanceof Package))
			return false;
		return key.equals(((Package)obj).key);
	}
	public int hashCode(){
		return key.hashCode();
	}
	public String toString(){
		return key + "\t" + value;
	}
}
