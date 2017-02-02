package cs235.xml;

public class TextImpl extends NodeImpl implements Text {
	
	private String data;
	
	public TextImpl(String data){
		super();
		this.data = data;
	}
	public int getType() {
		return TEXT_NODE;
	}
	
    public String getData(){
    	return data;
    }
    
    public void setData(String data){
    	if(data == null)
    		throw new IllegalArgumentException();
    	this.data = data;
    }

}
