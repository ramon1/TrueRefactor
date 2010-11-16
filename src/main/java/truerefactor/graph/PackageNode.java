package truerefactor.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Isaac Griffith
 *
 */
public class PackageNode extends Node {

	/** */
	private String name;
	/** */
	private List<ClassNode> classes;
	
	/**
	 * 
	 * @param name
	 */
	public PackageNode(String name) {
		super();
		this.name = name;
		this.classes = new ArrayList<ClassNode>();
	}
	
	/**
	 * 
	 * @param node
	 */
	public void addClass(ClassNode node) {
		if (!classes.contains(node)) {
			classes.add(node);
		}
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((classes == null) ? 0 : classes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof PackageNode)) {
			return false;
		}
		PackageNode other = (PackageNode) obj;
		if (classes == null) {
			if (other.classes != null) {
				return false;
			}
		} else if (!classes.equals(other.classes)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see truerefactor.graph.Node#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PackageNode temp = (PackageNode)super.clone();
		
		temp.setName(name);
		
		for (ClassNode node : classes) {
			temp.addClass((ClassNode)node.clone());
		}
		
		return temp;
	}
}
