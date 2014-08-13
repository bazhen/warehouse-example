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
public final class MaterialManager {
	
	private MaterialManager() {
	}
	
	public static Material createMaterial(final String name) {
		return Persistence.execute(new Persistence.IAction<Material>() {
			public Material execute(EntityManager manager) {
				Material entity = new Material();
				entity.setName(name);
				manager.persist(entity);
				return entity;
			}
		});
	}

	public static void removeMaterial(final Material material) {
		Persistence.execute(new Persistence.IAction<Void>() {
			public Void execute(EntityManager manager) {
				Material managed = manager.merge(material);
				manager.remove(managed);
				return null;
			}
		});
	}

	public static List<Material> getAllMaterials() {
		EntityManager manager = Persistence.getEntityManager();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Material> query = builder.createQuery(Material.class);
		query.from(Material.class);
		TypedQuery<Material> typedQuery = manager.createQuery(query);
		return typedQuery.getResultList();
	}

	public static Material getMaterial(String materialName) {
		EntityManager manager = Persistence.getEntityManager();
		TypedQuery<Material> query = manager.createNamedQuery("findMaterialByName", Material.class);
		query.setParameter("name", materialName);
		return query.getSingleResult();
	}
	
}