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
		final int no = 4;
		//our 4 criteria
		String criteria[][] = new String[1][no];
		PairwiseMatrix p = new PairwiseMatrix(no, criteria);

		// Set the relative importance of each criteria
		p.setPairwise(0, 1, 5.0);
		p.setPairwise(0, 2, 3.0);
		p.setPairwise(0, 3, 9.0);
		p.setPairwise(1, 3, 2.0);
		p.setPairwise(2, 1, 5.0);
		p.setPairwise(2, 3, 7.0);
		
		Matrix W = p.getWeights();// this is the weightings for our criteria
		
		W.print(W.getColumnDimension(),12);	
		
		//some stuff with the backing matrix
		
		Matrix m = p.getBackingMatrix();

	}

}
