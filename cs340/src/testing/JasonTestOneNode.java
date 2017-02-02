package testing;

import org.junit.Test;

import hypeerweb.HyPeerWeb;
import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.node.Node;

public class JasonTestOneNode {

	/**
	 * @param args
	 */
	@Test
	public void test() {

		HyPeerWeb hypeerweb = HyPeerWeb.getSingleton();
		hypeerweb.clear();
		
		hypeerweb.addToHyPeerWeb(new Node(0), null);
		
		Node node0 = hypeerweb.getNode(0);
		
		node0.addToHyPeerWeb(new Node(1));
		node0.addToHyPeerWeb(new Node(2));

		Node node1 = hypeerweb.getNode(0);
		node1.removeFromHyPeerWeb();
		verify(hypeerweb, node1);

		node1 = hypeerweb.getNode(1);
		node1.removeFromHyPeerWeb();
		verify(hypeerweb, node1);
		
		hypeerweb.clear();
		
		
	}
	
	private static void verify(HyPeerWeb hypeerweb, Node delnode) {
		for (int i = 0; i < hypeerweb.size(); i++) {
			Node nodei = hypeerweb.getNode(i);
			SimplifiedNodeDomain simplifiedNodeDomain = nodei.constructSimplifiedNodeDomain();
			ExpectedResult expectedResult = new ExpectedResult(hypeerweb.size(), i);
			
			if (!simplifiedNodeDomain.equals(expectedResult)) {
				printDeletionErrorMessage(hypeerweb.size(), delnode, simplifiedNodeDomain, expectedResult);
			}
		}
	}
	
	private static void printDeletionErrorMessage(int size, Node deleteNode, SimplifiedNodeDomain simplifiedNodeDomain, ExpectedResult expectedResult) {

			System.out.println("-------------------------------------------------------------");
			System.out.println("DELTION ERROR when:");
			System.out.println("HyPeerWebSize = " + size + ", Delete Location: " + deleteNode);
			System.out.println("Node Information:");
			System.out.println(simplifiedNodeDomain);
			System.out.println();
			System.out.println("Expected Information");
			System.out.println(expectedResult);
	}

}
