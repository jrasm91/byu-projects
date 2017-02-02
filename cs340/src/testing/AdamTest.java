package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.node.*;
import hypeerweb.visitor.BroadcastHelper;
import hypeerweb.visitor.BroadcastVisitor;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.SendVisitor;

public class AdamTest {
//is this working?
	@Before
	public void setUp() throws Exception {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		HyPeerWeb.getSingleton().clear();
	}

	@After
	public void tearDown() throws Exception {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		HyPeerWeb.getSingleton().clear();
	}

	public void addToHyPeerWeb(int numNodesToAdd) {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		HyPeerWeb.getSingleton().clear();

		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		Node startNode = new Node(0);

		if (numNodesToAdd > 0) {
			hypeerweb.addToHyPeerWeb(startNode, null);
		}

		for (int i = 1; i < numNodesToAdd; i++) {
			Node n = new Node(i);
			hypeerweb.addToHyPeerWeb(n, startNode);
		}

		assertEquals(numNodesToAdd, hypeerweb.size());
	}
	
	public void addNullNodesToHyPeerWeb(int numNodesToAdd) {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		HyPeerWeb.getSingleton().clear();

		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		Node startNode = Node.NULL_NODE;

		if (numNodesToAdd > 0) {
			hypeerweb.addToHyPeerWeb(startNode, null);
		}

		for (int i = 1; i < numNodesToAdd; i++) {
			Node n = Node.NULL_NODE;
			hypeerweb.addToHyPeerWeb(n, startNode);
		}

		assertEquals(numNodesToAdd, hypeerweb.size());
	}
	
	@Test
	public void testNullNodes() {
		addNullNodesToHyPeerWeb(5);
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		
		BroadcastHelper bh = new BroadcastHelper(new BroadcastVisitor() {
			@Override
			protected void operation(Node node, Parameters parameters) {
				node.getContents().set("I received the message", true);
				// System.out.println("Node: " + node.getWebId().getValue() +
				// " received the message");
			}
		});

		// start the broadcast from each node in the HyPeerWeb
		for (int i = 0; i < hypeerweb.size(); i++) {
			// System.out.println("Accept on Node: " + i);
			// System.out.println();
			Parameters parameters = SendVisitor.createInitialParameters(0);
			hypeerweb.getNode(i).accept(bh, parameters);

			// after the broadcast has finished, make sure each node has the
			// appropriate message
			for (int j = 0; j < hypeerweb.size(); j++) {
				Node currNode = hypeerweb.getNode(j);
				assertTrue(currNode.getContents().containsKey(
						"I received the message"));
				currNode.getContents().clear();
			}
		}
		
		System.out.println("finished nullNodes test");
	}

	@Test
	public void test() {
		for (int i = 1; i < 32; i++) {
			testBroadcast(i);
		}

		testBroadcast(1000);
		System.out.println("Passed test");
	}

	public void testBroadcast(int sizeOfHyPeerWeb) {
		addToHyPeerWeb(sizeOfHyPeerWeb);
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();

		// test broadcast

		BroadcastHelper bh = new BroadcastHelper(new BroadcastVisitor() {
			@Override
			protected void operation(Node node, Parameters parameters) {
				node.getContents().set("I received the message", true);
				// System.out.println("Node: " + node.getWebId().getValue() +
				// " received the message");
			}
		});

		// start the broadcast from each node in the HyPeerWeb
		for (int i = 0; i < hypeerweb.size(); i++) {
			// System.out.println("Accept on Node: " + i);
			// System.out.println();
			Parameters parameters = SendVisitor.createInitialParameters(0);
			hypeerweb.getNode(i).accept(bh, parameters);

			// after the broadcast has finished, make sure each node has the
			// appropriate message
			for (int j = 0; j < hypeerweb.size(); j++) {
				Node currNode = hypeerweb.getNode(j);
				assertTrue(currNode.getContents().containsKey(
						"I received the message"));
				currNode.getContents().clear();
			}
		}

		// System.out.println("Passed Test with HyPeerWeb size: " +
		// sizeOfHyPeerWeb);
	}

	@Test
	public void testRemoveThenBroadcast() {
		// remove from hypeerweb then broadcast
		// System.out.println("test2");
		// System.out.println();

		addToHyPeerWeb(100);
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();

		BroadcastHelper bh = new BroadcastHelper(new BroadcastVisitor() {
			@Override
			protected void operation(Node node, Parameters parameters) {
				node.getContents().set("I received the message", true);
				// System.out.println("Node: " + node.getWebId().getValue() +
				// " received the message");
			}
		});

		// remove each node from 99 down to 1
		for (int i = hypeerweb.size() - 1; i > 0; i--) {
			hypeerweb.getNode(i).removeFromHyPeerWeb();

			// start the broadcast from each node in the HyPeerWeb
			for (int j = 0; j < hypeerweb.size(); j++) {
				// System.out.println("Accept on Node: " + j);
				// System.out.println();
				Parameters parameters = SendVisitor.createInitialParameters(0);
				hypeerweb.getNode(j).accept(bh, parameters);

				// after the broadcast has finished, make sure each node has the
				// appropriate message
				for (int k = 0; k < hypeerweb.size(); k++) {
					Node currNode = hypeerweb.getNode(k);
					assertTrue(currNode.getContents().containsKey(
							"I received the message"));
					currNode.getContents().clear();
				}
			}
		}
		System.out.println("Passed Test2");
	}
}
