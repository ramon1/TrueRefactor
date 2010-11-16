/**
 * 
 */
package truerefactor.graph;

/**
 * 
 * @author Isaac
 *
 */
public class StatementNode extends Node {

	private String statement;
	private MethodNode method;
	
	/**
	 * 
	 * @param statement
	 */
	public StatementNode(String statement) {
		super();
		this.statement = statement;
	}
	
	/**
	 * @return the statement
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * @param statement the statement to set
	 */
	public void setStatement(String statement) {
		this.statement = statement;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		StatementNode temp = (StatementNode)super.clone();
		temp.setStatement(String.copyValueOf(statement.toCharArray()));
		
		return temp;
	}
	
	/**
	 * 
	 * @param method
	 */
	public void setMethod(MethodNode method) {
		this.method = method;
	}
	
	/**
	 * 
	 * @return
	 */
	public MethodNode getMethod() {
		return method;
	}
}
