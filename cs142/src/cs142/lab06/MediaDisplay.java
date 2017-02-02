//****************************************************************************************************
//
package cs142.lab06;
//	MediaDisplay.java	Authors: Andrew D and Michael		Date Created: September 2007
//				Edited by: Robbie Buck			Last Edited: October 25, 2007
//
//			Purpose: Display for the Cougar Media Store
//
//****************************************************************************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class MediaDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;
	//variable declarations
	private JRadioButton cdLib, bookLib;
	private JLabel promptLabel, displayLabel, displaySize;
	@SuppressWarnings("rawtypes")
	private JComboBox promptCombo;
	private JButton promptRemove, addButton, promptRemoveAll;
	private JTextField addField;
	private JTextArea displayArea;
	private Media studArray;
	private ActionListener libListener, opsListener, moreListener;
	private JPanel promptPanel, displayPanel, radioPanel, scrollPanel;
	final int PANEL_WIDTH = 500, PANEL_HEIGHT = 400;
	final int CDS=0, BOOKS=1;						//indentifies type of library currently being used
	private int currentLibrary;
	private int[] size;								//number of cds, books, and movies
	private DecimalFormat fmt;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MediaDisplay()
	{
		//support variable initializations
		libListener = new LibraryListener();
		opsListener = new OperationsListener();
		moreListener = new AddListener();
		studArray = new Media();
		try
		{
			studArray.addCDFile("cds.txt");
		}
		catch(Exception e)
		{
			System.out.println("\nThis is what went wrong trying to load the CD file");
			e.printStackTrace();
			System.out.println("\n\n");
			System.out.println("The problem above might cause other errors.");
			System.out.println("If so, they will be listed below\n");
			JOptionPane.showMessageDialog(null, "Error while trying to load the CD file." +
				"Please see command prompt.");
		}
		try
		{
			studArray.addBookFile("books.txt");
		}
		catch (Exception e)
		{
			System.out.println("\nThis is what went wrong trying to load the Book file");
			e.printStackTrace();
			System.out.println("\n\n");
			System.out.println("The problem above might cause other errors.");
			System.out.println("If so, they will be listed below\n");
			JOptionPane.showMessageDialog(null, "Error while trying to load Book file." +
				"Please see command prompt");
		}

		size = studArray.getSize();
		currentLibrary = CDS;
		fmt = new DecimalFormat("0.00");

		//GUI variable initializations
		radioPanel = new JPanel(); promptPanel = new JPanel(); scrollPanel = new JPanel();
		displayPanel = new JPanel();
		cdLib = new JRadioButton("CD's", true); bookLib = new JRadioButton("Books");
		ButtonGroup group = new ButtonGroup();
		addField = new JTextField (15);
		promptLabel = new JLabel("Select a Title: "); displayLabel = new JLabel ("Info on your selection: ");
		displaySize = new JLabel("CDs: " + size[CDS] + "   Books: " + size[BOOKS] + "   Store Net Worth: $" + fmt.format(studArray.getNetWorth()));
		promptCombo = new JComboBox(studArray.getCDTitles());
		promptRemove = new JButton("Remove"); addButton = new JButton("Add Title to Library");
		promptRemoveAll = new JButton("Remove All");
		displayArea = new JTextArea(8, 30);

		//Sets Info from beginning
		try
		{
			displayArea.setText(studArray.findCD((String)promptCombo.getSelectedItem()).toString());
		}
		catch(NullPointerException e)
		{
			System.out.println("The findCD method was called and a null object was returned");
			System.out.println("This is a problem");
			System.exit(0);
		}
		//add listeners
		cdLib.addActionListener(libListener); bookLib.addActionListener(libListener);
		promptCombo.addActionListener(opsListener); promptRemove.addActionListener(opsListener);
		promptRemoveAll.addActionListener(opsListener);
		addField.addActionListener(moreListener); addButton.addActionListener(moreListener);

		//Set up for radioPanel (toggle between books, cds, and movies)
			//setup button group
			group.add(cdLib);
			group.add(bookLib);

			//add buttons to panel
			radioPanel.add(cdLib);
			radioPanel.add(bookLib);

		//Set up for promptPanel (with TextField)
			//format panel
			promptPanel.setBackground(Color.CYAN);

			//add components to panel
			promptPanel.add(promptLabel);
			promptPanel.add(promptCombo);
			promptPanel.add(promptRemove);
			promptPanel.add(promptRemoveAll);

		//Set up for scrollPanel
			//add components to panel
			scrollPanel.add(addField);
			scrollPanel.add(addButton);

		//Set up for displayPanel
			//format panel
			displayPanel.setBackground(Color.CYAN);
			displayArea.setEditable(false);

			//add components to panel
			displayPanel.add(displayLabel);
			displayPanel.add(displayArea);
			displayPanel.add(displaySize);

		//Set up main panel
			//format panel
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

			//add components to panel
			add(radioPanel);
			add(promptPanel);
			add(scrollPanel);
			add(displayPanel);
	}

	//*******************************************************************************
	//			Listener for GUI components
	//*******************************************************************************
	private class OperationsListener implements ActionListener
	{		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void actionPerformed(ActionEvent event)
		{	
			//User presses "Display Info"
			if (event.getSource() == promptCombo)	//different media is selected
			{
				if (currentLibrary == CDS)
				{
					try
					{
						CD temp = studArray.findCD((String)promptCombo.getSelectedItem());
						displayArea.setText(temp.toString());
					}
					catch(NullPointerException e)
					{
						System.out.println("The findCD method was called and a null object was returned");
						System.out.println("This is a problem");
						System.exit(0);
					}
				}
				else if (currentLibrary == BOOKS)
				{
					try
					{
						Book temp = studArray.findBook((String)promptCombo.getSelectedItem());
						displayArea.setText(temp.toString());
					}
					catch(NullPointerException e)
					{
						System.out.println("The findBook method was called and a null object was returned");
						System.out.println("This is a problem");
						System.exit(0);
					}
				}
			}

			else if (event.getSource() == promptRemove && promptCombo.getItemCount() != 0)	//remove selection if there is one left in the library
			{

				String currentSelection = (String)promptCombo.getSelectedItem();	//current selected media

				if (currentSelection == null)	//bad selection
					displayArea.setText("");
				else if (currentLibrary == CDS)
				{					//get CD selection
					studArray.removeCD(currentSelection);						//remove CD from library
					promptCombo.setModel(new DefaultComboBoxModel(studArray.getCDTitles()));	//update CD library
					if(studArray.getSize()[CDS] > 0)
						displayArea.setText(studArray.getCD(0).toString());
					else
						displayArea.setText("");
				}
				else if (currentLibrary == BOOKS)
				{
					studArray.removeBook((String)promptCombo.getSelectedItem());
					promptCombo.setModel(new DefaultComboBoxModel(studArray.getBookTitles()));
					if(studArray.getSize()[BOOKS] > 0)
						displayArea.setText(studArray.getBook(0).toString());
					else
						displayArea.setText("");
				}
				//update sizes of media
				size = studArray.getSize();
				displaySize.setText("CD's: " + size[CDS] + "   Books: " + size[BOOKS] + "   Store Net Worth: $" + fmt.format(studArray.getNetWorth()));
			}
			else if (event.getSource() == promptRemoveAll && promptCombo.getItemCount() != 0)
			{
				String currentSelection = (String)promptCombo.getSelectedItem();	//current selected media

				if(currentSelection == null)
					displayArea.setText("");
				else if(currentLibrary == CDS)
				{
					while(studArray.getSize()[CDS] > 0)
					{
						String current = (String) promptCombo.getSelectedItem();
						if(current == null)
						{
							displayArea.setText("Error.  Your title array has indicies that are null");
							return;
						}
						studArray.removeCD(current);
						promptCombo.setModel(new DefaultComboBoxModel(studArray.getCDTitles()));
					}
					displayArea.setText("");
				}
				else if(currentLibrary == BOOKS)
				{
					while(studArray.getSize()[BOOKS] > 0)
					{
						String current = (String) promptCombo.getSelectedItem();
						if(current == null)
						{
							displayArea.setText("Error.  Your title array has indicies that are null");
							return;
						}
						studArray.removeBook(current);
						promptCombo.setModel(new DefaultComboBoxModel(studArray.getBookTitles()));
					}
					displayArea.setText("");
					size = studArray.getSize();
					displaySize.setText("CD's: " + size[CDS] + "   Books: " + size[BOOKS] + "   Store Net Worth: $" + fmt.format(studArray.getNetWorth()));
					
				}
			}
		}
	}

	//*******************************************************************************
	//		Listener for adding media to current library
	//*******************************************************************************
	private class AddListener implements ActionListener
	{
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void actionPerformed(ActionEvent event)
		{
			String title, year, price, person;
			title = addField.getText();

			if (title == null || title.equals(""))	//no title entered
			{
				JOptionPane.showMessageDialog(null, "Please enter a title.");
			}
			else
			{
				year = JOptionPane.showInputDialog("Enter a year for " + title + ":", "2007");
				price = JOptionPane.showInputDialog("Enter a price for " + title + ":", "9.99");
				if(currentLibrary == CDS)
				{
					person = JOptionPane.showInputDialog("Enter an artist for " + title + ":", "Mystery Man");
					studArray.addCD(title, Integer.parseInt(year), Double.parseDouble(price), person);
					promptCombo.setModel(new DefaultComboBoxModel(studArray.getCDTitles()));	//update CD library
				}
				else if(currentLibrary == BOOKS)
				{
					person = JOptionPane.showInputDialog("Enter an author for " + title + ":", "Mystery Man");
					studArray.addBook(title, Integer.parseInt(year), Double.parseDouble(price), person);
					promptCombo.setModel(new DefaultComboBoxModel(studArray.getBookTitles()));	//update book library
				}
			}

			addField.setText("");	//clear add field for next title

			//update sizes of media
			size = studArray.getSize();
			displaySize.setText("CD's: " + size[CDS] + "   Books: " + size[BOOKS] + "   Store Net Worth: $" + fmt.format(studArray.getNetWorth()));
		}
	}

	//*******************************************************************************
	//		Listener for changing current library
	//*******************************************************************************
	private class LibraryListener implements ActionListener
	{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void actionPerformed(ActionEvent event)
		{
			//If library is changed
			if(event.getSource() == cdLib)
			{
				currentLibrary = CDS;
				promptCombo.setModel(new DefaultComboBoxModel(studArray.getCDTitles()));
				String currentSelection = (String)promptCombo.getSelectedItem();
	
				if (currentSelection == null)
					displayArea.setText("");
				else
				{
					CD temp = studArray.findCD(currentSelection);
					displayArea.setText(temp.toString());
				}
			}
			else if (event.getSource() == bookLib)
			{
				currentLibrary = BOOKS;
				promptCombo.setModel(new DefaultComboBoxModel(studArray.getBookTitles()));
				String currentSelection = (String)promptCombo.getSelectedItem();

				if (currentSelection == null)
					displayArea.setText("");
				else
				{
					Book temp = studArray.findBook(currentSelection);
					displayArea.setText(temp.toString());
				}
			}			

		}
	}
}
