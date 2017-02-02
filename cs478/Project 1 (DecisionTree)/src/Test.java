import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		
		List l1 = new ArrayList();

		l1.add(1);
		l1.add(2);
		l1.add(3);

		List l2= new ArrayList();
		l2.add(4);
		l2.add(2);
		l2.add(3);

		List l3 = new ArrayList(l2);
		l3.retainAll(l1);
		
		System.out.println(Arrays.toString(l3.toArray()));
		
		
	}

}
