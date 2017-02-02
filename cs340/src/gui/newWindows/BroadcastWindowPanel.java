package gui.newWindows;

import gui.GUIBroadcaster;
import gui.GUISender;
import gui.Main.GUI;
import hypeerweb.node.Node;
import hypeerweb.visitor.BroadcastHelper;
import hypeerweb.visitor.BroadcastVisitor;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.SendVisitor;
import hypeerweb.visitor.Visitor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BroadcastWindowPanel extends JPanel {
	protected GUI main;
	protected JLabel startingNodeLabel;
	protected JLabel messageBoxLabel;
	protected JTextField startingNode;
	protected JTextField messageBox;
	protected JButton broadcastButton;

	public BroadcastWindowPanel(GUI main) {
		// super(new GridBagLayout());
		super(new GridLayout(3, 1));
		this.main = main;

		startingNodeLabel = new JLabel("Starting Node");
		messageBoxLabel = new JLabel("Message");

		startingNode = new JTextField(3);
		messageBox = new JTextField(20);

		// Build the send button
		broadcastButton = new JButton("Broadcast Message");
		broadcastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				broadcastButtonPressed();
			}

			public void windowClosing(WindowEvent we) {
				setBroadcastWindowToNull();
			}
		});

		JPanel startingEndingNodePanel = new JPanel();
		startingEndingNodePanel.add(startingNodeLabel);
		startingEndingNodePanel.add(startingNode);
		this.add(startingEndingNodePanel);

		JPanel messageNodePanel = new JPanel();
		messageNodePanel.add(messageBoxLabel);
		messageNodePanel.add(messageBox);
		this.add(messageNodePanel);

		this.add(broadcastButton);

	}

	private void setBroadcastWindowToNull() {
		main.getHyPeerWebDebugger().getStandardCommands()
				.setBroadcastWindowToNull();
	}

	private void broadcastButtonPressed() {
		String startNodeString = startingNode.getText();
		try {
			int startNodeInt = Integer.parseInt(startNodeString);
			Node startNode = main.getHyPeerWeb().getNode(startNodeInt);
			if (startNode == null) {
				main.getHyPeerWebDebugger()
						.getStatus()
						.setContent(
								"Indicated starting node is not in HyPeerWeb");
			} else {
				String message = messageBox.getText();
				BroadcastVisitor broadcastVisitor = new GUIBroadcaster();
				Parameters parameters = GUIBroadcaster
						.createInitialParameters(message);
				BroadcastHelper helper = new BroadcastHelper(broadcastVisitor);
				startNode.accept(helper, parameters);
				main.getHyPeerWebDebugger().getPrinter()
						.print(parameters.get(GUIBroadcaster.TRACE_KEY) + "\n");
			}
		} catch (NumberFormatException e) {
			String id = e.getMessage().substring(e.getMessage().indexOf(":") + 1);
			main.getHyPeerWebDebugger()
					.getStatus()
					.setContent(id + " is not a valid input");
		}
	}
}
