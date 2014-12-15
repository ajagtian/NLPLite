import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 *	A conjunction of clauses - Horn's Clauses - can be at most 10
 */
public class Premise implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	a list of conjuncted disjunctions form a premise.  
	List<Predicate> predicates;

	public List<Predicate> getPredicates() {
		return predicates;
	}
	public void setPredicates(List<Predicate> predicates) {
		this.predicates = predicates;
	}
	@Override
	public String toString() {
		return "Premise [predicates=" + predicates + "]";
	}
}
