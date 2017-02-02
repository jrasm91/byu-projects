package testing;

import java.util.ArrayList;
import hypeerweb.HyPeerWeb;
import hypeerweb.node.Node;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.Visitor;
import hypeerweb.visitor.SendVisitor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Black Box Testing Homework
 * @author Ben Romney
 */
public class SendTest extends TestCase {

	public static Test suite() {
		return new TestSuite(SendTest.class);
	}

	private HyPeerWeb web;

	public void setUp() {
		web = HyPeerWeb.getSingleton();
	}

	public void test() {

		// Build web with 257 nodes
		web.clear();
		Node node0 = new Node(0);
		web.addToHyPeerWeb(node0, Node.NULL_NODE);
		for (int i = 1; i < 100; i++) {
			Node node = new Node(0);
			node0.addToHyPeerWeb(node);
		}
		Node node100 = new Node(0);
		web.addToHyPeerWeb(node100, node0);
		for (int i = 1; i < 154; i++) {
			Node node = new Node(0);
			node0.addToHyPeerWeb(node);
		}
		Node node254 = new Node(0);
		Node node255 = new Node(0);
		Node node256 = new Node(0);
		web.addToHyPeerWeb(node254, node0);
		web.addToHyPeerWeb(node255, node0);
		web.addToHyPeerWeb(node256, node0);

		
		/*********** Equivalence Partitioning Test ***********/

		// Test invalid starting node: Send from node -5 to node 100
		Parameters param = new Parameters();
		param.set("target key", 100);
		final ArrayList<Integer> intermediates = new ArrayList<Integer>();
		param.set("intermediate list", intermediates);
		Visitor send = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
			}
		};
		try {
			(new Node(-5)).accept(send, param);
		} catch (Error e) {
			// Assert that an AssertionError was thrown
			assertEquals("java.lang.AssertionError", e.toString());			
		}
		
		// Test invalid target node: Send from node 100 to node -5
		Parameters param2 = new Parameters();
		param2.set("target key", -5);
		final ArrayList<Integer> intermediates2 = new ArrayList<Integer>();
		param2.set("intermediate list", intermediates2);
		Visitor send2 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
			}
		};
		try {
			node100.accept(send2, param2);
		} catch (Error e) {
			// Assert that an AssertionError was thrown
			assertEquals("java.lang.AssertionError", e.toString());		
		}
		
		// Test valid input: Send from 100 to 157
		Parameters param3 = new Parameters();
		param3.set("target key", 157);
		final ArrayList<Integer> intermediates3 = new ArrayList<Integer>();
		param3.set("intermediate list", intermediates3);
		Visitor send3 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(157, node.getWebId().getValue());
				assertEquals(3, intermediates3.size());
			}
		};
		node100.accept(send3, param3);
		
		
		/*********** Boundary Value Analysis ***********/
		
		// Test 1: Send from node 0 to node 100
		Parameters param4 = new Parameters();
		param4.set("target key", 100);
		final ArrayList<Integer> intermediates4 = new ArrayList<Integer>();
		param3.set("intermediate list", intermediates4);
		Visitor send4 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(100, node.getWebId().getValue());
				assertEquals(0, intermediates4.size());
			}
		};
		node0.accept(send4, param4);
		
		// Test 2: Send from node 100 to node 0
		Parameters param5 = new Parameters();
		param5.set("target key", 0);
		final ArrayList<Integer> intermediates5 = new ArrayList<Integer>();
		param5.set("intermediate list", intermediates5);
		Visitor send5 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(0, node.getWebId().getValue());
				assertEquals(3, intermediates5.size());
			}
		};
		node100.accept(send5, param5);
		
		// Test 3: Send from node 254 to node 100
		Parameters param6 = new Parameters();
		param6.set("target key", 100);
		final ArrayList<Integer> intermediates6 = new ArrayList<Integer>();
		param6.set("intermediate list", intermediates6);
		Visitor send6 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(100, node.getWebId().getValue());
				assertEquals(4, intermediates6.size());
			}
		};
		node254.accept(send6, param6);
		
		// Test 4: Send from node 100 to node 254
		Parameters param7 = new Parameters();
		param7.set("target key", 254);
		final ArrayList<Integer> intermediates7 = new ArrayList<Integer>();
		param7.set("intermediate list", intermediates7);
		Visitor send7 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(254, node.getWebId().getValue());
				assertEquals(4, intermediates7.size());
			}
		};
		node100.accept(send7, param7);
		
		// Test 5: Send from node 255 to node 100
		Parameters param8 = new Parameters();
		param8.set("target key", 100);
		final ArrayList<Integer> intermediates8 = new ArrayList<Integer>();
		param8.set("intermediate list", intermediates8);
		Visitor send8 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(100, node.getWebId().getValue());
				assertEquals(4, intermediates8.size());
			}
		};
		node255.accept(send8, param8);
		
		// Test 6: Send from node 100 to node 255
		Parameters param9 = new Parameters();
		param9.set("target key", 255);
		final ArrayList<Integer> intermediates9 = new ArrayList<Integer>();
		param9.set("intermediate list", intermediates9);
		Visitor send9 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(255, node.getWebId().getValue());
				assertEquals(4, intermediates9.size());
			}
		};
		node100.accept(send9, param9);
		
		// Test 7: Send from node 256 to node 100
		Parameters param10 = new Parameters();
		param10.set("target key", 100);
		final ArrayList<Integer> intermediates10 = new ArrayList<Integer>();
		param10.set("intermediate list", intermediates10);
		Visitor send10 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(100, node.getWebId().getValue());
				assertEquals(3, intermediates10.size());
			}
		};
		node256.accept(send10, param10);
		
		// Test 8: Send from node 100 to node 256
		Parameters param11 = new Parameters();
		param11.set("target key", 256);
		final ArrayList<Integer> intermediates11 = new ArrayList<Integer>();
		param11.set("intermediate list", intermediates11);
		Visitor send11 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(256, node.getWebId().getValue());
				assertEquals(3, intermediates11.size());
			}
		};
		node100.accept(send11, param11);
		
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
