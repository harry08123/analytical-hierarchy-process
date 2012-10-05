package ahp.model;

import java.util.Arrays;

import Jama.Matrix;


/**
 * A pairwise comparison test class.
 * Jama needs to be on the class path for this to run
 */
public class Test {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		
		//Create a test matrix
		Matrix C = new Matrix(new double[][] { { 1, 1, 1, 1 }, { 1, 1, 1, 1 },
				{ 1, 1, 1, 1 }, { 1, 1, 1, 1 } });
		//Set the sample values
		Test.setPairwise(0, 1, C, 5.0);
		Test.setPairwise(0, 2, C, 3.0);
		Test.setPairwise(0, 3, C, 9.0);
		Test.setPairwise(1, 3, C, 2.0);
		Test.setPairwise(2, 1, C, 5.0);
		Test.setPairwise(2, 3, C, 7.0);

		//utility matrix
		Matrix N = new Matrix(new double[][]{{1,1,1,1}});
		//utility matrix
		Matrix O = N.transpose();
		//the vector containing the final weights
		Matrix W = O.copy();
		//temporary variable to hold the changing weights
		Matrix WTEMP = null;
		//get the eigenvector for the matrix
		for ( int i=0; i<8; i++){
			WTEMP = W.copy();
			C = C.times(C);
			Matrix U=C.times(O);//get the sum of the column values
			double sum = N.times(U).get(0, 0);//get the sum of the row values
			W = U.times(1/sum);
			//loop until the values don't change anymore
			if (Arrays.equals(WTEMP.getArray(),W.getArray() ) ) break;
		}
		W.print(W.getColumnDimension(),12);																										
	}

	/**
	 * Sets the value for a matrix element and the inverse value for its pairwise.
	 *
	 * @param i the row
	 * @param j the column
	 * @param M the matrix
	 * @param value the value
	 */
	public static void setPairwise(int i, int j, Matrix M, double value) {
		M.set(i, j, value);
		M.set(j, i, 1.0 / value);
	}

}