package com.crdavis.cisco.gnode;

/**
 * Graph node inteface, as specified by coding challenge
 * @author charlie.davis
 *
 */
public interface GNode {
	
	/**
	 * Get this node's name
	 * @return this node's name
	 */
	public String getName();
	
	/**
	 * Get this node's children
	 * @return this node's children.  If this node has no children, returns an empty array.
	 */
	public GNode[] getChildren();

}
