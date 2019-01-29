package com.crdavis.cisco.gnode.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.crdavis.cisco.gnode.GNode;
import com.crdavis.cisco.gnode.GNodeBuilder;
import com.crdavis.cisco.gnode.GNodeImpl;
import com.crdavis.cisco.gnode.GraphUtils;

/**
 * Unit Tests for Graph utilities
 * @author charlie.davis
 *
 */
public class TestGraphUtils {

	@Test
	public void constructorTest() {
		GNodeImpl node = new GNodeImpl("a");
		assertTrue(node.getName().equals("a"));
		assertTrue(node.getChildren().length == 0);
	}

	@Test
	public void childTest() {
		GNodeImpl node = new GNodeImpl("a");
		GNodeImpl child1 = new GNodeImpl("b");
		GNodeImpl child2 = new GNodeImpl("c");
		node.addChild(child1);
		node.addChild(child2);
		assertNotNull(node.getChildren());
		assertTrue(node.getChildren().length == 2);
		assertEquals(child1, node.getChildren()[0]);
		assertEquals(child2, node.getChildren()[1]);
	}
	
	@Test
	public void walkGraphEmpty1() {
		Set<GNode> nodes = GraphUtils.collectNodesInGraph(null);
		assertNotNull(nodes);
		assertTrue(nodes.isEmpty());
	}

	@Test
	public void walkGraphEmpty2() {
		ArrayList nodesAsArrayList = GraphUtils.walkGraph(null);
		assertNotNull(nodesAsArrayList);
		assertTrue(nodesAsArrayList.isEmpty());
	}

	@Test
	public void walkGraphSingle() {
		GNode anchor = new GNodeImpl("a");
		Set<GNode> nodes = GraphUtils.collectNodesInGraph(anchor);
		assertNotNull(nodes);
		assertTrue(nodes.size() == 1);
		assertTrue(nodes.contains(anchor));

		ArrayList nodesAsArrayList = GraphUtils.walkGraph(anchor);
		assertNotNull(nodesAsArrayList);
		assertTrue(nodesAsArrayList.size() == 1);
		assertTrue(nodesAsArrayList.get(0).equals(anchor));
	}
	
	private GNodeImpl buildSampleGraph(boolean displayFlag) {
		GNodeImpl anchor = new GNodeBuilder("a")
				.withChild("b")
				  .withChild("e").endChild()
				  .withChild("f").endChild()
				  .endChild()
				.withChild("c")
				  .withChild("g").endChild()
				  .withChild("h").endChild()
				  .withChild("i").endChild()
				  .endChild()
				.withChild("d")
				  .withChild("j").endChild()
				  .endChild()
				 .build();
		if (displayFlag)
			anchor.display();
		return anchor;
	}

	@Test
	public void walkGraphMultiple1() {
		String[] expectedNodeNames = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" };
		GNodeImpl anchor = buildSampleGraph(false);

		Set<GNode> nodes = GraphUtils.collectNodesInGraph(anchor);
		assertNotNull(nodes);
		assertTrue(nodes.size() == 10);
		Set<String> collectedNodeNames = nodes.stream().map(GNode::getName).collect(Collectors.toSet());
		for (String name : expectedNodeNames) {
			assertTrue(collectedNodeNames.contains(name));
		}
	}
	
	@Test
	public void walkGraphMultiple2() {
		String[] expectedNodeNames = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" };
		GNodeImpl anchor = buildSampleGraph(false);

		ArrayList<GNode> nodes = GraphUtils.walkGraph(anchor);
		assertNotNull(nodes);
		assertTrue(nodes.size() == 10);
		List<String> collectedNodeNames = nodes.stream().map(GNode::getName).collect(Collectors.toList());
		Collections.sort(collectedNodeNames);
		assertArrayEquals(expectedNodeNames, collectedNodeNames.toArray());
	}

