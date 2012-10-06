package ahp.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Jama.Matrix;

public class AhpModel {

	private String goalName;
	private String goalDescription;
	private PairwiseMatrix criteria;
	private Map<String, PairwiseMatrix> pwAlternatives = new HashMap<String, PairwiseMatrix>();
	private Map<String, Matrix> alternatives = new HashMap<String, Matrix>();
	private String[] criteriaLabels;
	private String[] alternativeLabels;

	public AhpModel(String goalName, String goalDescription,
			String[] criteriaLabels, String[] alternativeLabels) {
		this.goalName = goalName;
		this.goalDescription = goalDescription;
		this.criteriaLabels = criteriaLabels;
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
		Matrix tmp1 = tmp.times(Util);
		return m.times(1 / tmp1.get(0, 0));
	}

	public Map<String, Double> getResult() {
		Map<String, Double> result = new HashMap<String, Double>();
		int noCriteria = criteriaLabels.length;
		int noAlternatives = alternativeLabels.length;
		// Now we are ready create a matrix with the weights
		double[][] arrays = new double[noAlternatives][noCriteria];
		for (String criteriaLabel : criteriaLabels) {
			int index = Arrays.binarySearch(criteriaLabels, criteriaLabel);
			for (int x = 0; x < noAlternatives; x++) {
				arrays[Arrays.binarySearch(alternativeLabels,
						alternativeLabels[x])][index] = alternatives.get(
						criteriaLabel).get(
						Arrays.binarySearch(alternativeLabels,
								alternativeLabels[x]), 0);
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

}
