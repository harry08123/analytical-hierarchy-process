package ahp.model;

public class PairwiseResult {
	
	private String winner;
	private String loser;
	private double score;
	private boolean isValid = false;
	private static final String delimiter =":";
	private String matrixLabel;
	private boolean noWinner = false;
	private String type;
	
	
	public PairwiseResult(){
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNoWinner() {
		return noWinner;
	}


	public String getMatrixLabel() {
		return matrixLabel;
	}


	public void setResult(String result) {
		if (result == null) return;
		String[] split= result.split(delimiter);
		if (split.length != 4 ) {isValid = false;return;}
		if (split[1].equalsIgnoreCase(CriteriaType.REAL_HIGHER_IS_BETTER) || 
				split[1].equalsIgnoreCase(CriteriaType.REAL_LOWER_IS_BETTER) ){
			setRealNumResult(split);
		}else{
			setPairwiseResult(split);
		}
		
	}
	
	private void setPairwiseResult(String[] split){
		type = CriteriaType.PAIRWISE_VALUE;
		int val = Integer.parseInt(split[3]);
		if ( val > 0 ){
			winner = split[2];
			loser = split[1];
		}else if ( val < 0 ){
			winner = split[1];
			loser = split[2];
		}else if ( val == 0){
			winner = split[1];
			loser = split[2];
			isValid = true;
			noWinner = true;
		}else{
			return;
		}
		matrixLabel = split[0];
		score = Math.abs(val)+1;
		isValid = true;
		
	}
	
	private void setRealNumResult(String[] split){
		
		this.type = split[1];
		noWinner = true;
		score = Double.parseDouble(split[3]);
		winner = split[2];
		matrixLabel = split[0];
		isValid = true;
	}


	public String getWinner() {
		return winner;
	}


	public String getLoser() {
		return loser;
	}


	public double getScore() {
		return score;
	}


	public boolean isValid() {
		return isValid;
	}

	@Override
	public String toString() {
		return "PairwiseResult [winner=" + winner + ", loser=" + loser
				+ ", score=" + score + ", isValid=" + isValid
				+ ", matrixLabel=" + matrixLabel + ", noWinner=" + noWinner
				+ "]";
	}
	
	
	
}
