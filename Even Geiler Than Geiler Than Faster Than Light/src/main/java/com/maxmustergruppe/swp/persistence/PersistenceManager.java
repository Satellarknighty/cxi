package com.maxmustergruppe.swp.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Persistence Manager of Hibernate.
 *
 * @author Hai Trinh
 */
public class PersistenceManager {
    private static final String PU_NAME = "GameDB";
    private static final EntityManagerFactory emFactory;
    static {
        emFactory = Persistence.createEntityManagerFactory(PU_NAME);
    }
    public static EntityManager getEntityManager(){
        return emFactory.createEntityManager();
    }
}
