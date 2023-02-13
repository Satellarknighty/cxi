package de.unibremen.swp.matti.persistence;

import de.unibremen.swp.matti.models.CardBox;

import java.util.List;
import java.util.Optional;

/**
 * Repository für CardBox.
 */
public class CardBoxRepository {
    /**
     * Dieser Karteikasten in den Persistenzkontext einfügen.
     *
     * @param box  Der eingefügte Kasten.
     */
    public void save(CardBox box){
        try (var em = PersistenceManager.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(box);
            em.getTransaction().commit();
        }
    }
    /**
     * Dieser Karteikasten in den Persistenzkontext finden.
     *
     * @param id  Der Name des Kastens.
     *
     * @return Der Kasten im Form von Optional. Gibt ein leeren
     * Optional zurück, wenn das Objekt nicht gefunden wird.
     */
    public Optional<CardBox> find(String id){
        try (var em = PersistenceManager.getEntityManager()) {
            return Optional.ofNullable(em.find(CardBox.class, id));
        }
    }
    /**
     * Gibt alle Karteikästen in den Persistenzkontext zurück.
     *
     * @return  alle Karteikästen
     */
    @SuppressWarnings("unchecked")
    public List<CardBox> findAll(){
        try (final var em = PersistenceManager.getEntityManager()) {
            return (List<CardBox>) em.createQuery("SELECT box FROM CardBox box").getResultList();
        }
    }

    /**
     * Dieser Karteikasten in den Persistenzkontext aktualisieren.
     *
     * @param box   Der aktualisierte Kasten.
     */
    public void update(CardBox box){
        try (final var em = PersistenceManager.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(box);
            em.getTransaction().commit();
        }
    }

    /**
     * Dieser Kasten aus dem Persistenzkontext entfernen.
     *
     * @param id    Der Name des Kastens.
     */
    public void deleteByName(String id){
        try (final var em = PersistenceManager.getEntityManager()){
            CardBox toBeDeleted = em.find(CardBox.class, id);
            em.getTransaction().begin();
            em.remove(toBeDeleted);
            em.getTransaction().commit();
        }
    }
}
