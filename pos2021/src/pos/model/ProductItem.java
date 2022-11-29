package pos.model;

import java.math.BigDecimal;

public class ProductItem {

	private BigDecimal quantity;
	private String product;
	private String description;
	private BigDecimal total;

	public ProductItem(BigDecimal quantity, String product, String description, BigDecimal total) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.description = description;
		this.total = total;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getProduct() {
		return product;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "ProductItem [quantity=" + quantity + ", product=" + product + ", description=" + description
				+ ", total=" + total + "]";
	}

}
