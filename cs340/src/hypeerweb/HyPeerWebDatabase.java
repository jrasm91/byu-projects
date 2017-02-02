package hypeerweb;

import gui.Main.GUI;
import hypeerweb.node.NodeState;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import distributed.*;

/**
 * Tool to persist the HyPeerWeb to an SQLite Database.
 * 
 * + Available as a Singleton + Helper for the HyPeerWeb
 * 
 * <pre>
 * <b>Domain:</b>
 * 		singleton : HyPeerWebDatabase
 * 		CREATE_FILE : String
 * 		DEFAULT_DB_NAME : String
 * 		DATABASE_DIRECTORY : String
 * 		lastLoaded : String
 * 		con : Connection
 * 
 * <b>Invariant:</b>
 * 		Connection is always closed after each method that uses the Connection.
 * 
 * </pre>
 * 
 * @author Jason Rasmussen, Adam Christiansen
 */
public class HyPeerWebDatabase {

	private static HyPeerWebDatabase singleton;
	private static final String CREATE_FILE = "database" + File.separator
			+ "table_statements.sql";

	private static final String DEFAULT_DB_NAME = "hyper.db";
	private static final String DATABASE_DIRECTORY = "database";
	private static String lastLoaded = "";

	private static Connection con;

	static {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the single HyPeerWebDatabase.
	 * 
	 * @return Returns the singleton HyPeerWebDatabase.
	 * @pre initHyPeerWebDatabase() must have been called previously. This
	 *      ensure the singleton does not equal null.
	 * @post result = singleton
	 */
	public static HyPeerWebDatabase getSingleton() {
		if (singleton == null) {
			singleton = new HyPeerWebDatabase();
		}
		return singleton;
	}

	/**
	 * Creates and loads a HyPeerWebDatabase. Should be one of the first things
	 * called when creating a HyPeerWeb.
	 * 
	 * @param dbName
	 *            - The name of the database.
	 * @pre dbName = null OR |dbName| = 0 OR There must exist a database with
	 *      the given dbName.
	 * @post IF dbName = null OR |dbName| = 0 new
	 *       HyPeerWebDatabase(DEFAULT_DATABASE_NAME).postCondition ELSE new
	 *       HyPeerWebDatabase(dbName).postCondition
	 */
	public static void initHyPeerWebDatabase(final String dbName) {
		String databaseName = dbName;
		if (dbName == null || dbName.length() == 0) {
			databaseName = DEFAULT_DB_NAME;
		}
		con = null;
		try {
			lastLoaded = "jdbc:sqlite:" + DATABASE_DIRECTORY + File.separator
					+ databaseName;
			con = DriverManager.getConnection(lastLoaded);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Connection getConnection() {
		try {
			if (con.isClosed()) {
				con = DriverManager.getConnection(lastLoaded);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Initializes the HyPeerWebDatabase from default database name
	 * 
	 * @pre none
	 * @post initHyPeerWebDatabase(null).postCondition
	 */
	public static void initHyPeerWebDatabase() {
		initHyPeerWebDatabase(null);
	}

	/**
	 * Clears the current database.
	 * 
	 * @pre None.
	 * @post Database contains tables with no elements.
	 */
	public void clear() {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(CREATE_FILE));
			scan.useDelimiter(";");
			while (scan.hasNext()) {
				Statement stmt = getConnection().createStatement();
				String temp = scan.next();
				if (temp.trim().length() != 0) {
					stmt.execute(temp);
				}
				stmt.close();
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private Map<Integer, GlobalObjectId> getGIds() {
		Map<Integer, GlobalObjectId> gids = new HashMap<Integer, GlobalObjectId>();
		try {
			String query = "SELECT * FROM remote_nodes";
			PreparedStatement stmt;
			ResultSet rs;
			stmt = getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PortNumber portNumber = new PortNumber(rs.getInt(4));
				LocalObjectId localObjectId = new LocalObjectId(rs.getInt(2));
				String machineName = rs.getString(2);
				GlobalObjectId g = new GlobalObjectId(machineName, portNumber, localObjectId);
				gids.put(rs.getInt(1), g);
			}
		} catch (SQLException e) {
			if(e.getMessage().contains("no such table")) {
				return gids;
			}
			e.printStackTrace();
		}
		return gids;
	}
	/**
	 * Helper for HyPeerWeb to load Nodes and eventually instantiate nodes.
	 * 
	 * @return A list of nodes representing the current nodes in the database.
	 * @pre None.
	 * @post |result| = |node_table| (in database) AND every node in result is
	 *       in the database once and only once
	 */
	public List<SimplifiedNodeDomain> getAll() {
//		Map<Integer, GlobalObjectId> gids = getGIds();
		LinkedList<SimplifiedNodeDomain> nodes = new LinkedList<SimplifiedNodeDomain>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM node";
			stmt = getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {

				int webid = rs.getInt(1);
				int fold = rs.getInt(2);
				if (rs.wasNull()) {
					fold = -1;
				}
				int s_fold = rs.getInt(3);
				if (rs.wasNull()) {
					s_fold = -1;
				}
				int inverse_s_fold = rs.getInt(4);
				if (rs.wasNull()) {
					inverse_s_fold = -1;
				}
				int height = rs.getInt(5);
				NodeState state = NodeState.valueOf(rs.getString(6));
				LocalObjectId lid = new LocalObjectId(rs.getInt(7));

				HashSet<Integer> neighbors = getNeighbors(webid, "neighbors");
				HashSet<Integer> s_neighbors = getNeighbors(webid,
						"s_neighbors");
				HashSet<Integer> inverse_s_neighbors = getNeighbors(webid,
						"inverse_s_neighbors");
				SimplifiedNodeDomain node = new SimplifiedNodeDomain(webid,
						height, neighbors, inverse_s_neighbors, s_neighbors,
						fold, s_fold, inverse_s_fold, state);
				node.setLocalId(lid);
//				node.setGlobalIds(gids);
				nodes.add(node);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// This database has not been initialized
			return new LinkedList<SimplifiedNodeDomain>();
		}
		return nodes;
	}

	/**
	 * Saves the database.
	 * 
	 * @param nodes
	 *            Nodes to base the stored database on.
	 * @pre nodes is not null, but nodes may be empty.
	 * @post |nodes_table| (in database) = |nodes| AND every node in nodes is in
	 *       the database once and only once
	 */
	public void save(List<SimplifiedNodeDomain> nodes) {
		clear();
		Map<Integer, GlobalObjectId> gids = new HashMap<Integer, GlobalObjectId>();
		for (SimplifiedNodeDomain node : nodes) {
			if(node.getGIds() == null) {
				continue;
			}
			gids.putAll(node.getGIds());
		}
		saveGlobalIds(gids);
		for (SimplifiedNodeDomain node : nodes) {
			add(node);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	private void saveGlobalIds(Map<Integer, GlobalObjectId> gids) {
		String insert = "INSERT INTO remote_nodes (node_webid, local_id, remote_server, remote_port)"
				+ " values (?,?,?,?)";
		PreparedStatement stmt = null;
		try {
			for (Entry<Integer, GlobalObjectId> gid : gids.entrySet()) {
				stmt = getConnection().prepareStatement(insert);
				stmt.setInt(1, gid.getKey());
				stmt.setInt(2, gid.getValue().getLocalObjectId().getId());
				stmt.setString(3, gid.getValue().getMachineAddr());
				stmt.setInt(4, gid.getValue().getPortNumber().getValue());
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Adds a specific node to the stored database.
	 * 
	 * @param node
	 *            Node to be added to the database.
	 * @pre node is not null.
	 * @post Node is in node_table' AND every element in node_table' is in
	 *       node_table AND |node_table'| = |node_table| + 1
	 */
	public void add(SimplifiedNodeDomain node) {
		insertToNode(node.getWebId(), node.getFold(), node.getSurrogateFold(),
				node.getInverseSurrogateFold(), node.getHeight(),
				node.getState(), node.getLocalId().getId());
		insertToNeighbors(node.getWebId(), node.getNeighbors(), "neighbor");
		insertToNeighbors(node.getWebId(), node.getDownPointers(), "s_neighbor");
		insertToNeighbors(node.getWebId(), node.getUpPointers(),
				"inverse_s_neighbor");
	}

	/**
	 * Abstraction to store and persist node attributes.
	 * 
	 * @param webid
	 *            Web Id of a node.
	 * @param fold
	 *            The fold of a node.
	 * @param sfold
	 *            The surrogate fold of a node.
	 * @param height
	 *            The height of the node.
	 * @param nodeState
	 * 
	 */
	private void insertToNode(int webid, int fold, int sfold,
			int inverse_s_fold, int height, NodeState state, int localId) {
		try {
			String insert = "INSERT INTO node (webid, fold, s_fold, inverse_s_fold, height, state, local_id)"
					+ " values (?,?,?,?,?,?,?)";
			PreparedStatement stmt = null;
			stmt = getConnection().prepareStatement(insert);
			stmt.setInt(1, webid);
			stmt.setInt(2, fold);
			stmt.setInt(3, sfold);
			stmt.setInt(4, inverse_s_fold);
			stmt.setInt(5, height);
			stmt.setString(6, state.toString());
			stmt.setInt(7, localId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds neighbors for a node.
	 * 
	 * @param webid
	 *            Web Id of the node to link neighbors to.
	 * @param neighbors
	 *            Set of Neighbors to add by Id.
	 * @param table
	 *            Table name to use, selected for surrogate neighbors for
	 *            example.
	 */
	private void insertToNeighbors(int webid, HashSet<Integer> neighbors,
			String table) {
		for (Integer i : neighbors) {
			try {
				String insert = "INSERT INTO " + table + "s (node_webid, "
						+ table + "_webid)" + " values (?,?)";

				PreparedStatement stmt = null;
				stmt = getConnection().prepareStatement(insert);
				stmt.setInt(1, webid);
				stmt.setInt(2, i);

				stmt.executeUpdate();
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets a set of neighbors for a node by a Web Id and table name.
	 * 
	 * @param webid
	 *            Node Web Id to search from.
	 * @param table
	 *            Table name, such as surrogate neighbors.
	 * @return Set of integers representing neighbors.
	 * @throws SQLException
	 * @pre the table name is in the database.
	 * @post No side effects.
	 */

	private HashSet<Integer> getNeighbors(int webid, String table)
			throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashSet<Integer> neighbors = new HashSet<Integer>();
		String query = "SELECT *" + "FROM " + table + " WHERE node_webid = ?";
		stmt = getConnection().prepareStatement(query);
		stmt.setInt(1, webid);
		rs = stmt.executeQuery();
		while (rs.next()) {
			neighbors.add(rs.getInt(2));
		}
		stmt.close();
		rs.close();
		return neighbors;
	}
}
