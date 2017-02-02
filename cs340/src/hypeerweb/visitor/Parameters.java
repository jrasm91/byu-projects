package hypeerweb.visitor;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper for a map. The map is used by Visitor objects to pass information
 * along during Send and Broadcast.
 * 
 * @author Adam Christiansen
 */
public class Parameters {

	private Map<String, Object> parameters;

	/**
	 * Default constructor for Parameters.
	 * 
	 * @pre None.
	 * @post Constructs a new Parameters object.
	 */
	public Parameters() {
		parameters = new HashMap<String, Object>();
	}

	/**
	 * @obvious
	 */
	public Object get(String key) {
		return parameters.get(key);
	}

	/**
	 * @obvious
	 */
	public boolean containsKey(String key) {
		return parameters.containsKey(key);
	}

	/**
	 * @obvious
	 */
	public void set(String key, Object value) {
		parameters.put(key, value);
	}
}
