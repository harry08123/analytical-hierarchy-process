package ahp.model;

import java.util.Arrays;

import Jama.Matrix;

public class Test {

	public static void main(String args[]) {
		
		Matrix C = new Matrix(new double[][] { { 1, 1, 1, 1 }, { 1, 1, 1, 1 },
				{ 1, 1, 1, 1 }, { 1, 1, 1, 1 } });

		Test.setPairwise(0, 1, C, 5.0);
		Test.setPairwise(0, 2, C, 3.0);
		Test.setPairwise(0, 3, C, 9.0);
		Test.setPairwise(1, 3, C, 2.0);
		Test.setPairwise(2, 1, C, 5.0);
		Test.setPairwise(2, 3, C, 7.0);


		Matrix O = new Matrix(new double[][]{{1,1,1,1}}).transpose();
		Matrix W = O.copy();
		Matrix WTEMP = null;
		for ( int i=0; i<8; i++){
			WTEMP = W.copy();
			C = C.times(C);
			Matrix U=C.times(O);
			double sum = 0.0;
			for (int x=0; x < U.getRowDimension(); x++) sum+=U.get(x,0);
			W = U.times(1/sum);
			if (Arrays.equals(WTEMP.getArray(),W.getArray() ) ) break;
		}
		W.print(0,3);																										
	}

	public static void setPairwise(int i, int j, Matrix M, double value) {
		M.set(i, j, value);
		M.set(j, i, 1.0 / value);
	}

}