package ahp.model;

public class User {
	
	private double weight;
	private String name;
	
	public User( String name, double weight ){
		this.name = name;
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
