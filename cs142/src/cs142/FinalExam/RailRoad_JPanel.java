package cs142.FinalExam;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class RailRoad_JPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack stack;
	private Queue queue;
	private Deque deque;
	private String currentID;

	private JLabel currentLabel, stackLabel, queueLabel, dequeLabel, errorLabel;

	private final int WIDTH = 600;
	public RailRoad_JPanel(){
		currentID = "No Current Car";
		stack = new Stack(); 
		queue = new Queue();
		deque = new Deque();

		add(new JLabel("ID Number for Next Car:                  "));
		add(new JLabel("ID Number of Current Car:                "));
		add(new JLabel("Send Current Car Away:")); newLine(WIDTH, 2);
		JTextField textbox = new JTextField(4); add(textbox); newLine(WIDTH/5, 2);
		textbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(currentID.equals("No Current Car")){
					if(!contains(e.getActionCommand())){
						currentID = e.getActionCommand();
						update();
					}
					else
						errorLabel.setText("Please Choose A Valid Name");
				}
			}
		});
		currentLabel = new JLabel("No Current Car"); add(currentLabel); newLine(WIDTH/5, 2);
		JButton sendButton = new JButton("Send ->"); add(sendButton); newLine(WIDTH, 1);
		errorLabel = new JLabel(""); add(errorLabel); newLine(WIDTH, 1);
		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!currentID.equals("No Current Car")){
					currentID = "No Current Car";
					update();
				}
			}
		});

		newLine(WIDTH, 20);
		JButton button = new JButton("Push"); // push button for stack
		add(button); button.setPreferredSize(new Dimension(100, 22));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!currentID.equals("No Current Car") && stack.getList().size() < 5){
					stack.push(currentID); currentID = "No Current Car";
					update();}
			}
		}); newLine(50, 25);
		button = new JButton("Push"); add(button); // push button for queue
		button.setPreferredSize(new Dimension(100, 25));
		button.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			if(!currentID.equals("No Current Car") && queue.getList().size() < 5){
				queue.push(currentID); currentID = "No Current Car"; 
				update();}
		}
		}); newLine(50, 25);
		button = new JButton("Push Left"); add(button); // push left button for deque
		button.setPreferredSize(new Dimension(100, 25));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!currentID.equals("No Current Car") && deque.getList().size() < 5){
					deque.pushLeft(currentID); currentID = "No Current Car";
					update();}
			}
		}); //newLine(125, 25);
		button = new JButton("Push Right"); add(button); // push right button for deque
		button.setPreferredSize(new Dimension(100, 25));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!currentID.equals("No Current Car") && deque.getList().size() < 5){
					deque.pushRight(currentID); currentID = "No Current Car"; 
					update();}
			}
		});

		add(new JLabel("Stack (Last in First Out)          ", SwingConstants.LEFT));
		add(new JLabel("   Queue (First in First Out)                  ", SwingConstants.CENTER));
		add(new JLabel("Deque (push/pop either end)              ", SwingConstants.RIGHT));

		button = new JButton("Pop"); add(button); // pop button for stack
		button.setPreferredSize(new Dimension(100, 25));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(currentID.equals("No Current Car") && stack.getList().size() > 0){
					currentID = stack.pop();
					update();}
			}
		}); newLine(50, 25);
		button = new JButton("Pop"); add(button); // pop button for queue
		button.setPreferredSize(new Dimension(100, 25));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(currentID.equals("No Current Car") && queue.getList().size() > 0){
					currentID = queue.pop();
					update();}
			}
		}); newLine(50, 25);
		button = new JButton("Pop Left"); add(button); // pop left button for deque
		button.setPreferredSize(new Dimension(100, 25));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(currentID.equals("No Current Car") && deque.getList().size() > 0){
					currentID = deque.popLeft();
					update();}
			}
		});//newLine(125, 25);
		button = new JButton("Pop Right"); add(button); // pop right button for deque
		button.setPreferredSize(new Dimension(100, 25));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(currentID.equals("No Current Car") && deque.getList().size() > 0){
					currentID = deque.popRight();
					update();}
			}
		});
		newLine(WIDTH, 20);	
		stackLabel = new JLabel(stack.printList()); add(stackLabel); newLine(WIDTH, 2);
		queueLabel = new JLabel(queue.printList()); add(queueLabel); newLine(WIDTH, 2);
		dequeLabel = new JLabel(deque.printList()); add(dequeLabel); newLine(WIDTH, 2);

	}


	//functions to format GUI with new lines etc

	private void newLine(int width, int height){
		JPanel panel = new JPanel(); add(panel);
		panel.setPreferredSize(new Dimension(width, height)); 
	}
	private void update(){
		if(errorLabel.getText() != null)
			errorLabel.setText(null);
		stackLabel.setText(stack.printList());
		queueLabel.setText(queue.printList());
		dequeLabel.setText(deque.printList());
		currentLabel.setText(currentID);
	}
	private boolean contains(String id){
		return stack.contains(id) || queue.contains(id) || deque.contains(id);
	}

}
