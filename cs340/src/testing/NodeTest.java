package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import hypeerweb.HyPeerWeb;
import hypeerweb.node.Node;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jason Rasmussen
 * 
 * Tests the Node.java class with BlackBox testing techniques.
 *
 */
public class NodeTest {

	static HyPeerWeb hypeerweb;

	@BeforeClass
	public static void setUpBeforeClass() {
		hypeerweb = HyPeerWeb.getSingleton();
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Before
	public void setUp() {
		hypeerweb.clear();
	}

	@After
	public void tearDown() {
		hypeerweb.clear();

	}

	/**
	 * Equivalence Partition testing for addToHyPeerWeb() method in Node.java
	 * tests with invalid input and valid input including different sizes of
	 * HyPeerWeb
	 */
	@Test
	public void testEquivalencePartitioning() {

		Node node0 = new Node(0);
		hypeerweb.addNode(node0);

		// test invalid input
		try {
			node0.addToHyPeerWeb(null);
			fail(); // adding a null node is invalid
		} catch (NullPointerException e) {
			assertTrue(true);
		}
		try {
			node0.addToHyPeerWeb(node0);
			fail(); // adding node that is already in hypeerweb is invalid
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}

		// test valid input

		// size 1 (special case)
		createHyPeerWebWith(1);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(2, hypeerweb.size());

		// size 2 (special case)
		createHyPeerWebWith(2);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(3, hypeerweb.size());

		// size 3 - 6
		for (int i = 3; i <= 6; i++) {
			createHyPeerWebWith(i);
			node0 = hypeerweb.getNode(0);
			node0.addToHyPeerWeb(new Node(0));
			assertEquals(i + 1, hypeerweb.size());
		}

		// size 7 (almost perfect hypeercube)
		createHyPeerWebWith(7);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(8, hypeerweb.size());

		// size 8 (perfect hypeercube)
		createHyPeerWebWith(7);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(8, hypeerweb.size());

		// size 9 (perfect hypeercube + 1)
		createHyPeerWebWith(8);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(9, hypeerweb.size());

		// size 10-14 (perfect normal cases)
		for (int i = 10; i <= 14; i++) {
			createHyPeerWebWith(i);
			node0 = hypeerweb.getNode(0);
			node0.addToHyPeerWeb(new Node(0));
			assertEquals(i + 1, hypeerweb.size());
		}

		// size 15 (almost perfect hypeercube)
		createHyPeerWebWith(15);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(16, hypeerweb.size());

		// size 16 (perfect hypeercube)
		createHyPeerWebWith(16);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(17, hypeerweb.size());

		// size 17 (perfect hypeercube + 1)
		createHyPeerWebWith(17);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(18, hypeerweb.size());

		// size 18+
		for (int i = 18; i <= 33; i++) {
			createHyPeerWebWith(i);
			node0 = hypeerweb.getNode(0);
			node0.addToHyPeerWeb(new Node(0));
			assertEquals(i + 1, hypeerweb.size());
		}
	}

	/**
	 * Boundary Value Analysis Partition testing for addToHyPeerWeb() method in
	 * Node.java Tests different sizes of hypeerweb (perfect cube - 1, perfect
	 * cube, perfect cube + 1
	 */
	@Test
	public void testBoundaryValueAnalysis() {

		// size 1 (special case)
		createHyPeerWebWith(1);
		Node node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(2, hypeerweb.size());

		// size 2 (special case)
		createHyPeerWebWith(2);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(3, hypeerweb.size());

		// size 7 (almost perfect hypeercube)
		createHyPeerWebWith(7);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(8, hypeerweb.size());

		// size 8 (perfect hypeercube)
		createHyPeerWebWith(7);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(8, hypeerweb.size());

		// size 9 (perfect hypeercube + 1)
		createHyPeerWebWith(8);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(9, hypeerweb.size());

		// size 15 (almost perfect hypeercube)
		createHyPeerWebWith(15);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(16, hypeerweb.size());

		// size 16 (perfect hypeercube)
		createHyPeerWebWith(16);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(17, hypeerweb.size());

		// size 17 (perfect hypeercube + 1)
		createHyPeerWebWith(17);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(18, hypeerweb.size());

		// size 31 (almost perfect hypeercube)
		createHyPeerWebWith(31);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(32, hypeerweb.size());

		// size 32 (perfect hypeercube)
		createHyPeerWebWith(32);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(33, hypeerweb.size());

		// size 33 (perfect hypeercube + 1)
		createHyPeerWebWith(33);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(34, hypeerweb.size());

		// size huge - 1
		createHyPeerWebWith(3999);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(4000, hypeerweb.size());

		// size huge
		createHyPeerWebWith(4000);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(4001, hypeerweb.size());

		// size huge + 1
		createHyPeerWebWith(4001);
		node0 = hypeerweb.getNode(0);
		node0.addToHyPeerWeb(new Node(0));
		assertEquals(4002, hypeerweb.size());
	}

	static private void createHyPeerWebWith(int numberOfNodes) {
		hypeerweb.clear();
		Node node0 = new Node(0);
		hypeerweb.addToHyPeerWeb(node0, null);

		for (int i = 1; i < numberOfNodes; i++) {
			Node node = new Node(0);
			node0.addToHyPeerWeb(node);
		}
	}

}
