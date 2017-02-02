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

public class AdamBlackBox {

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

	@Test
	public void equivalencePartitioningTest() {

		// valid - broadcast on complete hypercube
		// - broadcast on incomplete hypercube
		// invalid - broadcast on empty HyPeerWeb

		// invalid
		testBroadcast(0);

		// valid
		// complete hypercube
		testBroadcast(1);
		testBroadcast(2);
		testBroadcast(4);
		testBroadcast(8);
		testBroadcast(16);
		testBroadcast(32);

		// incomplete hypercube
		testBroadcast(3);
		testBroadcast(7);
		testBroadcast(9);
		testBroadcast(21);

		System.out.println("finished equivalencePartitioningTest");
	}

	@Test
	public void boundaryValueTest() {
		// broadcast on a HyPeerWeb of size 0, 1, 2, 3, and big.
		testBroadcast(0);
		testBroadcast(1);
		testBroadcast(2);
		testBroadcast(3);
		testBroadcast(1000);

		System.out.println("finished boundaryValueTest");
	}

	public void testBroadcast(int sizeOfHyPeerWeb) {
		addToHyPeerWeb(sizeOfHyPeerWeb);
		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();

		BroadcastHelper bh = new BroadcastHelper(new BroadcastVisitor() {
			@Override
			protected void operation(Node node, Parameters parameters) {
				node.getContents().set("I received the message", true);
			}
		});

		// start the broadcast from each node in the HyPeerWeb
		for (int i = 0; i < hypeerweb.size(); i++) {
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
	}
}
