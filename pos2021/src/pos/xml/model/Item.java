package pos.xml.model;

public class Item implements ModelValidator {

	private Integer quantity;
	private String product;
	private String description;
	private Double unitPrice;
	private Integer tvaTakeAway;
	private Integer tvaTakeOnPlace;

	public Item() {
	}

	public Item(Integer quantity, String product, String description, Double unitPrice, Integer tvaTakeAway,
			Integer tvaTakeOnPlace) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.description = description;
		this.unitPrice = unitPrice;
		this.tvaTakeAway = tvaTakeAway;
		this.tvaTakeOnPlace = tvaTakeOnPlace;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getTvaTakeAway() {
		return tvaTakeAway;
	}

	public void setTvaTakeAway(Integer tvaTakeAway) {
		this.tvaTakeAway = tvaTakeAway;
	}

	public Integer getTvaTakeOnPlace() {
		return tvaTakeOnPlace;
	}

	public void setTvaTakeOnPlace(Integer tvaTakeOnPlace) {
		this.tvaTakeOnPlace = tvaTakeOnPlace;
	}

	@Override
	public String toString() {
		return "Item [quantity=" + quantity + ", product=" + product + ", description=" + description + ", unitPrice="
				+ unitPrice + ", tvaTakeAway=" + tvaTakeAway + ", tvaTakeOnPlace=" + tvaTakeOnPlace + "]";
	}

	@Override
	public boolean validate() throws ModelValidatorException {

		boolean valid = true;

		String messageError = "";

		if (quantity == null) {
			messageError += "(quantity is null)";
			valid = false;
		}
		if (product == null || product.trim().length() == 0) {
			messageError += "(product is null)";
			valid = false;
		}
		if (description == null || description.trim().length() == 0) {
			messageError += "(description is null)";
			valid = false;
		}
		if (unitPrice == null) {
			messageError += "(unitPrice is null)";
			valid = false;
		}
		if (tvaTakeAway == null) {
			messageError += "(tvaTakeAway is null)";
			valid = false;
		}
		if (tvaTakeOnPlace == null) {
			messageError += "(tvaTakeOnPlace is null)";
			valid = false;
		}

		if (!valid) {
			throw new ModelValidatorException("[Item-" + product + ":" + messageError + "]");
		}

		return true;
	}

}
