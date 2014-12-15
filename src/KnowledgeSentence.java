import java.io.Serializable;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 *	A knowledge sentence is the single [ premise=>conclusion ] pair from the KnowledgeBase. 
 */
public class KnowledgeSentence implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	the set of clauses which form the premise on the knowledge sentence  
	private Premise premise;
	//	the single clause conclusion of the premise predicates
	private Conclusion conclusion;
	//	the single variable of the KowledgeSentence
	private String varVALUE;
	
	public Premise getPremise() {
		return premise;
	}
	public void setPremise(Premise premise) {
		this.premise = premise;
	}
	public Conclusion getConclusion() {
		return conclusion;
	}
	public void setConclusion(Conclusion conclusion) {
		this.conclusion = conclusion;
	}
	public String getVarVALUE() {
		return varVALUE;
	}
	public void setVarVALUE(String varVALUE) {
		this.varVALUE = varVALUE;
	}
	@Override
	public String toString() {
		return "KnowledgeSentence [premise=" + premise + ", conclusion="
				+ conclusion + ", varVALUE=" + varVALUE + "]";
	}
}
