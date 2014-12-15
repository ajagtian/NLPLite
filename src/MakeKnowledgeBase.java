/**
 * 
 * @author akshayjagtiani
 *
 */

/*
 * Pilot class for this application
 */
public class MakeKnowledgeBase {
	public static void main(String [] args) {
		FileIOUtil fileIOUtil = new FileIOUtil();
		KnowledgePopulationRoutine knowledgePopulationRoutine = new KnowledgePopulationRoutine();
		InferenceRoutine inferenceRoutine = new InferenceRoutine();
		Query query = knowledgePopulationRoutine.initQuery("/input.txt"); 									//	query could be null
		KnowledgeBase knowledgeBase = knowledgePopulationRoutine.initKnowledgeBase("/input.txt");			//	if query is null KB would be null
		boolean canInfer = inferenceRoutine.infer(query, knowledgeBase);
		fileIOUtil.printFile("/output.txt", canInfer == true?"TRUE":"FALSE");  
		//fileIOUtil.printDataToFile("/outo===", knowledgeBase);
	}
}
