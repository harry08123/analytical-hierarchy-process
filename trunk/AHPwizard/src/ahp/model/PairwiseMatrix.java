package ahp.model;

import java.util.Arrays;

import Jama.Matrix;

public class PairwiseMatrix {

	/**
	 * 
	 */
	private double[][] util;
	private Matrix backingMatrix;
	private String[][] labels;

	public PairwiseMatrix( int i, String[][] labels) {
		double[][] array = new double[i][i];
		double[] column = new double[i];
		Arrays.fill(column, 1.0);
		for ( int x=0;x<i;x++){array[x]=column.clone();}
		backingMatrix = new Matrix(array);
		util = new double[backingMatrix.getRowDimension()][1];
		Arrays.fill(util, new double[]{1.0});
		this.labels = labels;
	}

	public Matrix getWeights() {
		// utility column vector to get sum of columns
		Matrix O = new Matrix(util);
		// utility row vector to get sum of rows
		Matrix N =  O.transpose();
		// the column vector containing the final weights
		Matrix W = O.copy();
		// temporary column vector to hold the changing weights
		Matrix WTEMP = null;
		// get the eigenvector for the matrix
		for (int i = 0; i < 8; i++) {
			WTEMP = W.copy();
			backingMatrix = backingMatrix.times(backingMatrix);
			Matrix U = backingMatrix.times(O);// get the sum of the column values
			double sum = N.times(U).get(0, 0);// get the sum of the row values
			W = U.times(1 / sum);//divide by the sum
			// loop until the values don't change anymore
			if (Arrays.equals(WTEMP.getArray(), W.getArray())) break;
		}
		return W;
	}

	public Matrix getBackingMatrix() {
		return backingMatrix.copy();
	}

	public void setPairwise(int i, int j, double value) {
		backingMatrix.set(i, j, value);
		backingMatrix.set(j, i, 1.0 / value);
	}
}