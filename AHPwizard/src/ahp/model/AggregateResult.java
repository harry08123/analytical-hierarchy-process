package ahp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Double>> userResults;
	private String projectName;

	public AggregateResult(String projectName) {
		this.projectName = projectName;
		userResults = new HashMap<String, Map<String, Double>>();
	}

	public synchronized void addResult(Map<String, Double> result, User user) {
		//if (usersAllowed.contains(user)) {
			userResults.put(user.getName(), result);

		//}

	}

	public Map<String, Double> getAggreateResult() {
		Map<String, Double> result = new HashMap<String, Double>();
		for (String u : userResults.keySet()) {
			Map<String, Double> uResult = userResults.get(u);
			for (String alternative : uResult.keySet()) {
				if (result.containsKey(alternative)) {
					result.put(alternative,  result.get(alternative) + uResult.get(alternative));
				}else{
					result.put(alternative, uResult.get(alternative));
				}
				
			}

		}
		int size = userResults.size();
		for (String alternative : result.keySet() ){
			result.put(alternative,result.get(alternative)/size);
		}
		return result;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public int getSize(){
		return userResults.size();
	}

}