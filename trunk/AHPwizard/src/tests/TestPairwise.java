package tests;

import java.util.Arrays;

import Jama.Matrix;
import ahp.model.PairwiseMatrix;

public class TestPairwise {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// using the example:
		//An Illustrated Guide to the ANALYTIC HIERARCHY PROCESS


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

		Matrix W = criteria.getWeights();// this is the weightings for our// criteria
		System.out.println("Style = "+criteria.getWeightByLabel("Style"));
		System.out.println("Reliability = "+criteria.getWeightByLabel("Reliability"));
		System.out.println("Fuel Econony = "+criteria.getWeightByLabel("Fuel Econony"));
		
		// now do the alternatives relative to the criteria
		final int noAlternatives = 4;

		// alternative labels
		String carLabels[] = new String[noAlternatives];
		carLabels[0] = "Civic";
		carLabels[1] = "Saturn";
		carLabels[2] = "Escort";
		carLabels[3] = "Clio";

		Arrays.sort(carLabels);

		PairwiseMatrix style = new PairwiseMatrix(carLabels);// relative to
																// style

		style.setPairwiseByLabel("Civic", "Escort", 4.0);
		style.setPairwiseByLabel("Saturn", "Civic", 4.0);
		style.setPairwiseByLabel("Saturn", "Escort", 4.0);
		style.setPairwiseByLabel("Clio", "Civic", 6.0);
		style.setPairwiseByLabel("Clio", "Saturn", 4.0);
		style.setPairwiseByLabel("Clio", "Escort", 5.0);

		PairwiseMatrix reliability = new PairwiseMatrix(carLabels);// relative
																	// to
																	// reliability

		reliability.setPairwiseByLabel("Civic", "Saturn", 2.0);
		reliability.setPairwiseByLabel("Civic", "Escort", 5.0);
		reliability.setPairwiseByLabel("Saturn", "Escort", 3.0);
		reliability.setPairwiseByLabel("Saturn", "Clio", 2.0);
		reliability.setPairwiseByLabel("Clio", "Civic", 1.0);
		reliability.setPairwiseByLabel("Clio", "Escort", 4.0);

		// Fuel economy is quantitative so no pairwise just the known values;

		Matrix fuelEco = new Matrix(noAlternatives, 1);
		fuelEco.set(Arrays.binarySearch(carLabels, "Civic"), 0, 34.0);
		fuelEco.set(Arrays.binarySearch(carLabels, "Saturn"), 0, 27.0);
		fuelEco.set(Arrays.binarySearch(carLabels, "Escort"), 0, 24.0);
		fuelEco.set(Arrays.binarySearch(carLabels, "Clio"), 0, 28.0);

		// normalise the values. Need to encapsulate and hide this complexity
		Matrix tmp = fuelEco.transpose();
		Matrix tmp1 = tmp.times(new Matrix(new double[][] { { 1 }, { 1 },
				{ 1 }, { 1 } }));
		Matrix fuelEcoWeights = fuelEco.times(1 / tmp1.get(0, 0));

		// Now we are ready create a matrix with the weights

		style.getWeights();
		reliability.getWeights();

		double[][] arrays = new double[4][3];
		
		int index = Arrays.binarySearch(criteriaLabels,"Style");
		arrays[Arrays.binarySearch(carLabels, "Civic")][index]= style.getWeightByLabel("Civic");
		arrays[Arrays.binarySearch(carLabels, "Clio")][index]= style.getWeightByLabel("Clio");
		arrays[Arrays.binarySearch(carLabels, "Escort")][index]= style.getWeightByLabel("Escort");
		arrays[Arrays.binarySearch(carLabels, "Saturn")][index]= style.getWeightByLabel("Saturn");
		
		index = Arrays.binarySearch(criteriaLabels,"Reliability");
		arrays[Arrays.binarySearch(carLabels, "Civic")][index]= reliability.getWeightByLabel("Civic");
		arrays[Arrays.binarySearch(carLabels, "Clio")][index]= reliability.getWeightByLabel("Clio");
		arrays[Arrays.binarySearch(carLabels, "Escort")][index]= reliability.getWeightByLabel("Escort");
		arrays[Arrays.binarySearch(carLabels, "Saturn")][index]= reliability.getWeightByLabel("Saturn");
		
		index = Arrays.binarySearch(criteriaLabels,"Fuel Econony");
		arrays[Arrays.binarySearch(carLabels, "Civic")][index]= fuelEcoWeights.get(Arrays.binarySearch(carLabels, "Civic"),0);
		arrays[Arrays.binarySearch(carLabels, "Clio")][index]= fuelEcoWeights.get(Arrays.binarySearch(carLabels, "Clio"),0);
		arrays[Arrays.binarySearch(carLabels, "Escort")][index]= fuelEcoWeights.get(Arrays.binarySearch(carLabels, "Escort"),0);
		arrays[Arrays.binarySearch(carLabels, "Saturn")][index]= fuelEcoWeights.get(Arrays.binarySearch(carLabels, "Saturn"),0);
		
		
		
		
		Matrix m = new Matrix(arrays);
		m.print(m.getColumnDimension(), 10);
		W.print(W.getColumnDimension(), 10);
		Matrix R = m.times(W);
		R.print(R.getColumnDimension(), 10);
		
		System.out.println("Civic score = " + R.get(Arrays.binarySearch(carLabels, "Civic"),0));
		
		System.out.println("Saturn Score = " + R.get(Arrays.binarySearch(carLabels, "Saturn"),0) );
		
		System.out.println("Clio score = " + R.get(Arrays.binarySearch(carLabels, "Clio"), 0) );
		
		System.out.println("Escort score = " + R.get(Arrays.binarySearch(carLabels, "Escort"), 0));
	}

}
