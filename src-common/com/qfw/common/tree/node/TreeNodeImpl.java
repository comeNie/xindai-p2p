package com.qfw.common.tree.node;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.google.common.collect.Iterators;

public class TreeNodeImpl extends NamedNode implements TreeNode{

	public TreeNodeImpl(){
		
	}
	public TreeNodeImpl(String name,String code) {
		setName(name);
		setCode(code);
	}

	private List<TreeNode> nodes = new ArrayList<TreeNode>();
	@Override
	public TreeNode getChildAt(int childIndex) {
		return nodes.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return nodes.size();
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex(javax.swing.tree.TreeNode node) {
		return nodes.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return nodes.isEmpty();
	}

	@Override
	public Enumeration children() {
		return Iterators.asEnumeration(nodes.iterator());
	}
	
	public void addChildren(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public void addChild(TreeNode treeNode){
		this.nodes.add(treeNode);
	}
	public void removeChild(TreeNode treeNode){
		if(!isLeaf()){
			this.nodes.remove(treeNode);
		}
	}
	public List<TreeNode> getChildren(){
		return nodes;
	}


}
