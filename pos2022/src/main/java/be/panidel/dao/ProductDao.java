package be.panidel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import be.panidel.model.ProductModel;

class ProductDao extends GenericDaoImpl<ProductModel> {

	@Override
	public List<ProductModel> findAll(EntityManager em) {
		return em.createNamedQuery("pos.product.all", ProductModel.class).getResultList();
	}

}
