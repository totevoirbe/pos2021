package be.panidel.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class PageAfficheDef {

	List<String> tr;
	String name;

	@Override
	public String toString() {
		String desc = "";

		for (String id : tr) {
			desc += ";id[" + id + "]";
		}

		return desc;
	}

	public List<String> getTr() {
		return tr;
	}

	public void setTr(List<String> tr) {
		this.tr = tr;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
