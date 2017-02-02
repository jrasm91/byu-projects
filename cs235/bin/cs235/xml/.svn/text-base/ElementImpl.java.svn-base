package cs235.xml;

import java.util.ArrayList;
import java.util.List;

public class ElementImpl extends NodeImpl implements Element{
	
	private String name;
	private List<String> nameList;
	private List<String> valueList;
	
	public ElementImpl(String name){
		super();
		this.name = name;
		nameList = new ArrayList<String>();
		valueList = new ArrayList<String>();
	}
	
	public int getType() {
		return ELEMENT_NODE;
	}
	
    public String getTagName(){
    	return name;
    }
   
    public void setTagName(String tagName){
    	if(tagName == null)
    		throw new IllegalArgumentException();
    	name = tagName;
    }

    public String getAttributeValue(String name){
    	if(name == null)
    		throw new IllegalArgumentException();
    	if(valueList.contains(nameList.indexOf(name)))
    			return valueList.get(nameList.indexOf(name));
    	return null;
    }
    
    public int getAttributeCount(){
    	return nameList.size();
    }
    
    public String getAttributeName(int i){
    	if(i >= nameList.size() || i < 0)
    		throw new IllegalArgumentException();
    	return nameList.get(i);
    }
    
    public String getAttributeValue(int i){
    	if(i >= valueList.size() || i < 0)
		throw new IllegalArgumentException();
	return valueList.get(i);
    }
    
    public void setAttribute(String name, String value){
    	if(name == null || value == null)
    		throw new IllegalArgumentException();
    	nameList.add(name);
    	valueList.add(value);
    }
	

}
