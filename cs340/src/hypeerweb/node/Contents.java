package hypeerweb.node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Contents {

	private Map<String, Object> contents;

	public Contents() {
		contents = new HashMap<String, Object>();
		
	}
	public Set<Map.Entry<String, Object>> set() {
		return contents.entrySet();
	}
	

	public Object get(String key) {
		return contents.get(key);
	}

	public boolean containsKey(String key) {
		return contents.containsKey(key);
	}

	public void set(String key, Object value) {
		contents.put(key, value);
	}
	
	public void clear() {
		contents.clear();
	}
}
