package ahp.model;

import java.io.Serializable;
import java.util.Arrays;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class PairwiseMatrix implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private double[][] util;
	private Matrix backingMatrix;
	private String[] labels;
	private Matrix W;
	private int size;

	public PairwiseMatrix( String[] labels) {
		size = labels.length;
		double[][] array = new double[size][size];
		double[] column = new double[size];
		Arrays.fill(column, 1.0);
		for ( int x=0;x<size;x++){array[x]=column.clone();}
		backingMatrix = new Matrix(array);
		util = new double[backingMatrix.getRowDimension()][1];
		Arrays.fill(util, new double[]{1.0});
		backingMatrix.print(backingMatrix.getColumnDimension(), 3 );
		
		this.labels = labels;
		Arrays.sort(this.labels);
	}

	public Matrix getWeights() {
		if (W != null) return W;
		// utility column vector to get sum of columns
		Matrix O = new Matrix(util);
		// utility row vector to get sum of rows
		Matrix N =  O.transpose();
		// the column vector containing the final weights
		W = O.copy();
		// temporary column vector to hold the changing weights
		Matrix WTEMP = null;
		// get the eigenvector for the matrix
		Matrix backingMatrixTmp = backingMatrix.copy();
		for (int i = 0; i < 8; i++) {
			WTEMP = W.copy();
			backingMatrixTmp = backingMatrixTmp.times(backingMatrixTmp);
			Matrix U = backingMatrixTmp.times(O);// get the sum of the column values
			double sum = N.times(U).get(0, 0);// get the sum of the row values
			W = U.times(1 / sum);//divide by the sum
			// loop until the values don't change anymore
			if (Arrays.equals(WTEMP.getArray(), W.getArray())) break;
		}
		return W;
	}

	public double getWeightByLabel(String label){
		return  getWeights().get(Arrays.binarySearch(labels, label), 0);
	}
	
	public Matrix getBackingMatrix() {
		return backingMatrix;
	}

	public void setPairwiseByLabel(String s1, String s2, double s2Value ){
		int i = Arrays.binarySearch(labels, s1);
		int j = Arrays.binarySearch(labels, s2);
		backingMatrix.set(i, j, s2Value);
		backingMatrix.set(j, i, 1.0 / s2Value);
		System.out.println("After setting values for " + s1 + " to  " + s2Value  + " & " + s2 + " " +  1.0 / s2Value  );
	}

	public String[] getLabels() {
		return labels;
	}
	
	private double getMaxEigenValue(){
	    EigenvalueDecomposition Eig = new EigenvalueDecomposition(backingMatrix);
	    double[] values = Eig.getRealEigenvalues() ;
	    double max=0.0;

	    for(int i=0;i<size;i++){
	      if (values[i]>=max) max=values[i];
	    }
	    return max;
	  }
	
	private double getRandomInconsistency(){
	    switch (size)
	    {
	      case 0 : return 0.00;
	      case 1 : return 0.00;
	      case 2 : return 0.00;
	      case 3 : return 0.58;
	      case 4 : return 0.90;
	      case 5 : return 1.12;
	      case 6 : return 1.24;
	      case 7 : return 1.32;
	      case 8 : return 1.41;
	      case 9 : return 1.45;
	      case 10: return 1.49;
	      default : return 1.5;
	    }
	}
	
	public boolean isConsistient(){
		if ( size<=2) return true;
		double ratio = ((getMaxEigenValue()- size)/ (size-1.0))
				/getRandomInconsistency();
		System.out.println(ratio);
	    if (ratio <= 0.1) return true;
	    return false;
	  }

}