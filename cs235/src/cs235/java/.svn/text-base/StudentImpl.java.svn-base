
package cs235.java;

public class StudentImpl extends PersonImpl implements Student {

	private String major;
	private double gpa;

	public StudentImpl(String id, String name, String major, double gpa){
		super(id, name);
		setMajor(major);
		setGPA(gpa);
	}
	public String getMajor(){
		return major;
	}
	public void setMajor(String major){
		if(major == null)
			throw new IllegalArgumentException();
		else
			this.major = major;
	}
	public double getGPA(){
		return gpa;
	}
	public void setGPA(double gpa){
		final double MIN = 0.0;
		final double MAX = 4.0;
		if(gpa <= MAX && gpa >= MIN)
			this.gpa = gpa;
		else
			throw new IllegalArgumentException();
	}
	public String toString(){
		return "student\n" + super.toString() + major + "\n" + gpa + "\n";
	}
}


