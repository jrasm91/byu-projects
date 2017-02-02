package cs235.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class ParserImpl implements Parser{


	/**
     * Parses the XML document contained in the specified input stream
     * and returns a document tree that represents the document.
     * 
     * @param input an input stream containing an XML document
     * @return the root node of document tree
     * @throws IllegalArgumentException if input is null
     * @throws IOException if an input error occurs while parsing the XML document
     * @throws XMLException if the XML document being parsed is not in valid XML format
     */
	
	private Stack<String> tags;
	private Document root;
	private Node current;
	private boolean quit;
	
	public ParserImpl(){
		root = new DocumentImpl();
		quit = false;
		current = root;
		tags = new Stack<String>();
	}
    
	public Document parse(InputStream input) throws IOException, XMLException{
    	
		if(input == null)
			throw new IllegalArgumentException();
		
		
    	root =  new DocumentImpl();
    	current = root;
    	tags = new Stack<String>();
       	quit = false;
       	
       	TokenizerImpl token = new TokenizerImpl(input);
       	
    	while(!quit)
    		addNode(token);
    	return root;
    }
    
    private void addNode(TokenizerImpl token) throws XMLException{
    	final int START_TAG = 1;
    	final int END_TAG = 2;
    	final int TEXT = 3;
    	final int EOF = 4;
    	
    	try{
    		token.nextToken();
    		int tokenType = token.getTokenType();
    		
    		switch(tokenType){
    		
    		case START_TAG:
    			Element element = ParserFactory.createElement(token.getTagName());
    			for(int i = 0; i < token.getAttributeCount(); i++)
    				element.setAttribute(token.getAttributeName(i), token.getAttributeValue(i));
    			current.appendChild(element);
    			current = current.getLastChild();
    			tags.push(element.getTagName());
    			break;
    			
    		case END_TAG:
    			current = current.getParent();
    			checkBalance(token);
    			tags.pop();
    			break;			
    			
    		case TEXT:
    			Text text = ParserFactory.createText(token.getText());
    			current.appendChild(text);
    			break;
    			
    		case EOF: 
    			if(tags.size() != 0)
    				throw new XMLException();
    			quit = true;
    			break;
    			
    		default:
    			break;
    		}
    	}catch (IOException e){
    		quit = true;
    	}
    }
    
    public void checkBalance(Tokenizer token) throws XMLException{
    	if(tags.size() == 0 || !token.getTagName().equals(tags.peek()))
    		throw new XMLException();
    }
    
}
