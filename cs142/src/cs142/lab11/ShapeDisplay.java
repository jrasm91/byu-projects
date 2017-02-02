

package cs142.lab11;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


//import ShapePanel.MouseListener;

public class ShapeDisplay extends JPanel{
	//private Graphics page;
	//ArrayList for all the shapes in the GUI
	java.util.List<Shape> shapeList;
	//Constants for height and width and pictures
	final private static long serialVersionUID = 1L;
	final private int HEIGHT = 738;
	final private int WIDTH = 714;
	final private Color LIGHTBLUE = new Color(0,150, 250);
	final private Icon squarePicture = (Icon) new ImageIcon("lib/cs142/lab11/mySquare.gif");
	final private Icon rectanglePicture = (Icon) new ImageIcon("lib/cs142/lab11/myRectangle.gif");
	final private Icon ovalPicture = (Icon) new ImageIcon("lib/cs142/lab11/myOval.gif");
	final private Icon circlePicture = (Icon) new ImageIcon("lib/cs142/lab11/myCircle.gif");
	//options on how to display/create shapes
	boolean fill, coordinates, retran; //retran -> resize/translate. True-> translate False->resize


	//types of JFrame I will be adding many of
	JLabel label; JButton button; JPanel panel; JRadioButton radioButton;
	JPanel bluePanel, blackPanel;
	Graphics g;
	public ShapeDisplay(){

		//initialize
		fill = false; coordinates = false; retran = false;
		shapeList = new ArrayList<Shape>();
		this.setBackground(LIGHTBLUE);
		// "big blue panel" at top of screen
		bluePanel = new JPanel(); add(bluePanel);
		bluePanel.setPreferredSize(new Dimension(WIDTH, 85));
		bluePanel.setBackground(LIGHTBLUE);
		addBluePanelPushButtons();
		addBluePanelCheckBoxes();
		addBluePanelRadioButtons();	
		setBackground(LIGHTBLUE);
		//black panel at bottom
		blackPanel = new JPanel(){			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics page){
				super.paintComponent(page);
				sort();
				this.setBackground(Color.black);
				//blackPanel.paintComponents(blackPanel.getGraphics());
				page.setColor(Color.black);
				page.fillRect(0, 0, WIDTH, HEIGHT-100);
				for(int i = 0; i < shapeList.size(); i ++){
					Shape next = shapeList.get(i);
					next.draw(page, coordinates, fill);
				}
			}
		}; 
		add(blackPanel);
		blackPanel.setBackground(Color.black);
		blackPanel.setForeground(Color.black);
		blackPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT-90));
		
		MouseListener listener= new MouseListener();
		blackPanel.addMouseListener(listener );
		blackPanel.addMouseMotionListener(listener);
	}


	//adds the push buttons for the four shapes to bluePanel
	public void addBluePanelPushButtons(){
		// create panel to hold the buttons and title
		panel = new JPanel(); bluePanel.add(panel);
		panel.setBackground(LIGHTBLUE);

		// 4 Shape Buttons + Listeners
		button = new JButton(squarePicture); panel.add(button); //square button
		button.addActionListener(new ShapeListener(1));
		button = new JButton(rectanglePicture); panel.add(button); // rectangle button
		button.addActionListener(new ShapeListener(2));
		button = new JButton(ovalPicture); panel.add(button); // oval button
		button.addActionListener(new ShapeListener(3));
		button = new JButton(circlePicture); panel.add(button); // circle button
		button.addActionListener(new ShapeListener(4));

		// titled border
		TitledBorder tb = BorderFactory.createTitledBorder("Draw");
		tb.setTitleJustification(TitledBorder.LEFT); panel.setBorder(tb);
	}
	//adds the check boxes coordinates and fill to bluePanel
	public void addBluePanelCheckBoxes(){
		// create panel to hold the check boxes  and title
		panel = new JPanel();
		panel.setBackground(LIGHTBLUE);
		panel.setBorder(BorderFactory.createBevelBorder(panel.getHeight(), Color.cyan, Color.black));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JCheckBox checkbox = new JCheckBox("Fill"); 
		checkbox.addActionListener(new ShapeListener(5));
		checkbox.setBackground(LIGHTBLUE); panel.add(checkbox);
		checkbox = new JCheckBox("Coordinates");
		checkbox.addActionListener(new ShapeListener(6));
		checkbox.setBackground(LIGHTBLUE); panel.add(checkbox);
		bluePanel.add(panel);
	}
	//adds the radio buttons translate and resize to bluePanel
	public void addBluePanelRadioButtons(){
		// create panel to hold the buttons and title
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(LIGHTBLUE);
		TitledBorder tb = BorderFactory.createTitledBorder("Manipulate");
		tb.setTitleJustification(TitledBorder.RIGHT); mainPanel.setBorder(tb);

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		subPanel.setBackground(LIGHTBLUE);
		Border b1 = BorderFactory.createBevelBorder(subPanel.getHeight(), Color.black, Color.cyan);
		Border b2 = BorderFactory.createEmptyBorder();
		subPanel.setBorder(BorderFactory.createCompoundBorder(b1,b2));
		ButtonGroup group = new ButtonGroup(); 
		radioButton = new JRadioButton("Translate");
		radioButton.setSelected(true);
		radioButton.addActionListener(new ShapeListener(7));
		radioButton.setPreferredSize(radioButton.getMinimumSize());
		radioButton.setBackground(LIGHTBLUE);
		subPanel.add(radioButton); group.add(radioButton);
		subPanel.add(javax.swing.Box.createVerticalGlue());
		radioButton = new JRadioButton("Resize"); 
		radioButton.addActionListener(new ShapeListener(8));
		radioButton.setPreferredSize(radioButton.getMinimumSize());
		radioButton.setBackground(LIGHTBLUE);
		subPanel.add(radioButton); group.add(radioButton);
		mainPanel.add(subPanel);
		bluePanel.add(mainPanel);
	}
	// simple method that prints out the array list of shapes
	public void printArrayList(){
		for(int i = 0; i < shapeList.size(); i++)
			System.out.print(shapeList.get(i)); // helpful for debugging
	}
	//method that updates the GUI when something happens
	public void paintComponent(Graphics page){
//		sort();
//		//blackPanel.paintComponents(blackPanel.getGraphics());
//		//blackPanel.getGraphics().setColor(Color.black);
//		//blackPanel.getGraphics().fillRect(0, 0, WIDTH, HEIGHT-100);
//		System.out.println("size: " + shapeList.size());
//		for(int i = 0; i < shapeList.size(); i ++){
//			Shape next = shapeList.get(i);
//			next.draw(page, coordinates, fill);
//		}
	}
	

	//method that updates the order of the shapes in the arraylist by y coordinate
	private void sort(){
		ArrayList<Shape> newList = new ArrayList<Shape>();
		if(shapeList.size() > 0){
			newList.add(shapeList.get(0));
			for(int i = 1; i < shapeList.size(); i++){
				Shape nextShape = shapeList.get(i);

				for(int j = 0; j < newList.size(); j++){
					Shape thisShape = newList.get(j);
					if(nextShape.getMidPoint().getY() < thisShape.getMidPoint().getY()){
						newList.add(j, nextShape);
						break;
					}
					if(nextShape.getMidPoint().getY() == thisShape.getMidPoint().getY()){
						if(nextShape.getMidPoint().getX() <= thisShape.getMidPoint().getX()){
							newList.add(j, nextShape);
							break;
						}
					}
					if(j == newList.size()-1){
						newList.add(nextShape);
						break;
					}
				}
			}
			for(int i = 0; i < newList.size(); i++)
			{
				shapeList.set(i, newList.get(i));
				shapeList = newList;
			}
		}
		
	}
	private class ShapeListener implements java.awt.event.ActionListener {
		private int button;
		public ShapeListener(int num){
			button = num;
		}
		public void actionPerformed(java.awt.event.ActionEvent arg0){
			if(button == 1) 
				shapeList.add(new Square());
			if(button == 2) 
				shapeList.add(new Rectangle());
			if(button == 3) 
				shapeList.add(new Oval());
			if(button == 4) 
				shapeList.add(new Circle());
			if(button == 5)
				fill = !fill;
			if(button == 6 )
				coordinates = !coordinates;
			if(button == 7 && retran)
				retran = false;
			// if translate is pushed and it's on translate -> retran = false
			if(button == 8 && !retran) 
				retran = true;
			blackPanel.repaint();
		}
	}
	private class MouseListener extends java.awt.event.MouseAdapter{
		Shape selectedShape;
		Point mid;
		public void mousePressed(java.awt.event.MouseEvent event){
			//for loop finds shape mouse clicked on if any
			for(int i = shapeList.size()-1; i >= 0; i--){
				if(shapeList.get(i).containsPoint(event.getPoint()) == true){
					selectedShape = shapeList.get(i);
					mid = selectedShape.getMidPoint();
					break;
				}
			}
		} 
		public void mouseReleased(MouseEvent event){
			selectedShape = null;
			
		}
		public void mouseDragged(MouseEvent event){

			if(selectedShape != null){
				if(retran){
					selectedShape.setMidPoint(mid);
					selectedShape.setWidthHeight(2*(int)((event.getX()- mid.getX())), 2*(int)((event.getY()- mid.getY())));
					//selectedShape.draw(blackPanel.getGraphics(), coordinates, fill);
					
				}
				if(!retran){
					selectedShape.setMidPoint(event.getPoint());
				}
				blackPanel.repaint();
				
			}
		}
	}
}

