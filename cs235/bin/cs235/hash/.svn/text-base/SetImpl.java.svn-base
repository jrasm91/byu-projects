
package cs235.hash;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SetImpl implements java.util.Set{

	private int size;
	private List[] list;
	private final int DEFAULT_SIZE = 11;

	public SetImpl(){
		size = 0;
		list = new LinkedList[DEFAULT_SIZE];
	}
	public boolean add(Object o){
		if(o == null)
			return false;
		int hashValue = getHashValue(o);	
		if(list[hashValue] == null)
			list[hashValue] = new LinkedList<Object>();
		if(list[hashValue].contains(o))
			return false;
		list[hashValue].add(o);
		size++;
		rehash();
		return true;
	}
	public void clear(){
		list = new LinkedList[DEFAULT_SIZE];
		size = 0;
	}
	public boolean contains(Object o){
		if(o == null || list[getHashValue(o)] == null)
			return false;
		return list[getHashValue(o)].contains(o);
	}
	public boolean isEmpty(){
		return size == 0;
	}
	public Iterator iterator(){
		return new SetImplIterator();
	}
	public boolean remove(Object o){
		if(o == null || list[getHashValue(o)] == null)
			return false;
		if(!list[getHashValue(o)].remove(o))
			return false;
		size--;
		return true;
	}
	public Object getCorrespondingValue(Object key){

		if(key == null || list[getHashValue(key)] == null)
			return null;
		int num = list[getHashValue(key)].indexOf(new Package(key, null));
		if(num < 0)
			return null;
		return ((Package)(list[getHashValue(key)].get(num))).getValue();
	}
	public int size(){
		return size;
	}
	public Object[] toArray(){
		Object[] obj = new Object[list.length];
		Iterator iter = new SetImplIterator();
		int i = 0;
		while(iter.hasNext())
			obj[i++] = iter.next();
		return obj;
	}
	private int getHashValue(Object o){
		return Math.abs(o.hashCode() % list.length);
	}
	private void rehash(){
		if(size <= list.length)
			return ;
		SetImplIterator iter = (SetImplIterator)iterator();
		list = new LinkedList[list.length * 2 + 1];
		while(iter.hasNext()){
			add(iter.next());
			size--;
		}
	}
	public boolean addAll(Collection c){
		throw new UnsupportedOperationException();
	}
	public boolean containsAll(Collection c){
		throw new UnsupportedOperationException();
	}
	public boolean removeAll(Collection c){
		throw new UnsupportedOperationException();
	}
	public boolean retainAll(Collection c){
		throw new UnsupportedOperationException();
	}
	public Object[] toArray(Object[] array){
		throw new UnsupportedOperationException();
	}
	private class SetImplIterator implements java.util.Iterator{

		private List linked;
		public SetImplIterator(){
			linked = new LinkedList();
			for(int i = 0; i < list.length; i++)
				if(list[i] != null)
					for(int j = 0; j < list[i].size(); j++)
						linked.add(list[i].get(j));
		}
		public boolean hasNext() {
			return !(linked.size() == 0);
		}
		public Object next() {
			return linked.remove(0);
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
