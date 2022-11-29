package be.panidel.utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author franzph
 * 
 *         For pos 1.0 co,patibility
 * 
 */

@XmlRootElement(name = "root")
public class RootPos10 {

	ProductsPos10 productsPos10;

	@Override
	public String toString() {
		return (productsPos10 == null ? "null" : productsPos10.toString());
	}

	public RootPos10() {
		super();
	}

	@XmlElement(name = "products")
	public ProductsPos10 getProductsPos10() {
		return productsPos10;
	}

	public void setProductsPos10(ProductsPos10 productsPos10) {
		this.productsPos10 = productsPos10;
	}

	public ProductPos10 getProductPos10(String code) {
		for (ProductPos10 productPos10 : productsPos10.getProductPos10s()) {
			if (productPos10.getCode().equals(code)) {
				return productPos10;
			}
		}
		return null;
	}

}
