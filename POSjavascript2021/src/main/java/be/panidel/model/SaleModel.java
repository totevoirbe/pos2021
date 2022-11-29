package be.panidel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "sale")
@NamedQueries({ @NamedQuery(name = "pos.sale.all", query = "SELECT e FROM SaleModel e") })

public class SaleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SALE_ID")
	private long id;

	@Basic
	private SaleState status;
	@Basic
	private Date openDate;
	@Basic
	private Date endDate;
	@Basic
	private String identifier;
	@Basic
	private String source;

	@Basic
	private BigDecimal saleTotal;
	@Basic
	private BigDecimal payTotal;
	@Basic
	private BigDecimal nbArticles;
	@Basic
	private BigDecimal remainValue;
	@Basic
	private BigDecimal paySubTotal;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sale")
	private List<ItemModel> items;

	@Override
	public String toString() {

		StringBuffer description = new StringBuffer("id[" + id + "]; operationModel[");

		description.append("id[" + id + "]; ");
		description.append("status[" + status + "]; ");
		description.append("openDate[" + openDate + "]; ");
		description.append("endDate[" + endDate + "]; ");
		description.append("identifier[" + identifier + "]; ");
		description.append("source[" + source + "]; ");
		description.append("saleTotal[" + saleTotal + "]; ");
		description.append("payTotal[" + payTotal + "]; ");
		description.append("nbArticles[" + nbArticles + "]; ");
		description.append("remainValue[" + remainValue + "]; ");
		description.append("paySubTotal[" + paySubTotal + "]; ");

		if (items != null) {
			for (ItemModel itemModel : items) {
				description.append("item[" + itemModel + "]; ");
			}
		} else {
			description.append("items[NULL]; ");
		}

		return description.toString();
	}

	public SaleModel() {
	}

	public SaleModel(long id, SaleState status, Date openDate, Date endDate, String identifier, String source,
			BigDecimal saleTotal, BigDecimal payTotal, BigDecimal nbArticles, BigDecimal remainValue,
			BigDecimal paySubTotal, List<ItemModel> items) {
		super();
		this.id = id;
		this.status = status;
		this.openDate = openDate;
		this.endDate = endDate;
		this.identifier = identifier;
		this.source = source;
		this.saleTotal = saleTotal;
		this.payTotal = payTotal;
		this.nbArticles = nbArticles;
		this.remainValue = remainValue;
		this.paySubTotal = paySubTotal;
		this.items = items;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SaleState getStatus() {
		return status;
	}

	public void setStatus(SaleState status) {
		this.status = status;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(BigDecimal saleTotal) {
		this.saleTotal = saleTotal;
	}

	public BigDecimal getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(BigDecimal payTotal) {
		this.payTotal = payTotal;
	}

	public BigDecimal getNbArticles() {
		return nbArticles;
	}

	public void setNbArticles(BigDecimal nbArticles) {
		this.nbArticles = nbArticles;
	}

	public BigDecimal getRemainValue() {
		return remainValue;
	}

	public void setRemainValue(BigDecimal remainValue) {
		this.remainValue = remainValue;
	}

	public BigDecimal getPaySubTotal() {
		return paySubTotal;
	}

	public void setPaySubTotal(BigDecimal paySubTotal) {
		this.paySubTotal = paySubTotal;
	}

	public List<ItemModel> getItems() {
		return items;
	}

	public void setItems(List<ItemModel> items) {
		this.items = items;
	}

}
