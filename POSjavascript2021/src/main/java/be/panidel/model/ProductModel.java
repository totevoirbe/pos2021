package be.panidel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "product")

@NamedQueries({ @NamedQuery(name = "pos.product.all", query = "SELECT e FROM ProductModel e") })
public class ProductModel extends OperationModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "PRODUCT_ID")
	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private ItemModel item;

	private String label;
	private String ticketLabel;
	private String code;
	private String name;
	private String htmlKeyLabel;
	private String type;
	private String image;
	private String codeTva;
	private Integer mini;
	private Integer normal;
	private Integer geant;
	private Integer fitmini;
	private Integer fitnormal;
	private Integer fitgeant;
	private String webDetail;
	private String afficheDetail;

	@Override
	public String toString() {
		return "id[" + id + "]; operationModel[" + super.toString() + "];code[" + getCode() + "]; name[" + name
				+ "]; htmlKeyLabel[" + htmlKeyLabel + "]; type[" + type + "]; image[" + image + "]; codeTva[" + codeTva
				+ "]; mini[" + mini + "]; normal[" + normal + "]; geant[" + geant + "]; fitmini[" + fitmini
				+ "]; fitnormal[" + fitnormal + "]; fitgeant[" + fitgeant + "]; webDetail[" + webDetail
				+ "]; afficheDetail[" + afficheDetail + "]";

	}

	public ProductModel() {
	}

	public ProductModel(long id, ItemModel item, String label, String ticketLabel, String code, String name,
			String htmlKeyLabel, String type, String image, String codeTva, Integer mini, Integer normal, Integer geant,
			Integer fitmini, Integer fitnormal, Integer fitgeant, String webDetail, String afficheDetail) {
		super();
		this.id = id;
		this.item = item;
		this.label = label;
		this.ticketLabel = ticketLabel;
		this.code = code;
		this.name = name;
		this.htmlKeyLabel = htmlKeyLabel;
		this.type = type;
		this.image = image;
		this.codeTva = codeTva;
		this.mini = mini;
		this.normal = normal;
		this.geant = geant;
		this.fitmini = fitmini;
		this.fitnormal = fitnormal;
		this.fitgeant = fitgeant;
		this.webDetail = webDetail;
		this.afficheDetail = afficheDetail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ItemModel getItem() {
		return item;
	}

	public void setItem(ItemModel item) {
		this.item = item;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTicketLabel() {
		return ticketLabel;
	}

	public void setTicketLabel(String ticketLabel) {
		this.ticketLabel = ticketLabel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHtmlKeyLabel() {
		return htmlKeyLabel;
	}

	public void setHtmlKeyLabel(String htmlKeyLabel) {
		this.htmlKeyLabel = htmlKeyLabel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCodeTva() {
		return codeTva;
	}

	public void setCodeTva(String codeTva) {
		this.codeTva = codeTva;
	}

	public Integer getMini() {
		return mini;
	}

	public void setMini(Integer mini) {
		this.mini = mini;
	}

	public Integer getNormal() {
		return normal;
	}

	public void setNormal(Integer normal) {
		this.normal = normal;
	}

	public Integer getGeant() {
		return geant;
	}

	public void setGeant(Integer geant) {
		this.geant = geant;
	}

	public Integer getFitmini() {
		return fitmini;
	}

	public void setFitmini(Integer fitmini) {
		this.fitmini = fitmini;
	}

	public Integer getFitnormal() {
		return fitnormal;
	}

	public void setFitnormal(Integer fitnormal) {
		this.fitnormal = fitnormal;
	}

	public Integer getFitgeant() {
		return fitgeant;
	}

	public void setFitgeant(Integer fitgeant) {
		this.fitgeant = fitgeant;
	}

	public String getWebDetail() {
		return webDetail;
	}

	public void setWebDetail(String webDetail) {
		this.webDetail = webDetail;
	}

	public String getAfficheDetail() {
		return afficheDetail;
	}

	public void setAfficheDetail(String afficheDetail) {
		this.afficheDetail = afficheDetail;
	}

}
