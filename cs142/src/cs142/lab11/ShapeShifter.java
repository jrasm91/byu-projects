
package cs142.lab11;
import java.awt.Color;
import java.awt.*;

import javax.swing.*;




public class ShapeShifter extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	
	public static void main(String[] args) 
	{
		JPanel contentPanel = new ShapeDisplay();
		contentPanel.setPreferredSize(new Dimension(714, 738));
		JFrame frame = new JFrame ("Shape Shifter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(contentPanel);
		frame.setBackground(new Color(0,150, 250));
		frame.pack();
		frame.setLocation(0,0);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
