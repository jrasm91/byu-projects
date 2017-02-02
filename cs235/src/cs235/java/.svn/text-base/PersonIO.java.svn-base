

package cs235.java;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PersonIO {

	public static boolean save(Person[] people, String fileName) {
		
		if(fileName == null || people == null)
			throw new IllegalArgumentException();

		try{
			PrintWriter pw = new PrintWriter(new FileWriter(fileName));
			pw.println("" + people.length);
			for(int i = 0; i < people.length; i++)
				pw.println(people[i]);
			pw.close();
			return true;
		}
		catch(IOException e){
			System.err.println("ERROR: File could not be written");
			return false;
		}
	}

	public static Person[] load(String fileName) {

		if(fileName == null)
			throw new IllegalArgumentException();
		
		try{
			Scanner scan = new Scanner(new FileReader(fileName));
			PersonImpl[] people = new PersonImpl[scan.nextInt()];
			for(int i = 0; i< people.length; i++){
				if(scan.next().equals("student")){
					String stID = scan.next();
					scan.nextLine();
					String stName = scan.nextLine();
					String stMajor = scan.nextLine();
					double stGPA = scan.nextDouble();
					people[i] = new StudentImpl(stID, stName, stMajor, stGPA); 
					//System.out.println("ID: "+stID + "\nName: "+stName+"\nMajor: "+stMajor+"\nGPA: "+stGPA);
				}
				else{
					String emID = scan.next();
					scan.nextLine();
					String emName = scan.nextLine();
					String emOffice = scan.nextLine();
					people[i] = new EmployeeImpl(emID, emName, emOffice); 
					//System.out.println("ID: "+emID + "\nName: "+emName+"\nOffice: "+emOffice);
				}
			}
			return people;
		}
		catch(IOException e){
			System.err.println("ERROR: Invalid file name");
			return null;
		}
	}
}

