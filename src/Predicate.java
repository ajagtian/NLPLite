import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 * Predicate represents an atomic type in FOL
 * A predicate notation [ P(x,y) ] is same as writing [ Vx,y P(x,y) => true ]
 */
public class Predicate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	must begin with a Upper Case letter
	private String identifier;
	//	a map of predicate parameters, strictly in the same order in which they appear in the FOL Knowledge, at most two
	private HashMap<String,String> params;
	//	the count of the total number of parameters
	int paramCount;
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public HashMap<String, String> getParams() {
		return params;
	}
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	public int getParamCount() {
		return paramCount;
	}
	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
	}
	@Override
	public String toString() {
		return "Predicate [identifier=" + identifier + ", params=" + params
				+ ", paramCount=" + paramCount + "]";
	}
}
