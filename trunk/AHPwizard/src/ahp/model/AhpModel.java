package ahp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Jama.Matrix;

public class AhpModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goalName;
	private String goalDescription;
	private PairwiseMatrix criteria;
	private Map<String, PairwiseMatrix> pwAlternatives = new HashMap<String, PairwiseMatrix>();
	private Map<String, Matrix> alternatives = new HashMap<String, Matrix>();
	private String[] criteriaLabels;
	private Map<String,String> criteriaType;
	private String[] alternativeLabels;
	private String status;
	private boolean checkConsistency = true;
	
	
	public AhpModel() {
		this.status = "new";
	}
	
	public AhpModel(boolean checkConsistency) {
		this.status = "new";
		this.setCheckConsistency(checkConsistency);
	}
	
	
	public void setPwAlternatives(Map<String, PairwiseMatrix> pwAlternatives) {
		this.pwAlternatives = pwAlternatives;
	}



	public void setAlternatives(Map<String, Matrix> alternatives) {
		this.alternatives = alternatives;
	}



	public AhpModel(String goalName, String goalDescription,
			String[] criteriaLabels, String[] alternativeLabels) {
		this.goalName = goalName;
		this.goalDescription = goalDescription;
		this.criteriaLabels = criteriaLabels;
		this.alternativeLabels = alternativeLabels;
		this.status = "new";
	}
	
	public Map<String, String> getCriteriaType() {
		return criteriaType;
	}

	public void setCriteriaType(Map<String, String> criteriaType) {
		this.criteriaType = criteriaType;
	}

	public String[] getCriteriaLabels() {
		return criteriaLabels;
	}

	public void setCriteriaLabels(String[] criteriaLabels) {
		this.criteriaLabels = criteriaLabels;
	}

	public String[] getAlternativeLabels() {
		return alternativeLabels;
	}

	public void setAlternativeLabels(String[] alternativeLabels) {
		this.alternativeLabels = alternativeLabels;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getGoalDescription() {
		return goalDescription;
	}

	public void setGoalDescription(String goalDescription) {
		this.goalDescription = goalDescription;
	}

	public PairwiseMatrix getCriteria() {
		return criteria;
	}

	public void setCriteria(PairwiseMatrix criteria) {
		this.criteria = criteria;
	}

	public void addToPw(String name, PairwiseMatrix pwAlternative) {
		pwAlternatives.put(name, pwAlternative);
		alternatives.put(name, pwAlternative.getWeights());
	}

	public void addToAl(String name, Matrix alternative) {
		alternatives.put(name, normailse(alternative));
	}

	private Matrix normailse(Matrix m) {
		Matrix tmp = m.transpose();
		double[] util = new double[m.getRowDimension()];
		Arrays.fill(util, 1.0);
		Matrix Util = new Matrix(util, util.length);
		tmp = tmp.times(Util);
		return m.times(1 / tmp.get(0, 0));
	}

	public Map<String, Double> getResult() {
		Map<String, Double> result = new HashMap<String, Double>();
		int noCriteria = criteriaLabels.length;
		int noAlternatives = alternativeLabels.length;
		// Now we are ready create a matrix with the weights
		double[][] arrays = new double[noAlternatives][noCriteria];
		for (String criteriaLabel : criteriaLabels) {
			int index = Arrays.binarySearch(criteriaLabels, criteriaLabel);
			System.out.println( "Index of " +  criteriaLabel + " is " + index);
			for (int x = 0; x < noAlternatives; x++) {
				int indexOfAlternative = Arrays.binarySearch(alternativeLabels, alternativeLabels[x]);
				arrays[indexOfAlternative][index] = alternatives.get(criteriaLabel).get(indexOfAlternative, 0);
			}
		}
		Matrix m = new Matrix(arrays);
		Matrix R = m.times(criteria.getWeights());
		for (int x = 0; x < noAlternatives; x++) {
			result.put(alternativeLabels[x], R.get(Arrays.binarySearch(
					alternativeLabels, alternativeLabels[x]), 0));
		}
		return result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCriteriaType( String type ){
		return criteriaType.get(type);
	}

	public boolean isCheckConsistency() {
		return checkConsistency;
	}

	public void setCheckConsistency(boolean checkConsistency) {
		this.checkConsistency = checkConsistency;
	}
}