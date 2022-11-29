package be.panidel.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author franzph
 * 
 *         For pos 1.0 co,patibility
 * 
 */

@XmlRootElement(name = "products")
public class ProductsPos10 {

	private List<ProductPos10> productPos10s;

	@Override
	public String toString() {

		String desc = "";

		for (ProductPos10 productPos10 : productPos10s) {
			desc += ";productPos10[" + productPos10 + "]";
		}
		return desc;

	}

	@XmlElement(name = "product")
	public List<ProductPos10> getProductPos10s() {
		return productPos10s;
	}

	public void setProductPos10s(List<ProductPos10> productPos10s) {
		this.productPos10s = productPos10s;
	}

}
