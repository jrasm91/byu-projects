package gui.newWindows;

import gui.GUISender;
import gui.Main.GUI;
import hypeerweb.node.Node;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.SendVisitor;
import hypeerweb.visitor.Visitor;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class SendWindowPanel extends JPanel {
	protected GUI main;
	protected JLabel startingNodeLabel;
	protected JLabel endingNodeLabel;
	protected JLabel messageBoxLabel;
	protected JTextField startingNode;
	protected JTextField endingNode;
	protected JTextField messageBox;
	protected JButton sendButton;

	public SendWindowPanel(GUI main) {
		// super(new GridBagLayout());
		super(new GridLayout(3, 1));
		this.main = main;

		startingNodeLabel = new JLabel("Starting Node");
		endingNodeLabel = new JLabel("Ending Node");
		messageBoxLabel = new JLabel("Message");

		startingNode = new JTextField(3);
		endingNode = new JTextField(3);
		messageBox = new JTextField(20);

		// Build the send button
		sendButton = new JButton("Send Message");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButtonPressed();
			}

			public void windowClosing(WindowEvent we) {
				setSendWindowToNull();
			}
		});

		JPanel startingEndingNodePanel = new JPanel();
		startingEndingNodePanel.add(startingNodeLabel);
		startingEndingNodePanel.add(startingNode);
		startingEndingNodePanel.add(endingNodeLabel);
		startingEndingNodePanel.add(endingNode);
		this.add(startingEndingNodePanel);

		JPanel messageNodePanel = new JPanel();
		messageNodePanel.add(messageBoxLabel);
		messageNodePanel.add(messageBox);
		this.add(messageNodePanel);

		this.add(sendButton);

	}

	private void setSendWindowToNull() {
		main.getHyPeerWebDebugger().getStandardCommands().setSendWindowToNull();
	}

	private void sendButtonPressed() {
		String startNodeString = startingNode.getText();
		String targetNodeString = endingNode.getText();
		try {
			int startNodeInt = Integer.parseInt(startNodeString);
			int targetNodeInt = Integer.parseInt(targetNodeString);
			Node startNode = main.getHyPeerWeb().getNode(startNodeInt);
			Node targetNode = main.getHyPeerWeb().getNode(targetNodeInt);

			if (startNode == null) {
				main.getHyPeerWebDebugger()
						.getStatus()
						.setContent(
								"Indicated starting node is not in HyPeerWeb");
			} else if (targetNode == null) {
				main.getHyPeerWebDebugger()
						.getStatus()
						.setContent("Indicated ending node is not in HyPeerWeb");

			} else {
				String message = messageBox.getText();
				Visitor sendVisitor = new GUISender();
				Parameters parameters = GUISender.createInitialParameters(
						targetNodeInt, message);
				startNode.accept(sendVisitor, parameters);
				main.getHyPeerWebDebugger().getPrinter()
						.print(parameters.get(GUISender.TRACE_KEY));
			}

		} catch (NumberFormatException e) {
			String id = e.getMessage().substring(e.getMessage().indexOf(":") + 1);
			main.getHyPeerWebDebugger()
					.getStatus()
					.setContent(id + " is not a valid input");
		}
	}
}
