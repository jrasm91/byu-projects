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

public class AdamWhiteBox {

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

	// ** The method being tested **//

	/*
	 * public void visit(Node node, Parameters parameters) {
	 * parameters.set(STARTED_KEY, true); operation(node, parameters); char[]
	 * webId = node.getWebId().toString().toCharArray(); int index = 0; for
	 * (char digit : webId) { if (digit == '1') break; webId[index] = '1'; int
	 * id = Integer.parseInt(new String(webId), 2);
	 * node.getNodeByWebId(id).accept(this, parameters); webId[index] = '0';
	 * index++; } }
	 */

	@Test
	public void loopTest() {
		// skip for loop
		testBroadcast(1);

		// go into for loop once
		testBroadcast(2);

		// go into for loop many times
		testBroadcast(32);

		System.out.println("finished loop test");
	}

	@Test
	public void relationalTest() {
		// two cases:
		// if(digit == '1') && if(digit == '0');

		testBroadcast(2);
		System.out.println("finished relational test");
	}

	@Test
	public void internalBoundaryTest() {
		// how large can the char[] of the webID be?
		// 0 or huge

		// size of char[] = 0
		testBroadcast(1);

		// size of char[] = 1
		testBroadcast(2);

		// size of char[] = 10
		testBroadcast(1000);
		System.out.println("finished boundary test");
	}

	@Test
	public void dataflowTest() {
		// path from every definition to every use
		// hits every definition and every use
		testBroadcast(32);
		System.out.println("finished data flow test");
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
