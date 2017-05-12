package com.qfw.common.tree.node;

import java.util.List;

public interface TreeNode extends javax.swing.tree.TreeNode {	
	public void addChildren(List<TreeNode> nodes);	
	public void addChild(TreeNode treeNode);
	public void removeChild(TreeNode treeNode);
	public List<TreeNode> getChildren();
}
