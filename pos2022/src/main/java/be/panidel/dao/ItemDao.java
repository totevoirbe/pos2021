package be.panidel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.model.ItemModel;
import be.panidel.model.ProductModel;

class ItemDao extends GenericDaoImpl<ItemModel> {

	private final static Logger LOG = LoggerFactory.getLogger(ItemDao.class);

	@Override
	public ItemModel create(ItemModel itemModel, EntityManager em) {
		itemModel = super.create(itemModel, em);
		itemModel.setProductModels(new ArrayList<>());
		return itemModel;
	}

	public boolean joinProduct(ItemModel itemModel, ProductModel productModel, EntityManager em) {

		if (itemModel.getProductModels() == null) {
			itemModel.setProductModels(new ArrayList<>());
		}
		itemModel.getProductModels().add(productModel);
		try {
			update(itemModel, em);
		} catch (EntityExistsException e) {
			LOG.error("" + productModel, e);
			return false;
		}
		return true;
	}

	@Override
	public List<ItemModel> findAll(EntityManager em) {

		return em.createNamedQuery("pos.item.all", ItemModel.class).getResultList();

	}

}
