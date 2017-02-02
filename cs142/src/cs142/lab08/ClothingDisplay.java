package cs142.lab08;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;




public class ClothingDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ArrayList<ImageIcon> rackShirts;


	private int purchaseAmount;
	private JLabel label, shirtOnBackLabel, shirtOnFrontLabel, shirtsOnRackLabel, purchaseAmountLabel, numberOfShirtsInCartLabel,
	shirtInCartLabel;
	private JButton button, checkOutButton, shirtInHandButton, returnShirtBackToRackButton, moveToShirtInHand;
	private JTextField text;
	private Stack cart;


	private final ImageIcon csShirt = new ImageIcon("CS.jpg");
	private final ImageIcon greyShirt = new ImageIcon("Grey.jpg");
	private final ImageIcon poloShirt = new ImageIcon("polo.jpg");
	private final ImageIcon whiteShirt = new ImageIcon("White.jpg");
	private final int MAX_WIDTH = 1000;
	private final int MAX_HEIGHT = 750;
	private final int SHIRT_WIDTH = 150;
	private final int SHIRT_HEIGHT = 130;

	ImageIcon frontPicture, backPicture, shirtInHand; 

	public ClothingDisplay()
	{
		rackShirts = new ArrayList<ImageIcon>();
		//adds 10 random shirts to the array list
		for(int i = 0; i<= 9; i++)
		{
			Random r = new Random();
			int random = r.nextInt(4);
			ImageIcon nextShirt;
			if(random == 0){nextShirt = csShirt;}
			else if(random == 1){nextShirt = greyShirt;}
			else if(random == 2){nextShirt = poloShirt;}
			else{nextShirt = whiteShirt;}
			rackShirts.add(nextShirt);

		}
		//initializes the shirt icons
		frontPicture = rackShirts.get(0);
		backPicture = rackShirts.get(rackShirts.size()-1);
		shirtInHand = null;

		//initialize shirt counters
		cart = new Stack();
		purchaseAmount = 0;

		//set panel preferences
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(MAX_WIDTH,MAX_HEIGHT));

		//add a text field on top of GUI
		label = new JLabel("(Shirts That Can Be Added To the Rack)", SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(MAX_WIDTH, 25));
		add(label);

		//adds the first 4 buttons to the panel
		//BUTTON ONE
		button = new JButton(csShirt);
		button.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		button.addActionListener(new ButtonListener(1));
		add(button);

		//BUTTON TWO
		button = new JButton(greyShirt);
		button.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		button.addActionListener(new ButtonListener(2));
		add(button);
		//BUTTON THREE
		button = new JButton(poloShirt);
		button.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		button.addActionListener(new ButtonListener(3));
		add(button);

		//BUTON FOUR
		button = new JButton(whiteShirt);
		button.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		button.addActionListener(new ButtonListener(4));
		add(button);

		newLine();

		//add a label that displays the number of shirts on the rack
		label = new JLabel("Shirt in Hand", SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(MAX_WIDTH, 40));
		add(label);

		//text for "Front Shirt"
		label = new JLabel("Front Shirt -> ",SwingConstants.LEFT);
		add(label);
		//text for front shirt
		shirtOnFrontLabel = new JLabel(frontPicture);
		shirtOnFrontLabel.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		add(shirtOnFrontLabel);
		//button for shirt in hand
		shirtInHandButton = new JButton(shirtInHand);
		shirtInHandButton.addActionListener(new ButtonListener(5));
		shirtInHandButton.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		add(shirtInHandButton);
		//picture for backPicture
		shirtOnBackLabel = new JLabel(backPicture, SwingConstants.CENTER);
		shirtOnBackLabel.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		add(shirtOnBackLabel);
		//text for "Back Shirt"
		label = new JLabel("<- Back Shirt", SwingConstants.RIGHT);
		add(label);

		newLine();

		//text for "Number of Shirts on Rack"
		shirtsOnRackLabel = new JLabel("Number of Shirts on Rack: " + rackShirts.size() + "     ");
		add(shirtsOnRackLabel);
		//Button for "Return Shirt Back to Rack"
		returnShirtBackToRackButton = new JButton("Return Shirt Back to Rack");
		returnShirtBackToRackButton.addActionListener(new ButtonListener(6));
		add(returnShirtBackToRackButton);

		//text for "View shirt on Rack#"
		label = new JLabel("           View Shirt on Rack#", SwingConstants.CENTER);
		add(label);
		//JTextBox thingy-ma-bob
		text = new JTextField(2);
		text.addActionListener(new ButtonListener(9));
		add(text);

		newLine();

		//Button for "Replace Shirt in Hand With"
		moveToShirtInHand = new JButton("Move to Shirt in Hand");
		moveToShirtInHand.addActionListener(new ButtonListener(7));
		add(moveToShirtInHand);

		//label for the top shirt in the cart
		shirtInCartLabel = new JLabel(cart.getTopShirt());
		shirtInCartLabel.setPreferredSize(new Dimension(SHIRT_WIDTH, SHIRT_HEIGHT));
		add(shirtInCartLabel);

		//text for "Shirt in Cart"
		label = new JLabel("<- Shirt in Cart");
		add(label);

		newLine();

		//Text for "Number of Shirts in Cart" 
		numberOfShirtsInCartLabel = new JLabel("Number of Shirts in Cart: " + cart.getSize());
		add(numberOfShirtsInCartLabel);

		//Button for "Check Out"

		checkOutButton = new JButton("CHECK OUT!");
		checkOutButton.addActionListener(new ButtonListener(8));
		add(checkOutButton );
		//Text for "Number of Shirts Purchased"
		purchaseAmountLabel = new JLabel();
		purchaseAmountLabel.setText("Number of Shirts Purchased: " + purchaseAmount);
		add(purchaseAmountLabel);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(MAX_WIDTH,MAX_HEIGHT));
	}

	//Adds a new line
	public void newLine()
	{
		JPanel newLine = new JPanel();
		newLine.setPreferredSize(new Dimension(MAX_WIDTH, 20));
		newLine.setBackground(Color.WHITE);
		add(newLine);
	}

	public boolean addShirt(ImageIcon icon)	{

		if(rackShirts.size() < 20){
			rackShirts.add(icon);
			return true;
		}
		return false;
	}
	//Button Listeners for all the BUTTONS
	private class ButtonListener implements ActionListener
	{
		private int buttonNumber;
		public ButtonListener(int num){buttonNumber = num;}

		public void actionPerformed(ActionEvent event)
		{
			//BUTTON ONE
			if(buttonNumber == 1)
				addShirt(csShirt);
			//BUTTON TWO
			if(buttonNumber == 2)
				addShirt(greyShirt);
			//BUTTON THREE
			if(buttonNumber == 3)
				addShirt(poloShirt);
			//BUTTON FOUR
			if(buttonNumber == 4)
				addShirt(whiteShirt);
			//SHIRT IN HAND BUTTON
			if(buttonNumber == 5)
			{
				if(shirtInHandButton.getIcon() != null)
					if(cart.add((ImageIcon)shirtInHandButton.getIcon()))
						shirtInHandButton.setIcon(null);
				
			}
			//RETURN SHIRT BACK TO RACK BUTTON
			if(buttonNumber == 6)
			{
				if(shirtInHandButton.getIcon() != null)
				{
					if(addShirt((ImageIcon)shirtInHandButton.getIcon()))
						shirtInHandButton.setIcon(null);
				}
			}
			//MOVE SHIRT TO HAND BUTTON
			if(buttonNumber == 7)
			{
				if((shirtInHandButton.getIcon() == null) && (cart.getSize() != 0))
				{
					ImageIcon newItem = cart.remove();
					shirtInHandButton.setIcon(newItem);
				}
			}
			//CHECK OUT BUTTON
			if(buttonNumber == 8){purchaseAmount += cart.checkOut();}
			//TextBox BUTTON THINGY?
			if(buttonNumber == 9)
			{
				if((shirtInHandButton.getIcon() == null) || (rackShirts.size() != 20))
				{
					int j = -1;	
					for(int i = 0; i< rackShirts.size(); i++)
					{
						String index = "" + (i+1);
						if(index.equals(event.getActionCommand()))
						{j = i;}			
					}
					
					if(shirtInHandButton.getIcon() != null)
					{
						rackShirts.add((ImageIcon)shirtInHandButton.getIcon());
					}
					
					if(j != -1)
					{
						shirtInHandButton.setIcon(rackShirts.get(j));
						rackShirts.remove(j);
					}
					if(j == -1)
					{
						shirtInHandButton.setIcon(null);
					}
					
				}
			}
			shirtInCartLabel.setIcon(cart.getTopShirt());
			if(rackShirts.size() == 0)
			{
				shirtOnBackLabel.setIcon(null);
				shirtOnFrontLabel.setIcon(null);
			}
			else
			{
				shirtOnBackLabel.setIcon(rackShirts.get(rackShirts.size()-1));
				shirtOnFrontLabel.setIcon(rackShirts.get(0));
			}
			
			
			purchaseAmountLabel.setText("Number of Shirts Purchased: " + purchaseAmount);
			shirtsOnRackLabel.setText("Number of Shirts on Rack: " + rackShirts.size() + "     ");
			numberOfShirtsInCartLabel.setText("Number of Shirts in Cart: " + cart.getSize());
			
		}
	}
}
