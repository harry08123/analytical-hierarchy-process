package ahp.model.helpers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ahp.model.AhpModel;
import ahp.model.CriteriaType;
import ahp.model.PairwiseMatrix;

public class DefaultModelGenerator {
	
	public static final String GOAL_NAME = "Logistics and Transport";
	public static AhpModel generateDefaultModel(){
		AhpModel project = new AhpModel();
		
		project.setGoalName(GOAL_NAME);
		project.setGoalDescription("The default Logistics and Transport model");
		
		String criteriaLabels[] = new String[7];
		criteriaLabels[0] = "Cost";
		criteriaLabels[1] = "Travel Time";
		criteriaLabels[2] = "Safety";
		criteriaLabels[3] = "Pollution";
		criteriaLabels[4] = "Noise";
		criteriaLabels[5] = "Accessabilty";
		criteriaLabels[6] = "Energy Consumption";
		
		Map <String,String> cMap = new HashMap<String,String>();
		cMap.put(criteriaLabels[0], CriteriaType.REAL_VALUE );
		cMap.put(criteriaLabels[1] , CriteriaType.REAL_VALUE );
		cMap.put(criteriaLabels[2] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[3] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[4] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[5] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[6] , CriteriaType.PAIRWISE_VALUE );
		
		Arrays.sort(criteriaLabels);
		project.setCriteriaLabels(criteriaLabels);
		project.setCriteria(new PairwiseMatrix(project.getCriteriaLabels()));
		project.setCriteriaType(cMap);

		// alternative labels
		String alternativeLabels[] = new String[4];
		alternativeLabels[0] = "Rail";
		alternativeLabels[1] = "Sea";
		alternativeLabels[2] = "Road";
		alternativeLabels[3] = "Air";
		
		Arrays.sort(alternativeLabels);
		project.setAlternativeLabels(alternativeLabels);
		
		project.setStatus("prepare");
		
		return project;
	}

}
