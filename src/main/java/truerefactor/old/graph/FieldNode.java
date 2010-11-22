/**
 * 
 */
package truerefactor.old.graph;

/**
 * @author Isaac
 */
public class FieldNode extends Node {

    /** */
    private String name;
    /** */
    private ClassNode parentClass;

    /**
     * @param name
     */
    public FieldNode(String name) {
	super();
	this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the parentClass
     */
    public ClassNode getParentClass() {
	return parentClass;
    }

    /**
     * @param parentClass
     *            the parentClass to set
     */
    public void setParentClass(ClassNode parentClass) {
	this.parentClass = parentClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	FieldNode temp = (FieldNode) super.clone();
	temp.setName(String.copyValueOf(name.toCharArray()));

	return temp;
    }

    public String getCode()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
