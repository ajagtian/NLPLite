import java.io.Serializable;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 *	The query, for which inference has to be drawn, consists of a single predicate. Or does it?
 */
public class Query implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	single predicate to be inferred
	private Predicate predicate;

	public Predicate getPredicate() {
		return predicate;
	}
	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}
	@Override
	public String toString() {
		return "Query [predicate=" + predicate + "]";
	}
}
