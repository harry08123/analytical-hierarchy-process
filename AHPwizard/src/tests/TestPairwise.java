package tests;

import java.util.Arrays;

import Jama.Matrix;
import ahp.model.PairwiseMatrix;

public class TestPairwise {

	/**
	 * using the example: An Illustrated Guide to the ANALYTIC HIERARCHY PROCESS
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// number of criteria
		final int noCriteria = 3;
		// criteria labels
		String criteriaLabels[] = new String[noCriteria];
		criteriaLabels[0] = "Style";
		criteriaLabels[1] = "Reliability";
		criteriaLabels[2] = "Fuel Econony";
		Arrays.sort(criteriaLabels);

		PairwiseMatrix criteria = new PairwiseMatrix(criteriaLabels);
		// Set the relative importance of each criteria
		criteria.setPairwiseByLabel("Reliability", "Style", 2.0);
		criteria.setPairwiseByLabel("Style", "Fuel Econony", 3.0);
		criteria.setPairwiseByLabel("Reliability", "Fuel Econony", 4.0);

		Matrix W = criteria.getWeights();

		// now do the alternatives relative to the criteria
		final int noAlternatives = 4;

		// alternative labels
		String alternativeLabels[] = new String[noAlternatives];
		alternativeLabels[0] = "Civic";
		alternativeLabels[1] = "Saturn";
		alternativeLabels[2] = "Escort";
		alternativeLabels[3] = "Clio";

		Arrays.sort(alternativeLabels);

		PairwiseMatrix style = new PairwiseMatrix(alternativeLabels);
		style.setPairwiseByLabel("Civic", "Escort", 4.0);
		style.setPairwiseByLabel("Saturn", "Civic", 4.0);
		style.setPairwiseByLabel("Saturn", "Escort", 4.0);
		style.setPairwiseByLabel("Clio", "Civic", 6.0);
		style.setPairwiseByLabel("Clio", "Saturn", 4.0);
		style.setPairwiseByLabel("Clio", "Escort", 5.0);

		PairwiseMatrix reliability = new PairwiseMatrix(alternativeLabels);
		reliability.setPairwiseByLabel("Civic", "Saturn", 2.0);
		reliability.setPairwiseByLabel("Civic", "Escort", 5.0);
		reliability.setPairwiseByLabel("Saturn", "Escort", 3.0);
		reliability.setPairwiseByLabel("Saturn", "Clio", 2.0);
		reliability.setPairwiseByLabel("Clio", "Civic", 1.0);
		reliability.setPairwiseByLabel("Clio", "Escort", 4.0);

		//Fuel economy is quantitative so no pairwise just the known values;
		Matrix fuelEco = new Matrix(noAlternatives, 1);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Civic"), 0, 34.0);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Saturn"), 0, 27.0);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Escort"), 0, 24.0);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Clio"), 0, 28.0);

		// normalise the values. Need to encapsulate and hide this complexity
		Matrix tmp = fuelEco.transpose();
		
		double[] util = new double[noAlternatives];
		Arrays.fill(util, 1.0);
		Matrix Util = new  Matrix(util, util.length);
		Matrix tmp1 = tmp.times(Util);
		Matrix fuelEcoWeights = fuelEco.times(1 / tmp1.get(0, 0));

		// Now we are ready create a matrix with the weights
		double[][] arrays = new double[noAlternatives][noCriteria];

		int index = Arrays.binarySearch(criteriaLabels, "Style");
		for (int x = 0; x < noAlternatives; x++) {
			arrays[Arrays.binarySearch(alternativeLabels, alternativeLabels[x])][index] = style
					.getWeightByLabel(alternativeLabels[x]);
		}

		index = Arrays.binarySearch(criteriaLabels, "Reliability");
		for (int x = 0; x < noAlternatives; x++) {
			arrays[Arrays.binarySearch(alternativeLabels, alternativeLabels[x])][index] = reliability
					.getWeightByLabel(alternativeLabels[x]);
		}

		index = Arrays.binarySearch(criteriaLabels, "Fuel Econony");
		for (int x = 0; x < noAlternatives; x++) {
			arrays[Arrays.binarySearch(alternativeLabels, alternativeLabels[x])][index] = fuelEcoWeights
					.get(Arrays.binarySearch(alternativeLabels, alternativeLabels[x]), 0);
		}

		Matrix m = new Matrix(arrays);
		Matrix R = m.times(W);
		
		for ( int x = 0 ; x < noAlternatives ; x++ ){
		System.out.println( alternativeLabels[x] + " score = "
				+ R.get(Arrays.binarySearch(alternativeLabels, alternativeLabels[x] ), 0));
		}
		
	}

}
