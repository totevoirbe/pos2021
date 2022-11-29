package be.panidel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import be.panidel.model.ItemModel;
import be.panidel.model.SaleModel;

class SaleDao extends GenericDaoImpl<SaleModel> {

	@Override
	public SaleModel create(SaleModel saleModel, EntityManager em) {
		saleModel = super.create(saleModel, em);
		saleModel.setItems(new ArrayList<>());
		return saleModel;
	}

	public void joinItem(SaleModel saleModel, ItemModel itemModel, EntityManager em) {

		if (saleModel.getItems() == null) {
			saleModel.setItems(new ArrayList<>());
		}
		saleModel.getItems().add(itemModel);

		update(saleModel, em);

	}

	@Override
	public List<SaleModel> findAll(EntityManager em) {

		return em.createNamedQuery("pos.sale.all", SaleModel.class).getResultList();

	}

}