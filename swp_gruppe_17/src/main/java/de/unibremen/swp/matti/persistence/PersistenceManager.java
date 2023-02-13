package de.unibremen.swp.matti.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceManager {
    private static final String PU_NAME = "MattiDB";
    private static final EntityManagerFactory emFactory;
    static {
        emFactory = Persistence.createEntityManagerFactory(PU_NAME);
    }
    public static EntityManager getEntityManager(){
        return emFactory.createEntityManager();
    }
}
