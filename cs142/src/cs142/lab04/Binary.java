package cs142.lab04;




public class Binary {
	
	public int toBinary(int index, int value)
	{
		int y = value;
		int x = y;
		int a = x / 16;
	    x = x -(16*a);
	    int b = x / 8;
	    x = x -(8*b);
	    int c = x / 4;
	    x = x -(4*c);
	    int d = x / 2;
	    x = x -(2*d);
	    int e = x / 1;
	    if(index == 1){return a;}
	    if(index == 2){return b;}
	    if(index == 3){return c;}
	    if(index == 4){return d;}
	    else {return e;}
	}
    
}