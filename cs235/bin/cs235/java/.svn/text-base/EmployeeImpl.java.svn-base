
package cs235.java;

public class EmployeeImpl extends PersonImpl implements Employee{

	private String office;

	public EmployeeImpl(String id, String name, String office){
		super(id, name);
		setOffice(office);
	}
	public String getOffice(){
		return office;
	}
	public void setOffice(String office){
		if(office == null)
			throw new IllegalArgumentException();
		else
			this.office = office;
	}
	public String toString(){
		return "employee\n" +super.toString() + office + "\n";
	}
}
