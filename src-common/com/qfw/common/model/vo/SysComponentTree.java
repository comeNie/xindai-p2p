package com.qfw.common.model.vo;

import java.util.ArrayList;
import java.util.List;

public class SysComponentTree {
	private SysComponentTree leftNode;
	private SysComponentTree rightNode;
	private SysComponent value;
    private List<SysComponentTree> children;	
	private SysComponentTree parent;
	
	
	public SysComponentTree() {
		children = new ArrayList<SysComponentTree>();
	}

	public int getChildCount(){
		if(isLeaf()) return 0;
		return children.size();
	}
	
	public boolean isLeaf(){
		return children == null || children.size() == 0;
	}
	public void addChild(SysComponentTree child){
		if(children != null){
			children.add(child);
		}
	}
	public SysComponentTree getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(SysComponentTree leftNode) {
		this.leftNode = leftNode;
	}
	public SysComponentTree getRightNode() {
		return rightNode;
	}
	public void setRightNode(SysComponentTree rightNode) {
		this.rightNode = rightNode;
	}
	public SysComponent getValue() {
		return value;
	}
	public void setValue(SysComponent value) {
		this.value = value;
	}
	public List<SysComponentTree> getChildren() {
		return children;
	}
	public void setChildren(List<SysComponentTree> children) {
		this.children = children;
	}
	public SysComponentTree getParent() {
		return parent;
	}
	public void setParent(SysComponentTree parent) {
		if(this.parent != null) {
            this.parent.getChildren().remove(this);
        }
        
        this.parent = parent;
        
        if(this.parent != null) {
            this.parent.getChildren().add(this);
        }
	}
	
	

}
