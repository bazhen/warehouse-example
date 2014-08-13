package bazhen.examples.warehouse.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * 
 * @author ebazhenov
 *
 */
class Persistence {

	static interface IAction<T> {
		
		T execute(EntityManager manager);
		
	}

	static EntityManager getEntityManager() {
		EntityManagerFactory factory = javax.persistence.Persistence.createEntityManagerFactory("model");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

	static <T> T execute(IAction<T> action) {
		EntityManager manager  = getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		T execute = action.execute(manager);
		transaction.commit();
		return execute;
	}
	
}