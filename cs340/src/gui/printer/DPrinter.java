package gui.printer;

/**
 * interface for debug printers to keep the printers standardized
 * 
 * @author Matthew Smith
 *
 */
public interface DPrinter 
{
	/**
	 * Prints the message to the printer
	 * 
	 * @param msg
	 */
	public void print(Object msg);
	
	/**
	 * Creates a new line
	 */
	public void println();
	
	/**
	 * Prints the message to printer followed by a new line
	 * @param msg
	 */
	public void println(Object msg);

}
