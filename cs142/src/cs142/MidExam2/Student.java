package cs142.MidExam2;
import java.util.ArrayList;


public class Student 
{
	private String name;
	private boolean gender; // true = male, false = female
	private Student companion;
	private Student[] homeTeachers;
	private Student[] visitTeachers;
	private ArrayList<Student> homeTeachees;
	private ArrayList<Student> visitTeachees;

	public Student(String name, boolean gender)
	{
		this.name = name;
		this.gender = gender;
		homeTeachers = new Student[2]; // you can only have 2 home teachers
		visitTeachers = new Student[2]; // you can only have 2 visiting teachers
		homeTeachees = new ArrayList<Student>(); // list of home teaching responsibilities
		visitTeachees = new ArrayList<Student>(); // list of visiting teach responsibilities
		companion = null; // initially start without a companian 
	}
	//method that returns the name of student (with "(M)" or "(F)" attached)
	public String getName()
	{
		String temp = name;
		if(gender)
			temp += "(M)";
		else
			temp += "(F)";
		return temp;

	}
	//method the returns the gender of the student
	public boolean getGender()
	{
		return gender;
	}
	// getter for companion
	public Student getCompanion()
	{
		return companion;
	}
	// setter for companion
	public void setCompanion(Student newCompanion)
	{
		companion = newCompanion;
	}
	// method that returns an array of strings representing the student
	public String[] DisplayString()
	{
		String companionString = "No Companion";
		String genderString = "Male";
		String homeTeacher1 = "No Home Teachers";
		String homeTeacher2 = "";
		String visitTeacher1 = "No Visiting Teachers";
		String visitTeacher2 = "";
		String[] theReturn = new String[7];

		theReturn[0] = "     Name: " + name;
		if(!gender)
			genderString ="Female";
		theReturn[1] = "     Gender: " + genderString;
		if(companion != null)
			companionString = companion.getName();
		theReturn[2] = "     Companion: " + companionString;
		if(homeTeachers[0] != null)
		{
			homeTeacher1 = homeTeachers[0].getName() + ", ";
			homeTeacher2 = homeTeachers[1].getName();
		}
		theReturn[3] = "     Home Teachers: " + homeTeacher1 + homeTeacher2;
		if(visitTeachers[0] != null)
		{
			visitTeacher1 = visitTeachers[0].getName() + ", ";
			visitTeacher2 = visitTeachers[1].getName();
		}
		theReturn[4] = "     Visiting Teachers: " + visitTeacher1 + visitTeacher2;
		theReturn[5] = "     Home Teaching Assignment(s): " + homeString();
		theReturn[6] = "     Visiting Teaching Assignment(s): " + visitString();


		return theReturn;
	}
	// method to convert home teaching assignments into a string
	public String homeString()
	{
		String returner;
		if(homeTeachees.size() == 0)
			returner = "No Assignments";
		else
		{
			returner = homeTeachees.get(0).getName();
			for(int i = 1; i < homeTeachees.size(); i++)
			{
				returner += ", "  + homeTeachees.get(i).getName();
			}
		}
		return returner;
	}
	// method to convert visiting teaching assignments into a string
	public String visitString()
	{
		String returner;
		if(visitTeachees.size() == 0)
			returner = "No Assignments";
		else
		{
			returner = visitTeachees.get(0).getName();
			for(int i = 1; i < visitTeachees.size(); i++)
			{
				returner += ", "  + visitTeachees.get(i).getName();
			}
		}
		return returner;
	}

	/////////////////////////////////
	// getter for home teachers
	public Student[] getHomeTeachers()
	{
		return homeTeachers;
	}
	// method to add home teachers
	public void addHomeTeachers(Student student1, Student student2)
	{

		homeTeachers[0] = student1;
		homeTeachers[1] = student2;
		student1.addHomeTeachee(this);
		student2.addHomeTeachee(this);

	}
	// method to remove home teachers
	public void removeHomeTeachers()
	{
		homeTeachers[0] = null;
		homeTeachers[1] = null;
	}
	// getter for home teaching assignments
	public ArrayList<Student> getHomeTeachees()
	{
		return homeTeachees;
	}
	// method to remove home teaching assignment (oldStudent)
	public void removeHomeTeachee(Student oldStudent)
	{
		homeTeachees.remove(oldStudent);
	}
	// method to remove self as hometeacher of others
	public void removeHomeTeachees()
	{
		for(int i = 0; i < homeTeachees.size(); i++)
		{
			Student compare = homeTeachees.get(i);
			compare.removeHomeTeachers();
		}
		homeTeachees = null;
	}
	// method to add home teaching assignment
	public void addHomeTeachee(Student newStudent)
	{
		if(gender == true)
			homeTeachees.add(newStudent);
	}

	/////////////////////////////////


	//all the exact same stuff for visiting teachers
	public Student[] getVisitTeachers()
	{
		return visitTeachers;
	}
	public void addVisitTeachers(Student student1, Student student2)
	{

		visitTeachers[0] = student1;
		visitTeachers[1] = student2;
		student1.addVisitTeachee(this);
		student2.addVisitTeachee(this);

	}
	public void removeVisitTeachers()
	{
		visitTeachers[0] = null;
		visitTeachers[1] = null;
	}
	public ArrayList<Student> getVisitTeachees()
	{
		return visitTeachees;
	}
	public void removeVisitTeachee(Student oldStudent)
	{
		homeTeachees.remove(oldStudent);
	}
	public void removeVisitTeachees()
	{
		for(int i = 0; i < visitTeachees.size(); i++)
		{
			Student compare = visitTeachees.get(i);
			compare.removeHomeTeachers();
		}
		visitTeachees = null;
	}
	public void addVisitTeachee(Student newStudent)
	{
		if((gender == false) && (newStudent.getGender() == false))
			visitTeachees.add(newStudent);
	}

}

