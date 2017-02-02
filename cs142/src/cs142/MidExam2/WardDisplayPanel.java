package cs142.MidExam2;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class WardDisplayPanel extends JPanel
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// stupid global variables needed throughtout the class
	private JPanel panel, companionPanel;
	private JLabel label, nameStatLabel1, nameStatLabel3, nameStatLabel4,nameStatLabel5, nameStatLabel6, nameStatLabel7;
	private JLabel prospectiveLabel, member1Label, member2Label, companionLabel1, companionLabel2;
	private JButton companionshipButton, perspectiveButton, assignButton, deleteButton;
	private JButton member1Button, member2Button, newCompanionButton, selectMemberButton;
	private JTextField addMaleButton, addFemaleButton;
	private Ward theOne;
	private int member1index, member2index, companionIndex, noTeacherIndex, studentWardIndex;
	private final int HEIGHT = 500;
	private final int WIDTH = 500;

	// constructor for a WardDisplayPanel
	public WardDisplayPanel()
	{
		// initialize all the stuff up above
		theOne = new Ward();
		//adds seven students to the ward
		initialize();
		Student firstOne = theOne.getStudentWard().get(0);
		member1index = 0;
		member2index = 1;
		companionIndex = 0;
		noTeacherIndex = 0;
		studentWardIndex = 0;


		//Add member Bar
		newColorLine(); newLine(5);
		label = new JLabel("Add Member"); add(label);
		label = new JLabel("     Add Male: "); add(label);
		addMaleButton = new JTextField(8); 
		addMaleButton.addActionListener(new TextBoxListener(1));
		add(addMaleButton);
		label = new JLabel("     Add Female: "); add(label);
		addFemaleButton = new JTextField(8); 
		addFemaleButton.addActionListener(new TextBoxListener(2));
		add(addFemaleButton);

		//New Companionship Bar (Members without Companions)
		newColorLine(); newLine(5);
		label = new JLabel("Create New Companionships (Members Without Companions)"); add(label); newLine(5);
		label = new JLabel("Member1:                         Member2" +"                         New Companionship");
		add(label); newLine(2);
		member1Label = new JLabel(theOne.getNoCompanion().get(member1index).getName()); add(member1Label);
		member1Button = new JButton("Next"); add(member1Button);
		member1Button.addActionListener(new ButtonListener(1));
		label = new JLabel("     +     "); add(label);
		member2Label = new JLabel(theOne.getNoCompanion().get(member2index).getName()); add(member2Label);
		member2Button = new JButton("Next");add(member2Button);
		member2Button.addActionListener(new ButtonListener(2));
		label = new JLabel("     =     "); add(label);
		newCompanionButton = new JButton("Create"); add(newCompanionButton);
		newCompanionButton.addActionListener(new ButtonListener(3));
		newLine(5);

		//Companionship Bar
		newColorLine(); newLine(5);
		label = new JLabel("Create New Assignments (Members without Home/Visiting teachers)"); add(label); newLine(5);
		setPreferredSize(new Dimension(HEIGHT, WIDTH));
		label = new JLabel("Companionship:             Perspective Assignment" +"             New Assignment");
		add(label);newLine(2);
		companionPanel = new JPanel();
		companionLabel1 = new JLabel(theOne.getCompanion1().get(0).getName());
		companionPanel.add(companionLabel1);
		companionLabel2 = new JLabel(theOne.getCompanion2().get(0).getName());
		companionPanel.add(companionLabel2); add(companionPanel);
		companionshipButton = new JButton("Next"); add(companionshipButton); 
		companionshipButton.addActionListener(new ButtonListener(4));
		prospectiveLabel = new JLabel("   +    " + firstOne.getName());add(prospectiveLabel);
		perspectiveButton = new JButton("Next");add(perspectiveButton);
		perspectiveButton.addActionListener(new ButtonListener(5));
		label = new JLabel("  =      ");add(label);
		assignButton = new JButton("Assign");add(assignButton); newLine(5);
		assignButton.addActionListener(new ButtonListener(6));

		//Selected Member's Info Bar
		newColorLine(); newLine(5);

		label = new JLabel("Selected Member's Info"); add(label);
		selectMemberButton = new JButton("Next"); add(selectMemberButton);
		selectMemberButton.addActionListener(new ButtonListener(7));
		deleteButton = new JButton("Delete Member"); add(deleteButton);newLine(5);
		deleteButton.addActionListener(new ButtonListener(8));
		nameStatLabel1 = new JLabel(); add(nameStatLabel1); newLine(1);
		nameStatLabel3 = new JLabel(); add(nameStatLabel3); newLine(1);
		nameStatLabel4 = new JLabel(); add(nameStatLabel4); newLine(1);
		nameStatLabel5 = new JLabel();
		nameStatLabel6 = new JLabel();
		nameStatLabel7 = new JLabel();
		if(!firstOne.getGender())
		{
			add(nameStatLabel5);newLine(1); newLine(1);
			add(nameStatLabel7);newLine(1); newLine(1);
			add(nameStatLabel6);newLine(1); newLine(1);
			nameStatLabel6.setText(" ");
		}
		else
		{
			add(nameStatLabel5);newLine(1); newLine(1);
			add(nameStatLabel7);newLine(1); newLine(1);
			add(nameStatLabel6);newLine(1); newLine(1);
			nameStatLabel5.setText("");
			nameStatLabel7.setText("");
		}
		newColorLine();
		newLine(5);
		
		theOne.clear();
	}
	//method used at beginning. Initializes ward with 7 new students
	public void initialize()
	{
		Student David = new Student("David", true);
		Student Jason = new Student("Jason", true);
		theOne.add(new Student("Hannah", false));
		theOne.add(new Student("Mike", true));
		theOne.add(new Student("Allyson", false));
		theOne.add(new Student("Jessica", false));
		theOne.add(new Student("Jacob", true));
		theOne.add(David);
		theOne.add(Jason);
		David.setCompanion(Jason);
		Jason.setCompanion(David);
		theOne.sort();
	}
	// method for a new line in the GUI
	public void newLine(int depth)
	{
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH, depth));
		add(panel);
	}
	// method that adds a new line that is filled black in the GUI
	public void newColorLine()
	{
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setPreferredSize(new Dimension(WIDTH, 5));
		add(panel);
	}
	//private class for the two textboxes used to add members to the ward
	private class TextBoxListener implements ActionListener
	{
		//numer represents which textbox
		int number;
		public TextBoxListener(int num)
		{
			number = num;
		}
		//method for ActionEvents
		public void actionPerformed(ActionEvent event)
		{
			//if number = 1 -> add male, 2-> add female
			if(number ==1)
				theOne.getStudentWard().add(0, new Student(event.getActionCommand(), true));
			if(number ==2)
				theOne.getStudentWard().add(0,new Student(event.getActionCommand(), false));
			theOne.sort();
			member1index = 0;
			companionIndex = 0;
			noTeacherIndex = 0;
			studentWardIndex = 0;
			updateNewCompanionshipButtons();
			updateNewAssignmentButtons();
			updateStats();
		}
	}
	//private class for the rest of the buttons
	private class ButtonListener implements ActionListener
	{
		//number represts which button was presssed
		int number;
		public ButtonListener(int num)
		{
			number = num;
		}
		//method that handles ActionEvents
		public void actionPerformed(ActionEvent arg0)
		{
			//if number is a 1, go to next member in list of members without companions
			if(number ==1)
			{
				if(theOne.getNoCompanion().size() > 1)
				{
					do{
						member1index++;
						if(member1index >= theOne.getNoCompanion().size())
							member1index = 0;
						if(member1index == member2index)
							member1index++;
					}while(member1index == theOne.getNoCompanion().size());

				}
			}
			//if number == 2, go to the next member on second label
			if(number ==2)
			{
				if(theOne.getNoCompanion().size() > 1)
				{
					do{
						member2index++;
						if(member2index >= theOne.getNoCompanion().size())
							member2index = 0;
						if(member1index == member2index)
							member2index++;
					}while(member2index == theOne.getNoCompanion().size());
				}
			}
			//if number ==3, check to see if you can create a new companionship
			if(number ==3)
			{
				if(theOne.getNoCompanion().size() > 1)
				{
					Student member1 = theOne.getNoCompanion().get(member1index);
					Student member2 = theOne.getNoCompanion().get(member2index);
					if(member1.getGender() == member2.getGender())
					{
						member1.setCompanion(member2);
						member2.setCompanion(member1);
					}
					member1index = 0;
					member2index = 1;
				}
			}
			//if number ==4, go to next set of companions if possible
			if(number ==4)
			{
				if(theOne.getCompanion1().size() > 0 && theOne.getCompanion2().size() > 0)
				{
					companionIndex++;
					if(companionIndex == theOne.getCompanion1().size())
						companionIndex = 0;
				}
			}
			// go to next selection in list of students without home and/or visiting teachers
			if(number ==5)
			{
				if(theOne.getNoTeacher().size() > 0)
				{
					noTeacherIndex++;
					if(noTeacherIndex == theOne.getNoTeacher().size())
						noTeacherIndex = 0;
				}
			}
			// if possible create new assignment of visiting/ home teacher with selection
			if((number ==6) && (theOne.getNoTeacher().size() != 0) && (theOne.getCompanion1().size() !=0))
			{
				Student student1 = theOne.getNoTeacher().get(noTeacherIndex);
				Student student2 = theOne.getCompanion1().get(companionIndex);
				Student student3 = theOne.getCompanion2().get(companionIndex);
				if(!student2.getGender() && !student1.getGender() && (student1.getVisitTeachers()[0] == null) 
						&& !(student1.getName().equals(student2.getName())||(student1.getName().equals(student3.getName()))))
				{
					student1.addVisitTeachers(student2, student3);
					noTeacherIndex = 0;
					companionIndex = 0;
					studentWardIndex = theOne.getStudentWard().indexOf(student1);
				}
				if((student2.getGender() && student1.getHomeTeachers()[0] == null)
						&& (!student1.getName().equals(student2.getName())&&(!student1.getName().equals(student3.getName()))))
				{
					student1.addHomeTeachers(student2, student3);
					noTeacherIndex = 0;
					companionIndex = 0;
					studentWardIndex = theOne.getStudentWard().indexOf(student1);
				}
			}
			//if number ==7, scroll to next student and display their info below on the GUI
			if(number ==7)
			{

				studentWardIndex++;
				if(studentWardIndex >= theOne.getStudentWard().size())
					studentWardIndex = 0;
			}
			//if number == 8, delete the student and update the assignments
			if(number == 8)
			{
				if(theOne.getStudentWard().size() > 0)
				{
					theOne.remove(theOne.getStudentWard().get(studentWardIndex));
					studentWardIndex = 0;
				}
			}
			
			theOne.sort();
			updateNewCompanionshipButtons();
			updateNewAssignmentButtons();
			updateStats();
		}
	}
	
	private void updateStats()
	{
		if(theOne.getStudentWard().size() > 0)
		{
			Student firstOne = theOne.getStudentWard().get(studentWardIndex);
			nameStatLabel1.setText("     Name: " + firstOne.getName());  
			nameStatLabel3.setText(firstOne.DisplayString()[2]);  
			nameStatLabel4.setText(firstOne.DisplayString()[3]); 
			if(!firstOne.getGender())
			{
				nameStatLabel5.setText(firstOne.DisplayString()[4]);
				nameStatLabel7.setText(firstOne.DisplayString()[6]);
				nameStatLabel6.setText("Home Teaching Assignments: Oh Darn.");

			}
			else
			{
				nameStatLabel6.setText(firstOne.DisplayString()[5]);
				nameStatLabel5.setText("Visiting Teachers: If only...");
				nameStatLabel7.setText("Visiting Teaching Assignment(s): No Assignments.");
			}
		}
		if(theOne.getStudentWard().size() == 0)
		{
			nameStatLabel1.setText(" "); 
			nameStatLabel3.setText(" "); 
			nameStatLabel4.setText(" "); 
			nameStatLabel5.setText(" "); 
			nameStatLabel6.setText(" "); 
			nameStatLabel7.setText(" "); 
		}
	}
	private void updateNewAssignmentButtons()
	{
		if(theOne.getCompanion1().size() == 0 )
		{
			companionLabel1.setText(null);
			companionLabel2.setText(null);	
		}
		else
		{
			companionLabel1.setText(theOne.getCompanion1().get(companionIndex).getName());
			companionLabel2.setText(theOne.getCompanion2().get(companionIndex).getName());
		}
		if(theOne.getNoTeacher().size() == 0 )
			prospectiveLabel.setText("   +         ");
		else
			prospectiveLabel.setText("   +    " + theOne.getNoTeacher().get(noTeacherIndex).getName());
		theOne.sort();
	}
	private void updateNewCompanionshipButtons()
	{
		if(theOne.getNoCompanion().size() == 0)
		{
			member1Label.setText(null);
			member2Label.setText(null);
		}
		else if(theOne.getNoCompanion().size() == 1)
		{
			member1Label.setText(theOne.getNoCompanion().get(0).getName());
			member2Label.setText(null);
		}
		else
		{
			member1Label.setText(theOne.getNoCompanion().get(member1index).getName());
			member2Label.setText(theOne.getNoCompanion().get(member2index).getName());
		}
	}
}
