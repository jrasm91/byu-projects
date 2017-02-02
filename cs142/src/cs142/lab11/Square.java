
package cs142.lab11;


public class Square extends Rectangle{
	
	public Square(){
		super();
		setWidthHeight(width, height);
	}
	
	public void setWidthHeight(int width, int height){
		height = Math.abs(height);
		width = Math.abs(width);
		if(height > width){
			setHeight(height);
			setWidth(height);
		}
		if(height < width){
			setHeight(width);
			setWidth(width);
		}
	}
}
