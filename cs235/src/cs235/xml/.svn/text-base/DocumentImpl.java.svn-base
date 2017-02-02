
package cs235.xml;

import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * The Document interface represents the root of an XML document tree.
 */
public class DocumentImpl extends NodeImpl implements Document {

	DocumentImpl(){
		super();

	}


	/**
	 * Writes the document in XML format to the specified output stream.
	 * 
	 * @param output the output stream to which the document should be written
	 * @throws IllegalArgumentException if output is null
	 * @throws IOException if an output error occurs while writing to the stream
	 */
	public void write(OutputStream output) throws IOException{

		if(output == null)
			throw new IllegalArgumentException();

		PrintWriter pw = null;
		boolean answer = true;


		try{   
			pw = new PrintWriter(output);
			printTree(pw, this.getFirstChild());
			pw.close();
		}
		catch(NullPointerException e){

		}
	}

	public void printElement(PrintWriter pw, Element element){

		if(element.getAttributeCount() != 0){
			pw.print("<" + element.getTagName());
			for(int i = 0; i<element.getAttributeCount(); i++){
				pw.print(" " + element.getAttributeName(i) + "= '" + element.getAttributeValue(i) + "'");
			}
			pw.print(">");
		}
		else
			pw.print("<" + element.getTagName() + ">");
	}

	public void printTree(PrintWriter pw, Node current){

		if(current == null)
			return;

		if(current.getType() == ELEMENT_NODE){
			Element element = (ElementImpl) current;
			printElement(pw, element);
			if(element.getFirstChild() != null)
				printTree(pw, element.getFirstChild());
			pw.print("</" + element.getTagName() + ">");

			if(element.getNextSibling() != null)
				printTree(pw, element.getNextSibling());
		}

		if(current.getType() == TEXT_NODE){
			Text text = (TextImpl) current;
			pw.print( text.getData());
			if(text.getNextSibling() != null)
				printTree(pw, text.getNextSibling());
		}

	}

	public int getType(){
		return DOCUMENT_NODE;
	}
}
