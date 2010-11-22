/**
 * 
 */
package truerefactor.old.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaac
 */
public class MethodNode extends Node {

    private StatementNode start;
    private List<String> params;
    private String name;
    private ClassNode parentClass;

    public MethodNode(String name, List<String> params) {
	super();
	this.name = name;
	this.params = params;
    }

    /**
     * @return the start
     */
    public StatementNode getStart() {
	return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(StatementNode start) {
	this.start = start;
    }

    /**
     * @return the params
     */
    public List<String> getParams() {
	return params;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(List<String> params) {
	this.params = params;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	MethodNode temp = (MethodNode) super.clone();
	temp.setName(String.copyValueOf(name.toCharArray()));
	
	List<String> newParams = new ArrayList<String>();
	for (String param : params) {
	    newParams.add(String.copyValueOf(param.toCharArray()));
	}
	temp.setParams(newParams);
	temp.setStart((StatementNode) start.clone());

	return temp;
    }

    /**
     * @param parentClass
     */
    public void setParentClass(ClassNode parentClass) {
	this.parentClass = parentClass;
    }

    /**
     * @return
     */
    public ClassNode getParentClass() {
	return parentClass;
    }

    public String getCode()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
