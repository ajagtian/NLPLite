import java.util.HashMap;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 * Inference drawing and matching through backward chaining
 */
public class InferenceRoutine {
	
	/**
	 * 
	 * @param Q
	 * @param KB
	 * @return true Query can be inferred from KnowledgeBase
	 */
	public boolean infer(Query Q, KnowledgeBase KB){
		boolean inferable = false;
		String varVALUE = "";
		// query predicate
		Predicate queryPredicate = Q.getPredicate();
		for(KnowledgeSentence KS : KB.getKnowledgeSentences()) {
			//	conclusion predicate
			Predicate interrogatedPredicate = KS.getConclusion().getPredicate();
			varVALUE = doMatch(interrogatedPredicate, queryPredicate);
			if(varVALUE == null || varVALUE == "var"){
				// no match
				continue;
			}
			else if(isEmpty(varVALUE)){
				// perfect match found, no variable present
				if(KS.getPremise() == null){
					inferable =  true;
				}
				else{
					inferable = true;
					for(Predicate predicate : KS.getPremise().getPredicates()) {
						Query q = new Query();
						q.setPredicate(predicate);;
						inferable = inferable & infer(q, KB);
					}
				}
			}
			else{
				// a match in the conclusion found, draw inference on premise
				Premise premise = KS.getPremise();
				if(premise == null){
					System.out.println("x = "+varVALUE);
					return true;
				}
				else {
					// plug the value in KS
					KS.setVarVALUE(varVALUE);
					inferable= true;
					for(Predicate predicate: premise.getPredicates()){
						Predicate unifiedPredicate = plugValue(predicate, KS.getVarVALUE());
						// form a query with this predicate
						Query q = new Query();
						q.setPredicate(unifiedPredicate);
						inferable = inferable & infer(q, KB);
					}
					
				}
			}
		}
		return inferable;
	}
	
	
	
	/**
	 * Matched two predicates 
	 * @param p1
	 * @param p2
	 * @return the value of variable is a match is found, null if not matching and empty if constant.
	 */
	private String doMatch(Predicate interrogatedPredicate, Predicate queryPredicate){

		String varVALUE = null;
		//	check 1: identifiers match
		if(interrogatedPredicate.getIdentifier().compareTo(queryPredicate.getIdentifier()) == 0){
			//	Check 2: params are pluggale
			//	check 2.1: params are equal in count
			if(interrogatedPredicate.getParamCount() == queryPredicate.getParamCount()){
				HashMap<String,String> cParams = interrogatedPredicate.getParams();
				HashMap<String, String> qParams = queryPredicate.getParams();
				//	case1: one param
				if(interrogatedPredicate.getParamCount() == 1){
					//	constant
					if(cParams.get("con-1") != null){
						if(qParams.get("con-1") != null){
							if(cParams.get("con-1").compareTo(qParams.get("con-1"))  == 0){
								varVALUE = new String();
							}
							else {
								// no change
							}
						}
						else{
							varVALUE = cParams.get("con-1");
						}
					}
					//	variable
					else if(cParams.get("var-1") != null){
						if(qParams.get("con-1") != null){
							varVALUE = qParams.get("con-1");
						}
						else{
							varVALUE = "var";
						}
					}
				}
				else if(interrogatedPredicate.getParamCount() == 2){
					for(int i = 0 ; i < interrogatedPredicate.getParamCount() ; i++) {
						if (interrogatedPredicate.getParams().get(
								"con-" + String.valueOf(i + 1)) != null){
							if(queryPredicate.getParams().get("con-" + String.valueOf(i + 1)) != null){
								if(queryPredicate.getParams().get(
										"con-" + String.valueOf(i + 1)).compareTo(interrogatedPredicate
										.getParams().get(
												"con-" + String.valueOf(i + 1))) == 0) {
									if(varVALUE == null){
										varVALUE =  new String();
									}
								}
								else{
									// invalid
									varVALUE = null;
									break;
								}
							}
							else {
								if(varVALUE != null && !isEmpty(varVALUE)){
									if(varVALUE.compareTo(interrogatedPredicate.getParams().get("con-" + String.valueOf(i + 1)))==0){
									}
									else{
										varVALUE = null;
									}
								}
								else{
									varVALUE = interrogatedPredicate.getParams().get("con-" + String.valueOf(i + 1));
								}
							}
						}
						else if(interrogatedPredicate.getParams().get("var-" + String.valueOf(i + 1)) != null){
							if(varVALUE == null || isEmpty(varVALUE)){
								if(queryPredicate.getParams().get("con-" + String.valueOf(i + 1)) != null){
									varVALUE = queryPredicate.getParams().get("con-" + String.valueOf(i + 1));
								}
								else{
									varVALUE = "var";
								}
							}
							else {
								if (queryPredicate.getParams().get(
										"con-" + String.valueOf(i + 1)) != null
										&& (queryPredicate
												.getParams()
												.get("con-"
														+ String.valueOf(i + 1))
												.compareTo(varVALUE) == 0)
										&& (interrogatedPredicate
												.getParams()
												.get("var-"
														+ String.valueOf(i + 1))
												.compareTo(interrogatedPredicate
														.getParams()
														.get("var-"
																+ String.valueOf(i)))) == 0) {
									// valid - do nothing - varVALUE is already
									// assigned

								}
								else {
									// invalid query
									varVALUE = null;
									break;
								}

							}
						}
					}
					
				}
			}
		}
		return varVALUE;			// might be null, empty or something.
	}
	
//	private boolean hasVariable(Predicate p) {
//		boolean contains = false;
//		for(String type : p.getParams().keySet()){s
//			if(type.contains("var")){
//				contains = true;
//				break;
//			}
//		}
//		return contains;
//	}
	
	private Predicate plugValue(Predicate predicate, String value){
		HashMap<String, String> unifiedParams = new HashMap<String,String>();
		Predicate unifiedPredicate = new Predicate();
		for(int i = 0 ; i < predicate.getParamCount() ; i++) {
			if(predicate.getParams().get("var-"+String.valueOf(i+1)) != null){
				// variable found
				unifiedParams.put("con-"+String.valueOf(i+1), value);
			}
			else{
				unifiedParams.put("con-"+String.valueOf(i+1), predicate.getParams().get("con-"+String.valueOf(i+1)));
			}
		}
		unifiedPredicate.setIdentifier(predicate.getIdentifier());
		unifiedPredicate.setParamCount(predicate.getParamCount());
		unifiedPredicate.setParams(unifiedParams);
		return unifiedPredicate;
	}
	
	
	private boolean isEmpty(String str){
		if(str.length() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
}
