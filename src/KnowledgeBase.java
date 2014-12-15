import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 * 	The provided knowledge base of the system, which is
 * 		- a list of knowledge sentences, which is
 * 			- a group of premise and conclusion, and the variable field,which are
 * 				- premise -	a collection of predicates
 *				- conclusion - a predicate with a pointer to its representing knowledge sentence
 *					- predicate -	the predicate identifier and the predicate parameters(variables, constants)
 */				
public class KnowledgeBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<KnowledgeSentence> knowledgeSentences;

	public List<KnowledgeSentence> getKnowledgeSentences() {
		return knowledgeSentences;
	}

	public void setKnowledgeSentences(List<KnowledgeSentence> knowledgeSentences) {
		this.knowledgeSentences = knowledgeSentences;
	}
}
