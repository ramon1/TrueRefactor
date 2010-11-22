package truerefactor.old.util;

/**
 * Pair<X,Y> - 
 * 
 * @author Isaac Griffith
 *
 * @param <X>
 * @param <Y>
 */
public class Pair<X, Y> {
	/**
	 * 
	 */
	private X first;
	/**
	 * 
	 */
	private Y second;
	
	/**
	 * @param first
	 * @param second
	 */
	public Pair(X first, Y second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * @return
	 */
	public X getFirst() {
		return first;
	}

	/**
	 * @param first
	 */
	public void setFirst(X first) {
		this.first = first;
	}

	/**
	 * @return
	 */
	public Y getSecond() {
		return second;
	}

	/**
	 * @param second
	 */
	public void setSecond(Y second) {
		this.second = second;
	}
}
