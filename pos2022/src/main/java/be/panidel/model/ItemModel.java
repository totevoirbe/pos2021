package be.panidel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "item")
@NamedQueries({ @NamedQuery(name = "pos.item.all", query = "SELECT e FROM ItemModel e") })
public class ItemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ITEM_ID")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID")
	private SaleModel sale;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "item")
	private List<ProductModel> productModels;
	@Basic
	private BigDecimal quantity;
	@Basic
	private BigDecimal unitPrice;
	@Basic
	private Boolean deleted;
	@Basic
	private PriceCategory priceCategory;

	@Override
	public String toString() {

		StringBuffer description = new StringBuffer("id[" + id + "]; operationModel[");

		if (productModels != null) {
			for (ProductModel productModel : productModels) {
				description.append("productModel[" + productModel + "]; ");
			}
		} else {
			description.append("productModel[NULL]; ");
		}

		description.append("quantity[" + quantity + "]; unitPrice[" + unitPrice + "]; deleted[" + deleted
				+ "]; priceCategory[" + priceCategory + "]");

		return description.toString();
	}

	public ItemModel() {
	}

	public ItemModel(long id, SaleModel sale, List<ProductModel> productModels, BigDecimal quantity,
			BigDecimal unitPrice, Boolean deleted, PriceCategory priceCategory) {
		super();
		this.id = id;
		this.sale = sale;
		this.productModels = productModels;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.deleted = deleted;
		this.priceCategory = priceCategory;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SaleModel getSale() {
		return sale;
	}

	public void setSale(SaleModel sale) {
		this.sale = sale;
	}

	public List<ProductModel> getProductModels() {
		return productModels;
	}

	public void setProductModels(List<ProductModel> productModels) {
		this.productModels = productModels;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public PriceCategory getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(PriceCategory priceCategory) {
		this.priceCategory = priceCategory;
	}

}
