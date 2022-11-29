package be.panidel.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author franzph
 * 
 *         For pos 1.0 co,patibility
 * 
 */

@XmlRootElement(name = "root")
public class RootAfficheDef {

	private List<PageAfficheDef> page;

	@Override
	public String toString() {

		String desc = "";

		for (PageAfficheDef pageAfficheDef : page) {
			desc += ";page[" + pageAfficheDef + "]";
		}

		return desc;

	}

	public List<PageAfficheDef> getPage() {
		return page;
	}

	public void setPage(List<PageAfficheDef> page) {
		this.page = page;
	}

}
