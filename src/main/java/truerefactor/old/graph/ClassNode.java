/**
 * 
 */
package truerefactor.old.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaac
 */
public class ClassNode extends Node {

    /** */
    private String className;
    /** */
    private List<MethodNode> methods;
    /** */
    private List<FieldNode> fields;
    /** */
    private PackageNode pkg;

    /**
     * @param className
     */
    public ClassNode(String className) {
	super();
	this.className = className;
	methods = new ArrayList<MethodNode>();
	fields = new ArrayList<FieldNode>();
    }

    /**
     * @return
     */
    public String getName() {
	return className;
    }

    /**
     * @param name
     */
    protected void setName(String name) {
	this.className = name;
    }

    /**
     * @param field
     */
    public void addField(FieldNode field) {
	if (!fields.contains(field)) {
	    fields.add(field);
	}
    }

    /**
     * @param method
     */
    public void addMethod(MethodNode method) {
	if (!methods.contains(method)) {
	    methods.add(method);
	}
    }

    /**
     * @return the methods
     */
    public List<MethodNode> getMethods() {
	return methods;
    }

    /**
     * @param methods
     *            the methods to set
     */
    public void setMethods(List<MethodNode> methods) {
	this.methods = methods;
    }

    /**
     * @return the fields
     */
    public List<FieldNode> getFields() {
	return fields;
    }

    /**
     * @param fields
     *            the fields to set
     */
    public void setFields(List<FieldNode> fields) {
	this.fields = fields;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
	ClassNode cNode = (ClassNode) super.clone();

	for (MethodNode mNode : methods) {
	    MethodNode temp = (MethodNode) mNode.clone();
	    cNode.addMethod(temp);
	}

	for (FieldNode fNode : fields) {
	    FieldNode temp = (FieldNode) fNode.clone();
	    cNode.addField(temp);
	}

	cNode.setName(this.getName());

	return cNode;
    }

    /**
     * @param pkg
     */
    public void setPackage(PackageNode pkg) {
	this.pkg = pkg;
    }

    /**
     * @return
     */
    public PackageNode getPackage() {
	return pkg;
    }
}
