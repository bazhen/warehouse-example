package bazhen.examples.warehouse.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bazhen.examples.warehouse.model.Persistence.IAction;

/**
 * 
 * @author ebazhenov
 *
 */
public class ProductPartManager {

	public static void removeProductPart(final ProductPart productPart) {
		Persistence.execute(new IAction<Void>() {

			@Override
			public Void execute(EntityManager manager) {
				manager.merge(productPart);
				manager.remove(productPart);
				return null;
			}
		});

	}

	public static ProductPart createProductPart(final String name, final String material, final int quantity) {
		return Persistence.execute(new IAction<ProductPart>() {

			@Override
			public ProductPart execute(EntityManager manager) {
				ProductPart productPart = new ProductPart();
				productPart.setName(name);
				Material materialEntity = MaterialManager.getMaterial(material);
				productPart.setMaterial(materialEntity);
				productPart.setQuantity(quantity);
				manager.persist(productPart);
				return productPart;
			}

		});
	}

	public static List<ProductPart> getAllProductParts() {
		EntityManager manager = Persistence.getEntityManager();
		TypedQuery<ProductPart> query = manager.createNamedQuery("allProductParts", ProductPart.class);
		return query.getResultList();
	}

}
