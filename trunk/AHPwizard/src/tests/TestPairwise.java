package tests;

import java.util.Arrays;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import ahp.model.PairwiseMatrix;

public class TestPairwise {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// some objective ??

		
		//our 4 criteria
		final int no = 3;
		//criteria labels
		String criteria[] = new String[no];
		criteria[0] = "Style";
		criteria[1] = "Reliability";
		criteria[2] = "Fuel Econony";
	
		PairwiseMatrix p = new PairwiseMatrix(no, criteria);

		// Set the relative importance of each criteria
		p.setPairwiseByLabel("Reliability","Style", 2.0);
		p.setPairwiseByLabel("Style", "Fuel Econony", 3.0);
		p.setPairwiseByLabel("Reliability", "Fuel Econony", 4.0);
		
		Matrix W = p.getWeights();// this is the weightings for our criteria
		
		double[][] w = W.getArray();
		for ( int i=0; i < no; i++ ){ // print them out linked back to their label
			System.out.println(criteria[i] + "=" + w[i][0]);
			
		}
		
		
		//some stuff with the backing matrix
		
		Matrix m = p.getBackingMatrix();

	}

}
