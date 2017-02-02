package testing;

import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.node.Node;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Ben Romney
 */
public class DeletingTest extends TestCase {

	public static Test suite() {
		return new TestSuite(DeletingTest.class);
	}

	private HyPeerWeb web;

	public DeletingTest() {
		super();
	}

	public DeletingTest(String arg0) {
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

		// Build Web
		//System.out.println("\nBuilding web with 5 nodes...");
		web.clear();
		Node node0 = new Node(0);
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		web.addToHyPeerWeb(node0, Node.NULL_NODE);
		web.addToHyPeerWeb(node1, node0);
		web.addToHyPeerWeb(node2, node0);
		web.addToHyPeerWeb(node3, node0);
		web.addToHyPeerWeb(node4, node0);
		//System.out.println(web.toString());

		// Test 1
		//System.out.println("\nTest 1: Delete the 0 node.");
		node0.removeFromHyPeerWeb();
		//System.out.println(web.toString());
		assertEquals(4, web.size());
		for (int i = 0; i < 4; i++) {
			Node nodei = web.getNode(i);
			SimplifiedNodeDomain sdn = nodei.constructSimplifiedNodeDomain();
			ExpectedResult er = new ExpectedResult(4, i);
			assertEquals(er, sdn);
		}
		//System.out.println("Test 1 passed!");
		
		// Add 3 more nodes
		//System.out.println("\nAdding 3 more nodes...");
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		Node node7 = new Node(7);
		web.addToHyPeerWeb(node5, node4);
		web.addToHyPeerWeb(node6, node4);
		web.addToHyPeerWeb(node7, node4);
		//System.out.println(web.toString());
		
		// Test 2
		//System.out.println("\nTest 2: Delete a body node.");
		node5.removeFromHyPeerWeb();
		//System.out.println(web.toString());
		assertEquals(6, web.size());
		for (int i = 0; i < 6; i++) {
			Node nodei = web.getNode(i);
			SimplifiedNodeDomain sdn = nodei.constructSimplifiedNodeDomain();
			ExpectedResult er = new ExpectedResult(6, i);
			assertEquals(er, sdn);
		}
		//System.out.println("Test 2 passed!");
		
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
