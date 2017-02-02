package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import hypeerweb.HyPeerWeb;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.node.*;
import hypeerweb.visitor.BroadcastHelper;
import hypeerweb.visitor.BroadcastVisitor;
import hypeerweb.visitor.Parameters;
import hypeerweb.visitor.SendVisitor;

public class leviTest {
	final String COUNTER_KEY = "counting";
	@Before
	public void setUp() throws Exception {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		if(HyPeerWeb.getSingleton().size() > 0) {
			HyPeerWeb.getSingleton().clear();
		}
		
	}

	@After
	public void tearDown() throws Exception {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		if(HyPeerWeb.getSingleton().size() > 0) {
			HyPeerWeb.getSingleton().clear();
		}
	}

	@Test
	public void test() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		{
			Node n = new Node(0);
			h.addNode(n);
			h.removeNode(n);
			assertEquals(0, h.size());
		}
		Node zeroth = new Node(0);
		h.addToHyPeerWeb(zeroth, null);
		Node lastNode = null;
		for(int i = 1; i < 10; i++) {
			Node n = new Node(i);
			h.addToHyPeerWeb(n, zeroth);
			lastNode = n;
		}
		for(int i = 0; i < 3; i++) {
			h.getNode(i*2).removeFromHyPeerWeb();
		}
		//The last node should be 
		assertEquals(0,lastNode.getWebId().getValue());
		h.clear();
		assertEquals(0, h.size());
	}
	@Test
	public void sendTest() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		Node zeroth = new Node(0);
		h.addToHyPeerWeb(zeroth, null);
		for(int i = 1; i < 4000; i++) {
			Node n = new Node(i);
			h.addToHyPeerWeb(n, zeroth);
		}
		SendVisitor v = new SendVisitor() {
			//this might not do anything, simply a method we should call while traversing
			protected void intermediateOperation(Node node, Parameters parameters) {
				node.getContents().set(COUNTER_KEY, parameters.get(COUNTER_KEY));
				int val = (Integer)parameters.get(COUNTER_KEY);
				parameters.set(COUNTER_KEY, val+1);
			}

			@Override
			protected void targetOperation(Node node, Parameters parameters) {
				intermediateOperation(node, parameters);
			}
		};
		Parameters p = SendVisitor.createInitialParameters(3000);
		p.set(COUNTER_KEY, 0);
		h.getNode(2000).accept(v, p);
		assertTrue(h.getNode(3000).getContents().containsKey(COUNTER_KEY));
		for(int i = 0; i < 4000; i++) {
			Node n = h.getNode(i);
			if(n.getContents().containsKey(COUNTER_KEY)) {
				System.out.print(i);
				System.out.print(" ");
				System.out.println(n.getContents().get(COUNTER_KEY));
			}
		}
	}
	
	@Test
	public void test2() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		Node zeroth = new Node(0);
		h.addToHyPeerWeb(zeroth, null);
		assertEquals(NodeState.HYPEERCUBECAP, zeroth.getState());
		for(int i = 1; i < 10; i++) {
			Node n = new Node(i);
			h.addToHyPeerWeb(n, zeroth);
		}
		assertEquals(10, h.size());
		assertEquals(Node.NULL_NODE, zeroth.getSmallestNeighborWithChild());
		assertEquals(NodeState.STANDARD, zeroth.getState());
		SimplifiedNodeDomain sf = zeroth.constructSimplifiedNodeDomain();
		assertEquals(7, sf.getSurrogateFold());
		//Now add a beastly amount. 
		for(int i = 0; i < 2000; i++) {
			Node n = new Node(i);
			h.addToHyPeerWeb(n, zeroth);
		}
		//Now remove a half beastly amount.
		for(int i = 0; i < 40; i++) {
			h.getNode(i*2).removeFromHyPeerWeb();
		}
		for(int i = 0; i < 40; i++) {
			h.getNode(i*2+1).removeFromHyPeerWeb();
		}
		assertEquals(2000-80+10, h.size());
		zeroth = h.getNode(0);
		assertEquals(1023, zeroth.constructSimplifiedNodeDomain().getSurrogateFold());
		//Now let's try to throw knives at it in case there's still any states that are bad.
		Random generator = new Random();
		for(int i = 0; i < 2000-80+10 - 8; i++) {
			int pos = generator.nextInt(h.size());
			h.getNode(pos).removeFromHyPeerWeb();
		}
		zeroth = h.getNode(0);
		assertEquals(-1, zeroth.constructSimplifiedNodeDomain().getSurrogateFold());
		assertEquals(7, zeroth.constructSimplifiedNodeDomain().getFold());
		assertEquals(NodeState.HYPEERCUBECAP, h.getNode(7).getState());
		//It seems we are back to a normal HyPeerWeb now.
		
	}
	
	@Test
	public void BroadCastTest() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		Node zeroth = new Node(0);
		h.addToHyPeerWeb(zeroth, null);
		for(int i = 1; i < 8; i++) {
			Node n = new Node(i);
			h.addToHyPeerWeb(n, zeroth);
		}
		
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
		//Start from an arbitrary node
		h.getNode(4).accept(helpVisitor, SendVisitor.createInitialParameters(0));
		int checked = 0;
		for(int i = 0; i < 8; i++) {
			Integer val = (Integer)h.getNode(i).getContents().get(COUNTER_KEY);
			checked += val;
		}
		assertEquals(7+6+5+4+3+2+1, checked);
		
		//Now a much bigger (than 8) HyPeerWeb
		int amount = checked;
		for(int i = 8; i < 4000; i++) {
			amount += i;
			Node n = new Node(i);
			h.addToHyPeerWeb(n, zeroth);
		}
		//Now visit from some arbitrary node again
		h.getNode(409).accept(helpVisitor, SendVisitor.createInitialParameters(0));
		checked = 0;
		for(int i = 0; i < h.size(); i++) {
			Integer val = (Integer)h.getNode(i).getContents().get(COUNTER_KEY);
			checked += val;
		}
		assertEquals(amount, checked);
	}
	@Test
	public void WhiteboxBroadcast() {
		HyPeerWeb h = HyPeerWeb.getSingleton();
		h.clear();
		Node zeroth = new Node(0);
		h.addToHyPeerWeb(zeroth, null);
		for(int i = 1; i < 8; i++) {
			Node n = new Node(i);
			h.addToHyPeerWeb(n, zeroth);
		}
		final String COUNTER_KEY = "counting";
		final String DID_KEY = "did";
		BroadcastVisitor bvreset = new BroadcastVisitor() {
			@Override
			protected void operation(Node node, Parameters parameters) {
				node.getContents().set(DID_KEY, 0);
				node.getContents().set(COUNTER_KEY, 0);
			}
		};
		BroadcastVisitor bv = new BroadcastVisitor() {
			@Override
			protected void operation(Node node, Parameters parameters) {
				int pos = 1;
				if (parameters.get(COUNTER_KEY) != null) {
					pos = (int)(Integer)parameters.get(COUNTER_KEY);
				}
				node.getContents().set(COUNTER_KEY, pos);
				node.getContents().set(DID_KEY, 1);
				
				parameters.set(COUNTER_KEY, ++pos);
			}
		};
		zeroth.accept(bvreset, new Parameters());
		h.getNode(2).accept(bv, new Parameters());
		for(int i = 0; i < 8; i++) {
			Node n = h.getNode(i);
			/*System.out.print("Node ");
			System.out.print(i);
			System.out.print(" ");
			System.out.print(n.getContents().get(COUNTER_KEY));
			System.out.print(n.getContents().get(DID_KEY));
			System.out.println();*/
			if (i == 2 || i == 6) {
				assertEquals(1, n.getContents().get(DID_KEY));
				assertEquals(i == 2 ? 1 : 2, n.getContents().get(COUNTER_KEY));
			} else {
				assertEquals(0, n.getContents().get(COUNTER_KEY));
				assertEquals(0, n.getContents().get(DID_KEY));
			}
			
		}
	}
}
