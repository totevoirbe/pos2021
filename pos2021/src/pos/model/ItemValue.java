package pos.model;

import pos.xml.model.ModelValidatorException;

public class ItemValue {

	public ProductItem productItem;
	public TotalValue valueTotal = new TotalValue();

	public ItemValue(ProductItem productItem) throws ModelValidatorException {
		this.productItem = productItem;
		this.add(productItem);
	}

	public void add(ItemValue itemValue) {
		valueTotal.add(itemValue.getValueTotal());
	}

	public void add(ProductItem productItem) throws ModelValidatorException {

		if (productItem.getQuantity() != null) {
			valueTotal.setValueQty(productItem.getQuantity().add(valueTotal.getValueQty()));
		}
		if (productItem.getTotal() != null) {
			valueTotal.setValueTotal(productItem.getTotal().add(valueTotal.getValueTotal()));
		}
	}

	public ProductItem getProductItem() {
		return productItem;
	}

	public TotalValue getValueTotal() {
		return valueTotal;
	}

	@Override
	public String toString() {
		return "ItemValue [productItem=" + productItem + ", valueTotal=" + valueTotal + "]";
	}

}