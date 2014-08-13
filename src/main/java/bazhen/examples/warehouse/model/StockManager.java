package bazhen.examples.warehouse.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 
 * @author ebazhenov
 *
 */
public class StockManager {

	public static List<StockPosition> getAllStockPositions() {
		EntityManager manager = Persistence.getEntityManager();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<StockPosition> query = builder.createQuery(StockPosition.class);
		query.from(StockPosition.class);
		TypedQuery<StockPosition> typedQuery = manager.createQuery(query);
		List<StockPosition> list = typedQuery.getResultList();
		manager.close();
		return list;
	}

	public static void removeStockPosition(final StockPosition position) {
		Persistence.execute(new Persistence.IAction<Void>() {
			public Void execute(EntityManager manager) {
				StockPosition managed = manager.merge(position);
				manager.remove(managed);
				return null;
			}
		});
		
	}

	public static StockPosition createStockPosition(final String materialName, final int amount) {
		return Persistence.execute(new Persistence.IAction<StockPosition>() {
			public StockPosition execute(EntityManager manager) {
				StockPosition entity = new StockPosition();
				Material material = MaterialManager.getMaterial(materialName);
				entity.setMaterial(material);
				entity.setAmount(amount);
				manager.persist(entity);
				return entity;
			}
		
		});
	}
	
}