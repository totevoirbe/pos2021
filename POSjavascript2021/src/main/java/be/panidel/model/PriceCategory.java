package be.panidel.model;

public enum PriceCategory {
	SDWMINI("sdwmini"), SDWNORMAL("sdwnormal"), SDWGEANT("sdwgeant"), FITMINI("fitmini"), FITNORMAL(
			"fitnormal"), FITGEANT("fitgeant");
	private String type;

	PriceCategory(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
