package be.panidel.model;

public enum SaleState {

	CLOSE("close"), CANCEL("cancel");

	private String state;

	SaleState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}
