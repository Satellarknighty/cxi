package com.maxmustergruppe.swp.persistence;

import com.maxmustergruppe.swp.persistence.entity.SpaceshipEntity;

import java.util.Optional;

/**
 * @author Hai Trinh
 */
public class SpaceshipEntityRepo {
    /**
     * Persist a SpaceshipEntity into the Database.
     *
     * @param spaceshipEntity   with saveGameNo as the id.
     */
    public static void save(SpaceshipEntity spaceshipEntity){
        try (var em = PersistenceManager.getEntityManager()){
            em.getTransaction().begin();
            em.persist(spaceshipEntity);
            em.getTransaction().commit();
        }
    }

    /**
     * Find a SpaceshipEntity.
     *
     * @param saveGameNo    The id.
     * @return  The entity in the form of an Optional if found. Empty Optional otherwise.
     */
    public static Optional<SpaceshipEntity> find(Integer saveGameNo){
        try (var em = PersistenceManager.getEntityManager()){
            return Optional.ofNullable(em.find(SpaceshipEntity.class, saveGameNo));
        }
    }

    /**
     * Delete a SpaceshipEntity using its id. Nothing happens if the entity doesn't exist.
     * @param saveGameNo    The id.
     */
    public static void delete(Integer saveGameNo){
        try (var em = PersistenceManager.getEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM CanonEntity c1 WHERE c1.spaceshipEntity.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.createQuery("DELETE FROM CrewmateEntity c1 WHERE c1.spaceshipEntity.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.createQuery("DELETE FROM EngineRoomEntity e1 WHERE e1.spaceshipEntity.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.createQuery("DELETE FROM GunEntity g1 WHERE g1.spaceshipEntity.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.createQuery("DELETE FROM LaserEntity l1 WHERE l1.spaceshipEntity.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.createQuery("DELETE FROM ShieldRoomEntity s1 WHERE s1.spaceshipEntity.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.createQuery("DELETE FROM WeaponRoomEntity w1 WHERE w1.spaceshipEntity.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.createQuery("DELETE FROM SpaceshipEntity s1 WHERE s1.saveGameNo = :id")
                    .setParameter("id", saveGameNo)
                    .executeUpdate();
            em.getTransaction().commit();
        }
    }

    /**
     * Check if a SpaceshipEntity exists in the Database.
     *
     * @param saveGameNo    The id.
     * @return  True sometimes, false most of the time.
     */
    public static boolean exists(Integer saveGameNo){
        try (var em = PersistenceManager.getEntityManager()){
            return (boolean) em.createQuery("SELECT (count(s1) > 0) FROM SpaceshipEntity s1 WHERE s1.saveGameNo = :no")
                    .setParameter("no", saveGameNo).getSingleResult();
        }
    }
}
