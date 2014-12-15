import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 *	Populates the knowledge base provided through files 
 */
public class KnowledgePopulationRoutine {
	
	
	
	private FileIOUtil fileIOUtil = new FileIOUtil();
	/*
	 * Parse the predicate text into predicate 
	 */
	public Predicate parsePredicate(String text){
		Predicate predicate = new Predicate();
		//	store the two params of this predicate, in order.
		HashMap<String, String> params = new HashMap<String, String>();
		//	the parameter count
		int paramCount = 0;
		int openPara = text.indexOf("(");
		//	1	->	the identifier of the Predicate id its name
		predicate.setIdentifier(text.substring(0,openPara));
		String[] fields	 = text.substring(openPara+1,text.length()-1).split(",");
		//	2	->	the parameters of the predicate
		for(String field : fields) {
			if(field.compareTo("x") == 0) {
				//	it is a variable
				++paramCount;
				params.put("var-"+String.valueOf(paramCount), field);
			}
			else if(Character.isUpperCase(field.charAt(0))){
				//	a constant
				++paramCount;
				params.put("con-"+String.valueOf(paramCount), field);
			}
		}
		//	3	->	the parameter count of the predicate
		predicate.setParamCount(paramCount);
		predicate.setParams(params);
		return predicate;
	}

	public Premise parsePremise(List<Predicate> predicates){
		Premise premise = new Premise();
		premise.setPredicates(predicates);
		return premise;
	}
	
	public Conclusion parseConclusion(Predicate predicate){
		Conclusion conclusion = new Conclusion();
		conclusion.setPredicate(predicate);
		return conclusion;
	}
	/**
	 * parses query and checks if it is valid or not
	 * @param predicate
	 * @return null if Query is syntactically invalid, else the Query
	 */
	public Query parseQuery(Predicate predicate){
		Query query = null;
		query = new Query();
		query.setPredicate(predicate);
		return query;
	}
	
	public KnowledgeSentence parseKnowledgeSentence(Premise premise, Conclusion conclusion){
		KnowledgeSentence ks = new KnowledgeSentence();
		ks.setPremise(premise);
		ks.setConclusion(conclusion);
		return ks;
	}
	
	public KnowledgeBase parseKnowledgeBase(List<KnowledgeSentence> sentences){
		KnowledgeBase knowledgeBase = new KnowledgeBase();
		knowledgeBase.setKnowledgeSentences(sentences);
		return knowledgeBase;
	}
	
	private String extractConclusionText(String [] parts) {
		return parts[parts.length-1];
	}
	
	/**
	 * 
	 * @param parts
	 * @return null if no premise
	 */
	private String extractPremisesText(String [] parts) {
		if(parts.length == 1){
			return null;
		}
		return parts[0];
	}
	
	private Premise extractPremise(String [] clauses) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		for(String clause : clauses) {
			predicates.add(parsePredicate(clause));
		}
		return parsePremise(predicates);
	}
	
	private Conclusion extractConclusion(String conclusionText) {
		return parseConclusion(parsePredicate(conclusionText));
		
	}
	
	public Query initQuery(String fromFile) {
		String fileText = fileIOUtil.getDataFromFileAsString(fromFile);
		if(fileText != ""){
			String [] fileLines = fileText.split("\n");
			String queryText = fileLines[0];
			//	parse query
			Query query = parseQuery(parsePredicate(queryText));
			return query;		//could be null
		}
		else{
			fileIOUtil.putLog("Could not read from input.txt");
			return null;
		}
	}
	
	public KnowledgeBase initKnowledgeBase(String fromFile) {
		KnowledgeBase knowledgeBase = null;
		List<KnowledgeSentence> knowledgeSentences = new ArrayList<KnowledgeSentence>();
		String fileText = fileIOUtil.getDataFromFileAsString(fromFile);
		if(fileText != ""){	
			String [] fileLines = fileText.split("\n");
			String queryText = fileLines[0];
			//	parse query
			Query query = parseQuery(parsePredicate(queryText));
			if(query != null){
				int kowledgeSentencesCount = Integer.parseInt(fileLines[1]);
				for(int i = 0 ; i < kowledgeSentencesCount ; i++){
					String [] parts = fileLines[i+2].split("=>");
					String conclusionText = extractConclusionText(parts);
					//	parse conclusion
					Conclusion conclusion = extractConclusion(conclusionText);
					String premisesText = extractPremisesText(parts);				// could be null
					//	parse premise
					Premise premise;
					if(premisesText != null){
						String [] clauses = premisesText.split("&");
						premise = extractPremise(clauses);
					}
					else{
						premise = null;
					}
					//	Parse knowledge sentence
					KnowledgeSentence knowledgeSentence = parseKnowledgeSentence(premise, conclusion);
					//	now make a pointer to knowledge sentence from conclusion
					conclusion.setKs(knowledgeSentence);
					knowledgeSentences.add(knowledgeSentence);
				}
				//	parse knowledge base
				knowledgeBase = parseKnowledgeBase(knowledgeSentences);
				
			}
			else{
				System.out.println("Invalid Query - contains variable(s)");
			}
		}
		else{
			fileIOUtil.putLog("Could not read from input.txt");
		}
		return knowledgeBase;		// could be null file is wrongly formatted or file could not be read
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
