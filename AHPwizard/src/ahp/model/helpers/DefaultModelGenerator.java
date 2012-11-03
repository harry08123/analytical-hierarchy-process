package ahp.model.helpers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ahp.model.AhpModel;
import ahp.model.CriteriaType;
import ahp.model.PairwiseMatrix;

public class DefaultModelGenerator {
	
	public static final String GOAL_NAME = "Logistics and Transport";
	public static final String GOAL_NAME_CAR = "Buy a car";
	public static AhpModel generateDefaultModel(){
		AhpModel project = new AhpModel();
		
		project.setGoalName(GOAL_NAME);
		project.setGoalDescription("The default Logistics and Transport model");
		
		String criteriaLabels[] = new String[6];
		criteriaLabels[0] = "Cost";
		criteriaLabels[1] = "Travel Time";
		criteriaLabels[2] = "Safety";
		criteriaLabels[3] = "Pollution";
		criteriaLabels[4] = "Accessabilty";
		criteriaLabels[5] = "Energy Consumption";
		
		Map <String,String> cMap = new HashMap<String,String>();
		cMap.put(criteriaLabels[0], CriteriaType.REAL_LOWER_IS_BETTER );
		cMap.put(criteriaLabels[1] , CriteriaType.REAL_LOWER_IS_BETTER );
		cMap.put(criteriaLabels[2] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[3] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[4] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[5] , CriteriaType.PAIRWISE_VALUE );
		
		Arrays.sort(criteriaLabels);
		project.setCriteriaLabels(criteriaLabels);
		project.setCriteria(new PairwiseMatrix(project.getCriteriaLabels()));
		project.setCriteriaType(cMap);

		// alternative labels
		String alternativeLabels[] = new String[3];
		alternativeLabels[0] = "Rail";
		alternativeLabels[1] = "Sea";
		alternativeLabels[2] = "Road";
		
		Arrays.sort(alternativeLabels);
		project.setAlternativeLabels(alternativeLabels);
		
		project.setStatus("prepare");
		
		return project;
	}
	
	public static AhpModel generateCarModel(){
		AhpModel project = new AhpModel(false);
		project.setGoalName(GOAL_NAME_CAR);
		project.setGoalDescription("Example with consistency check disabled");

		String criteriaLabels[] = new String[3];
		criteriaLabels[0] = "Style";
		criteriaLabels[1] = "Reliability";
		criteriaLabels[2] = "Fuel Econony (kms/l)";
		
		Map <String,String> cMap = new HashMap<String,String>();
		cMap.put(criteriaLabels[0], CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[1] , CriteriaType.PAIRWISE_VALUE );
		cMap.put(criteriaLabels[2] , CriteriaType.REAL_HIGHER_IS_BETTER );
		
		Arrays.sort(criteriaLabels);
		project.setCriteriaLabels(criteriaLabels);
		project.setCriteria(new PairwiseMatrix(project.getCriteriaLabels()));
		project.setCriteriaType(cMap);

		// alternative labels
		String alternativeLabels[] = new String[4];
		alternativeLabels[0] = "Civic";
		alternativeLabels[1] = "Saturn";
		alternativeLabels[2] = "Escort";
		alternativeLabels[3] = "Clio";
		Arrays.sort(alternativeLabels);
		project.setAlternativeLabels(alternativeLabels);
		
		project.setStatus("prepare");

		return project;
		
	}

}
