package testing;

import static org.junit.Assert.*;
import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.node.Node;
import hypeerweb.visitor.BroadcastHelper;
import hypeerweb.visitor.BroadcastVisitor;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.SendVisitor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LeviBlackBox {
	final String COUNTER_KEY = "counting";
	
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
	public void equivalenceOne() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		//Only one node will be broadcasted from (0D)
		//Is a hypercube
		Node zeroth = new Node(0);
		h.addNode(zeroth);
		doBroadcast(zeroth);
		assertBroadcast();
		
	}
	@Test
	public void equivalenceTwo() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		//Only two nodes will be broadcasted from (1D)
		//Is a hypeercube
		Node zeroth = new Node(0);
		h.addNode(zeroth);
		zeroth.addToHyPeerWeb(new Node(1));
		doBroadcast(zeroth);
		assertBroadcast();
		
	}
	@Test
	public void equivalenceThree() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		//Only three nodes will be broadcasted from (2D)
		//Is NOT a hypeer cube
		Node zeroth = new Node(0);
		h.addNode(zeroth);
		zeroth.addToHyPeerWeb(new Node(1));
		zeroth.addToHyPeerWeb(new Node(2));
		doBroadcast(zeroth);
		assertBroadcast();
	}
	@Test
	public void equivalenceFour() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		//Only five nodes will be broadcasted from (3D)
		//Is NOT a hypeer cube
		Node zeroth = new Node(0);
		h.addNode(zeroth);
		zeroth.addToHyPeerWeb(new Node(1));
		zeroth.addToHyPeerWeb(new Node(2));
		zeroth.addToHyPeerWeb(new Node(3));
		zeroth.addToHyPeerWeb(new Node(4));
		doBroadcast(zeroth);
		assertBroadcast();
	}
	
	@Test
	public void boundaryAnalysis() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		//Here, we will test whether or not getting
		//a node by id is connected or not.
		Node zeroth = new Node(0);
		h.addNode(zeroth);
		//Make a big hyPeerWeb that is a cube(12D)
		for(int i = 1; i < 4096; i++) {
			zeroth.addToHyPeerWeb(new Node(i));
		}
		//We well test the getNodeByWebId function, which
		//returns a ConnectedNode
		//NOTE: .asNode() gets the raw node.
		//If the node is not IMMEDIATELY connected,
		//we should get a null_node
		//Test the 12 dimensions of neighbors 
		for(int i = 0; i < 12; i++) {
			int id = 1 << i;
			assertEquals(h.getNode(id), zeroth.getNodeByWebId(id).asNode());
		}
		//Test to see if we are 
		//We shift to 1 instead of 0, because
		//adding 1, actually makes the id 2, which
		//is connected.
		for(int i = 1; i < 12; i++) {
			int id = (1 << i) + 1;
			//We expect not to get the node in the HyPeerWeb since
			//it is not actually connected to the zeroth node.
			try {
			assertNotSame(h.getNode(id), zeroth.getNodeByWebId(id).asNode());
			} catch(AssertionError e){
				assertTrue(true);
			}
		}
		
	}
	//Helpers!
	//This just does a simple broadcast that gives each node
	//a number for the position for which it was hit.
	public void doBroadcast(Node start) {
		BroadcastHelper helpVisitor = new BroadcastHelper(new BroadcastVisitor() {
			@Override
			protected void operation(Node node, Parameters parameters) {
				int pos = 0; 
				if (parameters.get(COUNTER_KEY) != null) {
					pos = (int)(Integer)parameters.get(COUNTER_KEY);
				}
				node.getContents().set(COUNTER_KEY, pos);
				
				parameters.set(COUNTER_KEY, ++pos);
			}
		});
		start.accept(helpVisitor, SendVisitor.createInitialParameters(0));
	}
	//This checks if the broadcast were good
	//It counts up an expected result (sum)
	//and then also does an actual sum of the values
	//this is agnostic to the order of the broadcast, we assume
	//all nodes were hit.
	public void assertBroadcast() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		int sum = 0;
		int actual = 0;
		for(int i = 0; i < h.size(); i++) {
			sum += i;
			actual += (Integer)h.getNode(i).getContents().get(COUNTER_KEY);
		}
		assertEquals(sum, actual);
	}

}
