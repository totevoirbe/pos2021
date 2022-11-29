package be.panidel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ProductModels implements Serializable {

	private static final long serialVersionUID = 1L;

	Collection<ProductModel> productModels = new ArrayList<>();

	@Override
	public String toString() {

		String productsAsString = "";

		for (ProductModel product : productModels) {
			productsAsString += ";product[" + product + "]\n";
		}
		return productsAsString;
	}

	public Collection<ProductModel> getProducts() {
		return productModels;
	}

	public void setProducts(Collection<ProductModel> products) {
		this.productModels = products;
	}

	public ProductModel getProductModel(String code) {

		if (code == null) {
			return null;
		}

		code = code.toUpperCase();

		for (ProductModel productModel : productModels) {
			if (code.equals(productModel.getCode())) {
				return productModel;
			}
		}

		return null;
	}

}