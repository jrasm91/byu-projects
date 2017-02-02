package testing;

import hypeerweb.node.Node;
import hypeerweb.node.NodeState;

import java.util.HashSet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Ben Romney
 * 
 */
public class BenWhiteBox extends TestCase {

	public static Test suite() {
		return new TestSuite(BenWhiteBox.class);
	}

	public void setUp() {
		return;
	}

	public void tearDown() {
		return;
	}

	public void test() {

		// Uncomment these tests and make createSetOfNeighborIds() and the
		// connections attribute in Node public instead of private

		// Loop Test on Node's createSetOfNeighborIds()
		// Loop 0 times


//		Node myNode = new Node(0);
//		HashSet<Integer> neighbors = new HashSet<Integer>();
//		neighbors = myNode.createSetOfNeighborIds();
//		assertEquals(0, neighbors.size()); // Loop 1 time
//		Node neighbor1 = new Node(1);
//		myNode.addNeighbor(neighbor1);
//		neighbors = myNode.createSetOfNeighborIds();
//		assertEquals(1, neighbors.size());
//		// Loop 3 times
//		Node neighbor2 = new Node(2);
//		Node neighbor3 = new Node(3);
//		myNode.addNeighbor(neighbor2);
//		myNode.addNeighbor(neighbor3);
//		neighbors = myNode.createSetOfNeighborIds();
//		assertEquals(3, neighbors.size());
//
//		// Internal Boundary Value Test on Connection's addNeighbor()
//		// The input node's webId is equal
//		myNode = new Node(2);
//		try {
//			myNode.connections.addNeighbor(myNode);
//		} catch (Exception e) {
//			assertEquals("you are a potato", e.getMessage());
//		}
//		// The input node's webId is slightly larger myNode = new Node(2);
//		myNode.connections.addNeighbor(neighbor3);
//		assertEquals(neighbor3, myNode.connections.getBiggestNeighbor());
//		// The input node's webId is slightly smaller
//		myNode = new Node(2);
//		myNode.connections.addNeighbor(neighbor1);
//		assertEquals(neighbor1,
//				myNode.connections.getSmallestNeighborWithoutChild());
//
//		// Relational Test on Connection's addNeighbor()
//		// The input node's webId is smaller and its state is not standard
//		myNode = new Node(2);
//		myNode.connections.addNeighbor(neighbor1);
//		assertEquals(neighbor1,
//				myNode.connections.getSmallestNeighborWithoutChild());
//		// The input node's webId is smaller and its state is standard
//		myNode = new Node(2);
//		myNode.connections.addNeighbor(neighbor1);
//		assertEquals(neighbor1,
//				myNode.connections.getSmallestNeighborWithChild());
//
//		// Dataflow Test on Node's addNeighbor() // The state should remain
//		null
//		myNode = new Node(2);
//		myNode.addNeighbor(neighbor1);
//		assertEquals(null, myNode.getState());
//		// The state should change to downpointing
//		myNode = new Node(2);
//		myNode.setState(NodeState.TERMINAL);
//		myNode.addNeighbor(neighbor3);
//		assertEquals(NodeState.DOWNPOINTING, myNode.getState());

	}

}
