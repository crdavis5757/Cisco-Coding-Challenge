package com.crdavis.cisco.gnode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Class implementing graph utilities as specified in coding challenge.
 * @author charlie.davis
 *
 */
public class GraphUtils {

	// Control debug output
	private static final boolean debug = false;

	/**
	 * Walk an acyclic graph and collect all the nodes in the graph.  This method is a wrapper for collectNodesInGraph, and
	 * exists to exactly match the signature specified in challenge part 1.
	 * @param anchorNode the anchor node of the graph
	 * @return an ArrayList of the nodes, possibly empty
	 */
	public static ArrayList walkGraph(GNode anchorNode) {   // Use of raw type required by challenge specification
		return new ArrayList<>(collectNodesInGraph(anchorNode));
	}
	
	/**
	 * Walk an acyclic graph and collect all the nodes in the graph.
	 * @param anchorNode the anchor node of the graph
	 * @return the set of nodes in the graph, possibly empty.
	 */
	public static Set<GNode> collectNodesInGraph(GNode anchorNode) {
		
		Set<GNode> allNodes = new HashSet<>();
		if (anchorNode == null)
			return allNodes;	// Null graph, return empty set
		
		List<GNode> nodesToConsider = new ArrayList<>();
		nodesToConsider.add(anchorNode);

		while (!nodesToConsider.isEmpty()) {
			ListIterator<GNode> it = nodesToConsider.listIterator();
			GNode nextNode = it.next();
			it.remove();
			allNodes.add(nextNode);
			for (GNode child : nextNode.getChildren()) {
				it.add(child);
			}
		}

		return allNodes;
	}

	/**
	 * Walk a graph and return a list of all paths from the anchor to any leaf node.
	 * This method is a wrapper for findAllPaths; it corresponds exactly to the signature specified in challenge part 2.
	 * @param anchorNode the graph's anchor node
	 * @return an ArrayList of paths, each path being a list of GNode's comprising the path
	 */
	public static ArrayList paths(GNode anchorNode) {
		return new ArrayList<>(findAllPaths(anchorNode));
	}

	/**
	 * Walk a graph and return a list of all paths from the anchor node to any leaf node.
	 * @param anchorNode the graph's anchor node
	 * @return a list of paths, each path being a list of the GNode's comprising the path
	 */
	public static List<List<GNode>> findAllPaths(GNode anchorNode) {
		List<List<GNode>> allPaths = new ArrayList<>();
		if (anchorNode == null)
			return allPaths;

		findAllPaths(allPaths, new LinkedList<GNode>(), anchorNode);
		return allPaths;
	}

	/**
	 * Recursive node visitor used in finding paths.
	 * @param allPaths this list of complete paths found so far
	 * @param currentPath the path currently being considered in the recursive walk
	 * @param currentNode the next node to consider in the recursive walk
	 */
	private static void findAllPaths(List<List<GNode>> allPaths, Deque<GNode> currentPath, GNode currentNode) {
		currentPath.addLast(currentNode);

		GNode[] childrenToVisit = currentNode.getChildren();
		if (childrenToVisit.length == 0) {
			// We are on a leaf node and have a complete path. Record it.
			if (debug)
				displayPath(currentPath);
			allPaths.add(new ArrayList<>(currentPath));
		}
		else {
			// Non-leaf node, continue recursive walk
			for (GNode child : childrenToVisit) {
				findAllPaths(allPaths, currentPath, child);
			}
		}

		currentPath.removeLast();
		return;
	}

	// Debug support
	private static void displayPath(Deque<GNode> path) {
		System.out.print("Found path:");
		for (GNode node : path)
			System.out.print(" " + node.getName());
		System.out.println();
	}

}
