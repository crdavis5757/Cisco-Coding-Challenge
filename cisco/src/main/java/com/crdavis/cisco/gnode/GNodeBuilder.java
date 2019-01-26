package com.crdavis.cisco.gnode;

import java.util.ArrayList;
import java.util.List;

/**
 * A fluent builder for GNodes allowing convenient construction of graphs.
 * @author charlie.davis
 *
 */
public class GNodeBuilder {

	private String nodeName;
	private List<GNodeBuilder> children;
	private GNodeBuilder parent;

	/**
	 * Constructs an anchor-level builder
	 * @param nodeName the anchor node's name
	 */
	public GNodeBuilder(String nodeName) {
		this(nodeName, null);
	}

	private GNodeBuilder(String childName, GNodeBuilder parent) {
		this.nodeName = childName;
		this.children = new ArrayList<>();
		this.parent = parent;
	}

	/**
	 * Start building a child sub-graph
	 * @param childName the child node's name
	 * @return the builder to use for building out the child sub-graph
	 */
	public GNodeBuilder withChild(String childName) {
		GNodeBuilder childBuilder = new GNodeBuilder(childName, this);
		children.add(childBuilder);
		return childBuilder;
	}

	/**
	 * Finish building the current child
	 * @return the builder to use for continuing to build out the parent
	 */
	public GNodeBuilder endChild() {
		return parent;
	}

	/**
	 * Constuct a graph from this anchor-level builder
	 * @return the anchor node of the graph
	 */
	public GNodeImpl build() {
		GNodeImpl node = new GNodeImpl(nodeName);
		for (GNodeBuilder childBuilder : children) {
			node.addChild(childBuilder.build());
		}
		return node;
	}

}
