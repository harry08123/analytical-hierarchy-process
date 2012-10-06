package tests;

import Jama.Matrix;
import ahp.model.PairwiseMatrix;

public class TestPairwise {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PairwiseMatrix p = new PairwiseMatrix(4, 4);

		// Set the sample values
		p.setPairwise(0, 1, 5.0);
		p.setPairwise(0, 2, 3.0);
		p.setPairwise(0, 3, 9.0);
		p.setPairwise(1, 3, 2.0);
		p.setPairwise(2, 1, 5.0);
		p.setPairwise(2, 3, 7.0);
		
		Matrix W = p.getWeights();
		
		W.print(W.getColumnDimension(),12);		

	}

}
