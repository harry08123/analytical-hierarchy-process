package tests;

import java.util.Arrays;

import Jama.Matrix;
import ahp.model.AhpModel;
import ahp.model.PairwiseMatrix;

public class TestPairwise {

	/**
	 * using the example: An Illustrated Guide to the ANALYTIC HIERARCHY PROCESS
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		final int noCriteria = 3;
		String criteriaLabels[] = new String[noCriteria];
		criteriaLabels[0] = "Style";
		criteriaLabels[1] = "Reliability";
		criteriaLabels[2] = "Fuel Econony";
		Arrays.sort(criteriaLabels);
		
		// alternative labels
		final int noAlternatives = 4;
		String alternativeLabels[] = new String[noAlternatives];
		alternativeLabels[0] = "Civic";
		alternativeLabels[1] = "Saturn";
		alternativeLabels[2] = "Escort";
		alternativeLabels[3] = "Clio";
		Arrays.sort(alternativeLabels);
		
		AhpModel model = new AhpModel("Buy Car","Buy car",criteriaLabels,alternativeLabels);
		
		// Set the relative importance of each criteria
		PairwiseMatrix criteria = new PairwiseMatrix(criteriaLabels);
		criteria.setPairwiseByLabel("Reliability", "Style", 2.0);
		criteria.setPairwiseByLabel("Style", "Fuel Econony", 3.0);
		criteria.setPairwiseByLabel("Reliability", "Fuel Econony", 4.0);
		model.setCriteria(criteria);
		
		//Set the style pairwise
		PairwiseMatrix style = new PairwiseMatrix(alternativeLabels);
		style.setPairwiseByLabel("Civic", "Escort", 4.0);
		style.setPairwiseByLabel("Saturn", "Civic", 4.0);
		style.setPairwiseByLabel("Saturn", "Escort", 4.0);
		style.setPairwiseByLabel("Clio", "Civic", 6.0);
		style.setPairwiseByLabel("Clio", "Saturn", 4.0);
		style.setPairwiseByLabel("Clio", "Escort", 5.0);
		model.addToPw("Style", style);
		
		// Set the reliability pairwise
		PairwiseMatrix reliability = new PairwiseMatrix(alternativeLabels);
		reliability.setPairwiseByLabel("Civic", "Saturn", 2.0);
		reliability.setPairwiseByLabel("Civic", "Escort", 5.0);
		reliability.setPairwiseByLabel("Saturn", "Escort", 3.0);
		reliability.setPairwiseByLabel("Saturn", "Clio", 2.0);
		reliability.setPairwiseByLabel("Clio", "Civic", 1.0);
		reliability.setPairwiseByLabel("Clio", "Escort", 4.0);
		model.addToPw("Reliability", reliability);
		
		// Fuel economy is quantitative so no pairwise just the known values;
		Matrix fuelEco = new Matrix(noAlternatives, 1);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Civic"), 0, 34.0);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Saturn"), 0, 27.0);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Escort"), 0, 24.0);
		fuelEco.set(Arrays.binarySearch(alternativeLabels, "Clio"), 0, 28.0);
		model.addToAl("Fuel Econony", fuelEco);
		
		model.getResult();
	}

}