	@Test
	public void walkGraphMultiple3() {
		String[] expectedNodeNames = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "x", "y", "z" };
		GNodeImpl nodeA = buildSampleGraph(false);
		GNodeImpl anchor = new GNodeBuilder("x")
				.withChild("y").endChild()
				.withChild("z").endChild()
				.build();
		GNodeImpl nodeX = (GNodeImpl) anchor.getChildren()[0];
		GNodeImpl nodeY = (GNodeImpl) anchor.getChildren()[1];
		GNode nodeB = nodeA.getChildren()[0];
		nodeX.addChild(nodeA);
		nodeY.addChild(nodeB);
		anchor.display();

		Set<GNode> nodes = GraphUtils.collectNodesInGraph(anchor);
		assertNotNull(nodes);
		assertTrue(nodes.size() == expectedNodeNames.length);
		Set<String> collectedNodeNames = nodes.stream().map(GNode::getName).collect(Collectors.toSet());
		for (String name : expectedNodeNames) {
			assertTrue(collectedNodeNames.contains(name));
		}
	}

	@Test
	public void pathTest0() {
		List<List<GNode>> paths = GraphUtils.findAllPaths(null);
		assertNotNull(paths);
		assertTrue(paths.isEmpty());
	}

	@Test
	public void pathTest1() {
		String[][] expectedPaths = {
				{ "a", "b", "e" },
				{ "a", "b", "f" },
				{ "a", "c", "g" },
				{ "a", "c", "h" },
				{ "a", "c", "i" },
				{ "a", "d", "j" }
		};
		GNodeImpl anchor = buildSampleGraph(false);
		List<List<GNode>> paths = GraphUtils.findAllPaths(anchor);
		assertNotNull(paths);
		assertTrue(paths.size() == 6);
		for (int i = 0; i < paths.size(); i++) {
			List<String> expectedPath = Arrays.asList(expectedPaths[i]);
			List<String> actualPath = paths.get(i).stream().map(GNode::getName).collect(Collectors.toList());
			assertEquals(expectedPath, actualPath);
		}
	}

	@Test
	public void pathTest2() {
		String[][] expectedPaths = {
				{ "a", "b", "e" },
				{ "a", "b", "f" },
				{ "a", "c", "g" },
				{ "a", "c", "h" },
				{ "a", "c", "i" },
				{ "a", "d", "j" }
		};
		GNodeImpl anchor = buildSampleGraph(false);
		ArrayList<List<GNode>> paths = GraphUtils.paths(anchor);
		assertNotNull(paths);
		assertTrue(paths.size() == 6);
		for (int i = 0; i < paths.size(); i++) {
			List<String> expectedPath = Arrays.asList(expectedPaths[i]);
			List<String> actualPath = paths.get(i).stream().map(GNode::getName).collect(Collectors.toList());
			assertEquals(expectedPath, actualPath);
		}
	}

	@Test
	public void pathTest3() {
		String[][] expectedPaths = {
				{ "x", "y", "a", "b", "e" },
				{ "x", "y", "a", "b", "f" },
				{ "x", "y", "a", "c", "g" },
				{ "x", "y", "a", "c", "h" },
				{ "x", "y", "a", "c", "i" },
				{ "x", "y", "a", "d", "j" },
				{ "x", "z", "b", "e" },
				{ "x", "z", "b", "f" }
		};
		GNodeImpl nodeA = buildSampleGraph(false);
		GNodeImpl anchor = new GNodeBuilder("x")
				.withChild("y").endChild()
				.withChild("z").endChild()
				.build();
		GNodeImpl nodeX = (GNodeImpl) anchor.getChildren()[0];
		GNodeImpl nodeY = (GNodeImpl) anchor.getChildren()[1];
		GNode nodeB = nodeA.getChildren()[0];
		nodeX.addChild(nodeA);
		nodeY.addChild(nodeB);

		List<List<GNode>> paths = GraphUtils.findAllPaths(anchor);
		assertNotNull(paths);
		assertTrue(paths.size() == expectedPaths.length);
		for (int i = 0; i < paths.size(); i++) {
			List<String> expectedPath = Arrays.asList(expectedPaths[i]);
			List<String> actualPath = paths.get(i).stream().map(GNode::getName).collect(Collectors.toList());
			assertEquals(expectedPath, actualPath);
		}

	}
	
}


