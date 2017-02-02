package cs235.hash;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapImpl implements java.util.Map{

	private Set impl;

	public MapImpl(){
		impl = new SetImpl();
	}
	public SetImpl getSet(){
		return (SetImpl)impl;
	}
	public void clear(){
		impl = new SetImpl();
	}
	public boolean isEmpty(){
		return impl.size() != 0;
	}
	public Object get(Object key){
		return ((SetImpl)impl).getCorrespondingValue(key);
	}
	public Object put(Object key, Object value){
		impl.add(new Package(key, value));
		return key;
	}
	public Object remove(Object key){
		impl.remove(new Package(key, null));
		return key;
	}
	public int size(){
		return impl.size();
	}
	public void printMap(){
		java.util.Iterator iter = impl.iterator();
		while(iter.hasNext()){
			Package pack = (Package)iter.next();
			System.out.println("Key: " + pack.getKey() + "\tValue: " + pack.getValue());
		}
	}
	public boolean containsKey(Object key){
		return keySet().contains(key);
	}
	public boolean containsValue(Object value){
		return entrySet().contains(value);
	}
	public Set entrySet(){
		Set temp = new SetImpl();
		java.util.Iterator<Object> iter = impl.iterator();
		while(iter.hasNext())
			temp.add(((Package)iter.next()).getValue());
		return temp;
	}
	public Set<Object> keySet(){
		Set temp = new SetImpl();
		java.util.Iterator<Object> iter = impl.iterator();
		while(iter.hasNext())
			temp.add(((Package)iter.next()).getKey());
		return temp;
	}
	public void putAll(Map t){
		throw new UnsupportedOperationException();
	}
	public Collection values(){
		throw new UnsupportedOperationException();
	}
}
