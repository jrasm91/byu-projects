package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import hypeerweb.HyPeerWebDatabase;
import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.node.Node;
import hypeerweb.node.NodeState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jason Rasmussen
 * 
 *         Uses Whitebox testing principles to test HyPeerWebDatabase.java
 * 
 */
public class HyPeerWebDatabaseTest {

	static HyPeerWebDatabase db;

	@BeforeClass
	public static void setUpBeforeClass() {
		HyPeerWebDatabase.initHyPeerWebDatabase();
		db = HyPeerWebDatabase.getSingleton();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		db.save(new LinkedList<SimplifiedNodeDomain>());
	}

	@Before
	public void setUp() {
		db.clear();
	}

	@After
	public void tearDown() {
	}

	/**
	 * Loop testing for method getAll() in HyPeerWebDatabase.java Tests size 0,
	 * size 1, size 2, and size many (10)
	 * 
	 */
	@Test
	public void testLoop() {

		List<SimplifiedNodeDomain> testNodes = getTestNodes();

		// size of 0
		List<SimplifiedNodeDomain> nodes = db.getAll();
		assertEquals(0, nodes.size());

		// size of 1
		db.add(testNodes.get(0));
		nodes = db.getAll();
		assertEquals(1, nodes.size());
		assertEquals(testNodes.get(0), nodes.get(0));

		// size of 2
		db.clear();
		db.add(testNodes.get(0));
		db.add(testNodes.get(1));
		nodes = db.getAll();
		assertEquals(2, nodes.size());
		for (int i = 0; i < nodes.size(); i++) {
			assertEquals(testNodes.get(i), nodes.get(i));
		}

		// size of n
		db.clear();
		for (int i = 0; i < 10; i++) {
			Node nodei = new Node(i);
			db.add(nodei.constructSimplifiedNodeDomain());
		}
		nodes = db.getAll();
		assertEquals(10, nodes.size());
		for (int i = 0; i < nodes.size(); i++) {
			assertEquals(testNodes.get(i), nodes.get(i));
		}
	}

	/**
	 * Relation testing for getAll() method in HyPeerWebDatabase.java Tests add
	 * two SimplifiedNodeDomains that are almost equivalent, equals, and
	 * completely different
	 */
	@Test
	public void testRelational() {

		// test two nodes that are almost equal
		SimplifiedNodeDomain node0 = new SimplifiedNodeDomain(0, 0,
				new HashSet<Integer>(), new HashSet<Integer>(),
				new HashSet<Integer>(), 0, 1, 1, NodeState.HYPEERCUBECAP);
		SimplifiedNodeDomain nodeAlmost0 = new SimplifiedNodeDomain(1, 0,
				new HashSet<Integer>(), new HashSet<Integer>(),
				new HashSet<Integer>(), 0, 1, 1, NodeState.HYPEERCUBECAP);
		db.add(node0);
		db.add(nodeAlmost0);
		assertEquals(node0, db.getAll().get(0));
		assertEquals(nodeAlmost0, db.getAll().get(1));
		assertFalse(nodeAlmost0.equals(db.getAll().get(0)));

		// test two nodes that are equals
		db.clear();
		SimplifiedNodeDomain firstNode0 = new SimplifiedNodeDomain(0, 0,
				new HashSet<Integer>(), new HashSet<Integer>(),
				new HashSet<Integer>(), 0, 1, 1, NodeState.HYPEERCUBECAP);
		SimplifiedNodeDomain secondNode0 = new SimplifiedNodeDomain(0, 0,
				new HashSet<Integer>(), new HashSet<Integer>(),
				new HashSet<Integer>(), 0, 1, 1, NodeState.HYPEERCUBECAP);
		db.add(firstNode0);
		db.add(secondNode0);
		// should not be able to add two nodes with same webid to database

		assertEquals(node0, db.getAll().get(0));
		assertEquals(1, db.getAll().size());

		// test two nodes that are not equal at all
		db.clear();
		SimplifiedNodeDomain node1 = new SimplifiedNodeDomain(1, 2,
				new HashSet<Integer>(), new HashSet<Integer>(),
				new HashSet<Integer>(), 3, 4, 5, NodeState.HYPEERCUBECAP);
		SimplifiedNodeDomain node2 = new SimplifiedNodeDomain(6, 7,
				new HashSet<Integer>(), new HashSet<Integer>(),
				new HashSet<Integer>(), 8, 9, 10, NodeState.HYPEERCUBECAP);
		db.add(node1);
		db.add(node2);
		assertEquals(node1, db.getAll().get(0));
		assertEquals(node2, db.getAll().get(1));
		assertFalse(node1.equals(db.getAll().get(1)));
	}

	/**
	 * Internal Boundary Value testing for initHyPeerWebDatabase(String dbName)
	 * method in HyPeerWebDatabase.java Tests with null input, |dbName| = 1,
	 * |dbName| = 2, |dbName| = many (26)
	 */
	@Test
	public void testInternalBoundaryValue() {
		List<SimplifiedNodeDomain> testNodes = getTestNodes();
		// test with null name
		HyPeerWebDatabase.initHyPeerWebDatabase(null);
		db.clear();
		db.add(testNodes.get(0));
		List<SimplifiedNodeDomain> results = db.getAll();
		SimplifiedNodeDomain act = results.get(0);
		SimplifiedNodeDomain exp = testNodes.get(0);
		assertEquals(exp, act);

		// test with name of length 1
		HyPeerWebDatabase.initHyPeerWebDatabase("a");
		db.clear();
		db.add(testNodes.get(1));
		results = db.getAll();
		act = results.get(0);
		exp = testNodes.get(1);
		assertEquals(exp, act);

		// test with name of length n
		HyPeerWebDatabase.initHyPeerWebDatabase("abcdefghijklmnopqrstuvwxyz");
		db.clear();
		db.add(testNodes.get(2));
		results = db.getAll();
		act = results.get(0);
		exp = testNodes.get(2);
		assertEquals(exp, act);
	}

	/**
	 * Dataflow testing for save(List-SimplifiedNodeDomain- nodes) method in
	 * HyPeerWebDatabase.java The database could possibly change when this
	 * method is called
	 */
	@Test
	public void testDataflow() {

		// testing save with no nodes being saved
		List<SimplifiedNodeDomain> testNodes = getTestNodes();
		int before = db.getAll().size();
		db.save(new ArrayList<SimplifiedNodeDomain>());
		int after = db.getAll().size();
		assertEquals(db.getAll().size(), 0);
		assertEquals(before, after); // saving 0 nodes to an empty database
										// should not change it

		// testing save with some nodes being saved (these are the two possible
		// conditions)
		before = db.getAll().size();
		assertEquals(before, 0);
		db.save(testNodes);
		after = db.getAll().size();
		assertEquals(after, testNodes.size());
		assertFalse(after == before); // size here should change
	}

	private List<SimplifiedNodeDomain> getTestNodes() {
		List<SimplifiedNodeDomain> testNodes = new LinkedList<SimplifiedNodeDomain>();
		for (int i = 0; i < 10; i++) {
			testNodes.add(new Node(i).constructSimplifiedNodeDomain());
		}
		return testNodes;
	}

}
