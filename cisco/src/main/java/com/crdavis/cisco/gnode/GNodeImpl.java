package com.crdavis.cisco.gnode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple concrete implementation of the GNode interface
 * @author charlie.davis
 *
 */
public class GNodeImpl implements GNode {
	
	private String name;
	private List<GNode> children;
	
	/**
	 * Construct a new node
	 * @param name the new node's name
	 */
	public GNodeImpl(String name) {
		this.name = name;
		this.children = new ArrayList<>();
	}
	
	/**
	 * Add a child to this node
	 * @param child the child to add
	 */
	public void addChild(GNode child) {
		children.add(child);
	}

	/**
	 * Remove a child from this node (for completeness; not used in challenge)
	 * @param child the child to remove
	 */
	public void removeChild(GNode child) {
		children.remove(child);
	}

	/**
	 * Print the graph anchored at this node (for debug)
	 * @param ps the output print stream
	 */
	public void display() {
		display(0);
	}

	private void display(int level) {
		for (int i = 0; i < level; i++)
			System.out.print("  ");
		System.out.println(name);
		for (GNode child : children)
			((GNodeImpl) child).display(level + 1);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public GNode[] getChildren() {
		return children.toArray(new GNode[children.size()]);
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		if ((other == null) || !(other instanceof GNodeImpl))
			return false;

		// TODO: We consider the node's name to be it's unique identity
		// That suffices for this code challenge, but would need to be revisited in real production code
		GNodeImpl that = (GNodeImpl) other;
		return (this == that) || this.name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

}
