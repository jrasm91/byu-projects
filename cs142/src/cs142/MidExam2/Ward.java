package cs142.MidExam2;
import java.util.ArrayList;


public class Ward 
{
	//Arrays for the different types of people in the ward
	// studentWard contains all ward members
	private ArrayList<Student> studentWard;
	//companion1 (one companion) and companion2 (the other companion) contain companionships
	private ArrayList<Student> companion1;
	private ArrayList<Student> companion2;
	//noCompanion contains students who do not have companions
	private ArrayList<Student> noCompanion;
	// noTeacher contains students without home teachers and/or visiting teachers
	private ArrayList<Student> noTeacher;
	
	//constructor for a new Ward
	public Ward()
	{
		//initialize all the arrays
		studentWard = new ArrayList<Student>();
		companion1 = new ArrayList<Student>();
		companion2 = new ArrayList<Student>();
		noCompanion = new ArrayList<Student>();
		noTeacher = new ArrayList<Student>();
	}
	// getters for all the arrays
	public ArrayList<Student> getStudentWard(){ return studentWard; }
	public ArrayList<Student> getCompanion1(){ return companion1; }
	public ArrayList<Student> getCompanion2(){ return companion2; }
	public ArrayList<Student> getNoCompanion(){ return noCompanion; }
	public ArrayList<Student> getNoTeacher(){ return noTeacher; }

	// add(Student) -> adds Student to student ward
	public void add(Student newStudent)
	{
		studentWard.add(newStudent);
	}
	//remove(Student) -> removes student from wardList and responsibilities become null
	public void remove(Student oldStudent)
	{
		
		if(oldStudent.getHomeTeachers()[0] != null || oldStudent.getHomeTeachers()[1] != null)
		{
		oldStudent.getHomeTeachers()[0].removeHomeTeachee(oldStudent);
		oldStudent.getHomeTeachers()[1].removeHomeTeachee(oldStudent);
		}
		if(oldStudent.getVisitTeachers()[0] != null || oldStudent.getVisitTeachers()[1] != null)
		{
		oldStudent.getVisitTeachers()[0].removeVisitTeachee(oldStudent);
		oldStudent.getVisitTeachers()[1].removeVisitTeachee(oldStudent);
		}
		if(oldStudent.getHomeTeachees().size() > 0)
			oldStudent.removeHomeTeachees();
		if(oldStudent.getVisitTeachees().size() > 0)
			oldStudent.removeVisitTeachees();
		
		if(oldStudent.getCompanion() != null)
		{
			oldStudent.getCompanion().getHomeTeachees().clear();
			oldStudent.getCompanion().getVisitTeachees().clear();
			oldStudent.getCompanion().setCompanion(null);
			oldStudent.setCompanion(null);
		}
		
		studentWard.remove(oldStudent);
		sort();
	}
	//method that sorts the ward list
	public void sort()
	{
		sort1(); // sort1(); -> sorts ward into companionships (companion1/companion2)/noCompanions
		sort2(); // sort2(); -> sorts ward into those with home/visiting teachers and those without
	}
	//sorts the ward into lists (home/visiting teachers vs no home/visiting teachers)
	public void sort2()
	{
		ArrayList<Student> tempList = new ArrayList<Student>();
		for(int i = 0; i < studentWard.size(); i++)
		{
			Student nextStudent = studentWard.get(i);
			tempList.add(nextStudent);
		}
		noTeacher.clear();
		for(int i = 0; i < tempList.size(); i++)
		{
			Student temp = tempList.get(i);
			if(temp.getHomeTeachers()[0] == null && temp.getHomeTeachers()[1] == null)
				noTeacher.add(temp);
			if((temp.getVisitTeachers()[0] == null && temp.getVisitTeachers()[1] == null) 
					&& !temp.getGender() && !noTeacher.contains(temp))
				noTeacher.add(temp);
		}
	}
	//sorts the ward into lists (companions vs. no companions)
	public void sort1()
	{
		//copies the student array into temp array
		ArrayList<Student> tempList = new ArrayList<Student>();
		for(int i = 0; i < studentWard.size(); i++)
		{
			Student nextStudent = studentWard.get(i);
			tempList.add(nextStudent);
		}
		companion1.clear();
		companion2.clear();
		//checks for companions and adds them to the companion1/2 list
		for(int i = 0; i < tempList.size(); i++)
		{
			Student temp = tempList.get(i);
			if((temp.getCompanion() != null))
			{
				companion1.add(temp);
				companion2.add(temp.getCompanion());
				tempList.remove(temp);
				tempList.remove(temp.getCompanion());
				i--;
			}
		}
		//everyone not in a companionship goes into noCompanion list
		noCompanion = tempList;
	}
	
	public void clear()
	{
		studentWard.clear();
		sort();
	}
}
