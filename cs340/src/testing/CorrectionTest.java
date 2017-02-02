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
 * Tests for the other group's corrections.
 * @author Ben Romney
 */
public class CorrectionTest extends TestCase {

	public static Test suite() {
		return new TestSuite(CorrectionTest.class);
	}

	private HyPeerWeb web;

	public void setUp() {
		web = HyPeerWeb.getSingleton();
		web.clear();
	}

	public void test() {

		// Build web with 100 nodes
		Node node0 = new Node(0);
		web.addToHyPeerWeb(node0, Node.NULL_NODE);
		for (int i = 1; i < 99; i++) {
			Node node = new Node(0);
			node0.addToHyPeerWeb(node);
		}
		Node node100 = new Node(0);
		web.addToHyPeerWeb(node100, node0);

		// Test Sending from NULL_NODE to node 100
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
			Node.NULL_NODE.accept(send, param);
		} catch (Error e) {
			// Assert that an AssertionError was thrown
			assertEquals("java.lang.AssertionError", e.toString());			
		}
		
		// Test Sending from node 100 to "null"
		Parameters param2 = new Parameters();
		param2.set("target key", null);
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
		
		// Test Sending from node 100 to node 10000
		Parameters param3 = new Parameters();
		param3.set("target key", 10000);
		final ArrayList<Integer> intermediates3 = new ArrayList<Integer>();
		param3.set("intermediate list", intermediates3);
		Visitor send3 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(157, node.getWebId().getValue());
				assertEquals(3, intermediates3.size());
			}
		};
		try {
			node100.accept(send3, param3);
		} catch (Error e) {
			// Assert that an AssertionError was thrown
			assertEquals("java.lang.AssertionError", e.toString());		
		}
		
		// Test Sending empty parameters from node 0 to node 100
		Parameters param4 = new Parameters();
		param4.set("target key", 100);
		final ArrayList<Integer> intermediates4 = new ArrayList<Integer>();
		Visitor send4 = new SendVisitor() {
			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				assertEquals(100, node.getWebId().getValue());
				assertEquals(0, intermediates4.size());
			}
		};
		try {
			node0.accept(send4, param4);
		} catch (Error e) {
			// Assert that an AssertionError was thrown
			assertEquals("java.lang.AssertionError", e.toString());		
		}
		
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
