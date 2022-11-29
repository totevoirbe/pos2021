package be.panidel.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class ProductPos10 {

	String id;
	String code;
	String nom;
	String description;
	String htmlKeyLabel;
	Integer prixachat;
	Integer prix;
	Integer tvaTakeAway;
	Integer tvaTakeOnPlace;
	String group;
	List<SubproductPos10> subproducts;

	@Override
	public String toString() {
		return "id[" + id + "]; code[" + code + "]; nom[" + nom + "]; description[" + description + "]; htmlKeyLabel["
				+ htmlKeyLabel + "]; prixachat[" + prixachat + "]; prix[" + prix + "]; tvaTakeAway[" + tvaTakeAway
				+ "]; tvaTakeOnPlace[" + tvaTakeOnPlace + "]; group[" + group + "]; subproducts[" + subproducts + "];";
	}

	public ProductPos10() {
		super();
	}

	public ProductPos10(String id, String code, String nom, String description, String htmlKeyLabel, Integer prixachat,
			Integer prix, Integer tvaTakeAway, Integer tvaTakeOnPlace, String group,
			List<SubproductPos10> subproducts) {
		super();
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.description = description;
		this.htmlKeyLabel = htmlKeyLabel;
		this.prixachat = prixachat;
		this.prix = prix;
		this.tvaTakeAway = tvaTakeAway;
		this.tvaTakeOnPlace = tvaTakeOnPlace;
		this.group = group;
		this.subproducts = subproducts;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProductPos10) {
			ProductPos10 product = (ProductPos10) obj;
			return code.equals(product.getCode());
		}
		return false;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}

	public String getHtmlKeyLabel() {
		return htmlKeyLabel;
	}

	public Integer getPrixachat() {
		return prixachat;
	}

	public Integer getPrix() {
		return prix;
	}

	public Integer getTvaTakeAway() {
		return tvaTakeAway;
	}

	public Integer getTvaTakeOnPlace() {
		return tvaTakeOnPlace;
	}

	public String getGroup() {
		return group;
	}

	public List<SubproductPos10> getSubproducts() {
		return subproducts;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHtmlKeyLabel(String htmlKeyLabel) {
		this.htmlKeyLabel = htmlKeyLabel;
	}

	public void setPrixachat(Integer prixachat) {
		this.prixachat = prixachat;
	}

	public void setPrix(Integer prix) {
		this.prix = prix;
	}

	public void setTvaTakeAway(Integer tvaTakeAway) {
		this.tvaTakeAway = tvaTakeAway;
	}

	public void setTvaTakeOnPlace(Integer tvaTakeOnPlace) {
		this.tvaTakeOnPlace = tvaTakeOnPlace;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setSubproducts(List<SubproductPos10> subproducts) {
		this.subproducts = subproducts;
	}

}
