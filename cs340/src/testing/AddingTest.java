package testing;

import hypeerweb.HyPeerWebDatabase;
import hypeerweb.node.Node;
import hypeerweb.HyPeerWeb;
import junit.framework.*;

/**
 * @author Ben Romney
 */
public class AddingTest extends TestCase {

	public static Test suite() {
		return new TestSuite(AddingTest.class);
	}

	private HyPeerWeb web;

	public AddingTest() {
		super();
	}

	public AddingTest(String arg0) {
		super(arg0);
	}

	public void setUp() {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		HyPeerWebDatabase.getSingleton().clear();
		web = HyPeerWeb.getSingleton();
	}

	public void tearDown() {
		return;
	}

	public void test() {

		// Test 1
		//System.out.println("\nTest 1: Add using NULL_NODE as start node.");
		web.clear();
		Node node0 = new Node(0);
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		web.addToHyPeerWeb(node0, Node.NULL_NODE);
		web.addToHyPeerWeb(node1, Node.NULL_NODE);
		web.addToHyPeerWeb(node2, Node.NULL_NODE);
		//System.out.println(web.toString());
		assertEquals(web.size(), 3);
		assertEquals(node2.getWebId().getHeight(), 2);
		assertEquals(web.contains(node0), true);
		assertEquals(web.contains(node1), true);
		assertEquals(web.contains(node2), true);
		//System.out.println("Test 1 passed!");
		
		// Test 2
		//System.out.println("\nTest 2: Add using existing nodes as start nodes.");
		web.clear();
		node0 = new Node(0);
		node1 = new Node(1);
		node2 = new Node(2);
		web.addToHyPeerWeb(node0, null);
		web.addToHyPeerWeb(node1, node0);
		web.addToHyPeerWeb(node2, node1);
		//System.out.println(web.toString());
		assertEquals(web.size(), 3);
		assertEquals(node2.getWebId().getHeight(), 2);
		assertEquals(web.contains(node0), true);
		assertEquals(web.contains(node1), true);
		assertEquals(web.contains(node2), true);
		//System.out.println("Test 2 passed!");

		// Test 3
		//System.out.println("\nTest 3: Add nodes to large HyPeerWeb.");
		web.clear();
		node0 = new Node(0);
		node1 = new Node(1);
		node2 = new Node(2);
		web.addToHyPeerWeb(node0, null);
		for (int i = 1; i < 20; i++) {
			Node node = new Node(0);
			node0.addToHyPeerWeb(node);
		}
		web.addToHyPeerWeb(node1, node0);
		web.addToHyPeerWeb(node2, node0);
		//System.out.println(web.toString());
		assertEquals(web.size(), 22);
		assertEquals(node0.getWebId().getHeight(), 5);
		assertEquals(web.contains(node1), true);
		assertEquals(web.contains(node2), true);
		//System.out.println("Test 3 passed!");

		// Test 4
		//System.out.println("\nTest 4: Build web with 1000 nodes");
		web.clear();
		node0 = new Node(0);
		web.addToHyPeerWeb(node0, null);
		for (int i = 1; i < 1000; i++) {
			Node node = new Node(0);
			node0.addToHyPeerWeb(node);
		}
		//System.out.println(web.toString());
		assertEquals(web.size(), 1000);
		assertEquals(node0.getWebId().getHeight(), 10);
		//System.out.println("Test 4 passed!");

		// Test 5
		//System.out.println("\nTest 5: Add node 20 to node 19.");
		web.clear();
		node0 = new Node(0);
		node1 = new Node(1);
		node2 = new Node(2);
		web.addToHyPeerWeb(node0, null);
		for (int i = 1; i < 19; i++) {
			Node node = new Node(0);
			node0.addToHyPeerWeb(node);
		}
		web.addToHyPeerWeb(node1, node0);
		web.addToHyPeerWeb(node2, node1);
		//System.out.println(web.toString());
		assertEquals(web.size(), 21);
		assertEquals(web.contains(node1), true);
		assertEquals(web.contains(node2), true);
		assertEquals(node0.getWebId().getHeight(), 5);
		assertEquals(node1.getWebId().getHeight(), 5);
		assertEquals(node2.getWebId().getHeight(), 5);
		assertEquals(node1.constructSimplifiedNodeDomain().getNeighbors()
				.contains(node2.getWebId().getValue()), false);
		assertEquals(node2.constructSimplifiedNodeDomain().getNeighbors()
				.contains(node1.getWebId().getValue()), false);
		//System.out.println("Test 5 passed!");

	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
