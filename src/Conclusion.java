import java.io.Serializable;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 *	conclusion on a set of premises - contains single predicate 
 */
public class Conclusion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	single predicate
	private Predicate predicate;
	//	pointer to its parent knowledge sentence
	private KnowledgeSentence ks;
	
	public KnowledgeSentence getKs() {
		return ks;
	}
	public void setKs(KnowledgeSentence ks) {
		this.ks = ks;
	}
	public Predicate getPredicate() {
		return predicate;
	}
	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}
	@Override
	public String toString() {
		return "Conclusion [predicate=" + predicate + ", ks=" + ks + "]";
	}
}
