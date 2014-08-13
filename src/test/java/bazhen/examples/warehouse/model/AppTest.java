package bazhen.examples.warehouse.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	private EntityManager manager;
	private EntityTransaction transaction;
	private EntityManagerFactory factory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		manager = createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
	}

	@Override
	protected void tearDown() throws Exception {
		transaction.commit();
		manager.close();
		factory.close();
		super.tearDown();
	}

	public void test() {
		Material material = new Material();
		manager.persist(material);
		assertNotNull(material.getId());
		Material found = manager.find(Material.class, material.getId());
		assertEquals(material, found);
	}

	private EntityManager createEntityManager() {
		factory = Persistence.createEntityManagerFactory("model");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

	public void test2() {
		Material material = new Material();
		StockPosition position = new StockPosition();
		position.setMaterial(material);
		manager.persist(material);
		manager.persist(position);
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Material> query = builder.createQuery(Material.class);
		Root<StockPosition> root = query.from(StockPosition.class);
	}

}
