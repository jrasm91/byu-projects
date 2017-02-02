package testing;

import static org.junit.Assert.*;
import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.node.Connections;
import hypeerweb.node.Node;
import hypeerweb.node.NodeState;
import hypeerweb.node.WebId;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LeviWhiteBox {

	@Before
	public void setUp() throws Exception {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		HyPeerWebDatabase.getSingleton().clear();
		HyPeerWeb.getSingleton().clear();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void loopZero() {
		//Testing HyPeerWeb.saveToDatabase()
		//with zero entries, so the loop should skip entirely
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		h.saveToDatabase();//this is what is tested
		HyPeerWebDatabase d = HyPeerWebDatabase.getSingleton();
		assertEquals(0, d.getAll().size());
	}
	@Test
	public void loopOne() {
		//Testing adding just one 
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		h.addNode(new Node(0));
		h.saveToDatabase();//this is what is tested
		HyPeerWebDatabase d = HyPeerWebDatabase.getSingleton();
		assertEquals(1, d.getAll().size());
	}
	@Test
	public void loopTwo() {
		//Testing one, but also another (but not more)
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		Node zeroth = new Node(0);
		h.addNode(zeroth);
		zeroth.addToHyPeerWeb(new Node(1));
		h.saveToDatabase();//this is what is tested
		HyPeerWebDatabase d = HyPeerWebDatabase.getSingleton();
		assertEquals(2, d.getAll().size());
	}
	@Test
	public void loopMany() {
		//Do more than 2
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		Node zeroth = new Node(0);
		h.addNode(zeroth);
		zeroth.addToHyPeerWeb(new Node(1));
		zeroth.addToHyPeerWeb(new Node(2));
		h.saveToDatabase();//this is what is tested
		HyPeerWebDatabase d = HyPeerWebDatabase.getSingleton();
		assertEquals(3, d.getAll().size());
	}
	
	@Test
	public void relationalEqual() {
		//We throw an exception on when you try to add yourself
		//this is the equal condition since the WebIds are checked
		try {
			Node n = new Node(0);
			n.addNeighbor(n);//this is what is tested
			fail();
		} catch(ArrayIndexOutOfBoundsException ex) {
			//Good.
		}
	}
	
	@Test
	public void relationalGreater() {
		//Forcefully add a neighbor on the connections
		//with a neighbor that is greater
		Node n = new Node(0);
		Connections c = n.getConnections();
		Node one = new Node(1);
		c.addNeighbor(one);//this is what is tested
		assertEquals(1, c.getBiggerNeighbors().size());
	}
	
	@Test
	public void relationalLesser() {
		//See that the children were set in the connections for
		//smaller neighbors
		Node n = new Node(0);
		for (int i = 1; i < 8; i++) {
			n.addToHyPeerWeb(new Node(i));
		}
		HyPeerWeb h = HyPeerWeb.getSingleton();
		Connections c = h.getNode(4).getConnections();
		assertEquals(1, c.getSmallerNeighborsWithChild().size());
		assertEquals(Node.NULL_NODE, c.getSmallestNeighborWithoutChild());
	}
	
	@Test
	public void DataFlowOne() {
		//Check that the transition does change for the new cap
		Node n = new Node(0);
		Node one = new Node(1);
		one.setState(NodeState.NULL_STATE);
		assertEquals(NodeState.NULL_STATE, one.getState());
		one.setFold(n);//this is what is tested
		assertEquals(NodeState.HYPEERCUBECAP, one.getState());
	}
	
	@Test
	public void DataFlowTwo() {
		//Test that the state does not change
		Node n = new Node(0);
		Node one = new Node(1);
		n.setState(NodeState.NULL_STATE);
		assertEquals(NodeState.NULL_STATE, n.getState());
		n.setFold(one);//this is what is tested
		assertEquals(NodeState.NULL_STATE, n.getState());
	}
	
	@Test
	public void DataFlowThree() {
		//Test the cap transition
		Node n = new Node(0);
		Node one = new Node(1);
		n.setState(NodeState.HYPEERCUBECAP);
		assertEquals(NodeState.HYPEERCUBECAP, n.getState());
		n.setFold(one);//this is what is tested
		assertEquals(NodeState.STANDARD, n.getState());
	}
	
	@Test
	public void BoundaryValueOne() {
		//Test the null id toString
		WebId null_id = WebId.NULL_WEB_ID;//this is what is tested
		//would be the same as new WebId(-1), but that does not
		//satisfy the invariant, luckily, there's a static one already
		//given that can be accessed to do the same thing.
		assertEquals("NULL WEB ID", null_id.toString());
	}
	@Test
	public void BoundaryValueTwo() {
		//test the zero height toString
		WebId null_id = new WebId(0,0);//this is what is tested
		assertEquals("", null_id.toString());
	}
	@Test
	public void BoundaryValueThree() {
		//test the more than zero height toString
		WebId null_id = new WebId(0,1);//this is what is tested
		assertEquals("0", null_id.toString());
		//To get that loop to run.
		WebId null_id2 = new WebId(0,2);//this is what is tested
		assertEquals("00", null_id2.toString());
	}
	

}
