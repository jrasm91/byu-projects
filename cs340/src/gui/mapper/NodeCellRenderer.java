package gui.mapper;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;

import gui.Main.GUI;
import hypeerweb.node.NodeProxy;

/**
 * Cell Renderer for rendering nodes in a JList
 * 
 * @author Matthew Smith
 */
public class NodeCellRenderer implements ListCellRenderer {

	public static final int cellWidth = 30;
	public static final int cellHieght = 10;

	/* Root to the GUI */
	private GUI main;

	/* Remembers the last selected component */
	private Component selected;

	/**
	 * 
	 * @param main
	 *            root to the GUI
	 */
	public NodeCellRenderer(GUI main) {
		this.main = main;

	}

	/**
	 * Produces the componets used to make up a JList
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		if (index == 0)
			selected = null;

		JPanel panel = new JPanel();
		panel.setSize(cellWidth, cellHieght);

		if (cellHasFocus) {
			selected = panel;
		}

		// panel.setToolTipText(extractToolTipText(list,value,index,isSelected,cellHasFocus));
		panel.setBackground(extractBackgroundColor(list, value, index,
				isSelected, cellHasFocus));
		panel.setForeground(extractForegroundColor(list, value, index,
				isSelected, cellHasFocus));
		panel.add(extractContent(list, value, index, isSelected, cellHasFocus,
				main));

		if (cellHasFocus) {
			panel.setBorder(BorderFactory.createEtchedBorder());
		} else
			panel.setBorder(BorderFactory.createEmptyBorder());

		return panel;
	}

	/**
	 * Extracts what the Background Color should be for the Object in the list
	 * 
	 * @param list
	 * @param value
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return
	 */
	private Color extractBackgroundColor(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {

		if (isSelected
				&& index < main.getHyPeerWebDebugger().getMapper()
						.getNodeListing().listSize())
			return Color.RED;
		else
			return list.getBackground();
	}

	/**
	 * Extracts what the Foreground Color should be for the Object in the list
	 * 
	 * @param list
	 * @param value
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return
	 */
	private Color extractForegroundColor(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {

		if (isSelected
				&& index < main.getHyPeerWebDebugger().getMapper()
						.getNodeListing().listSize())
			return Color.RED;
		else
			return list.getForeground();
	}

	/**
	 * Extracts the contents of should be displayed for the Object in the list
	 * 
	 * @param list
	 * @param value
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return
	 */
	private JComponent extractContent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus, GUI main) {

		JLabel label = new JLabel(value.toString());
		if (main.getHyPeerWeb().getNode(index) instanceof NodeProxy) {
			label.setForeground(Color.BLUE);
		} else if(main.getHyPeerWeb().getNode(index) != null){
			label.setForeground(Color.BLACK);
		} else {
			label.setForeground(Color.MAGENTA);
		}
		return label;
	}

}
