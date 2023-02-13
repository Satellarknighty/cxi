package de.unibremen.swp.matti.persistence;

import de.unibremen.swp.matti.models.Flashcard;

import java.util.List;
import java.util.Optional;

public class FlashcardRepository {
    /**
     * Diese Karteikarte in den Persistenzkontext einfügen.
     *
     * @param card  Die eingefügte Karte.
     */
    public void save(final Flashcard card){
        try (final var em = PersistenceManager.getEntityManager()){
            em.getTransaction().begin();
            em.persist(card);
            em.getTransaction().commit();
        }
    }
    /**
     * Diese Karteikarte in den Persistenzkontext finden.
     *
     * @param name  Die eingefügte Karte.
     *
     * @return Der Kasten im Form von Optional. Gibt ein leeren
     * Optional zurück, wenn das Objekt nicht gefunden wird.
     */
    public Optional<Flashcard> find(final String name){
        try (final var em = PersistenceManager.getEntityManager()){
            return Optional.ofNullable(em.find(Flashcard.class, name));
        }
    }
    /**
     * Gibt alle Karteikarten in den Persistenzkontext zurück.
     *
     * @return  alle Karteikarten
     */
    @SuppressWarnings("unchecked")
    public List<Flashcard> findAll(){
        try (final var em = PersistenceManager.getEntityManager()){
            return (List<Flashcard>) em.createQuery("SELECT card FROM Flashcard card").getResultList();
        }
    }
    /**
     * Diese Karteikarte in den Persistenzkontext aktualisieren.
     *
     * @param card  Die aktualisierte Karte.
     */
    public void update(Flashcard card){
        try (final var em = PersistenceManager.getEntityManager()){
            em.getTransaction().begin();
            em.merge(card);
            em.getTransaction().commit();
        }
    }
    /**
     * Diese Karteikarte aus dem Persistenzkontext entfernen.
     *
     * @param name    Der Name der Karte.
     */
    public void deleteByName(String name){
        try (final var em = PersistenceManager.getEntityManager()){
            Flashcard toBeDeleted = em.find(Flashcard.class, name);
            if (toBeDeleted != null) {
                em.getTransaction().begin();
                em.remove(toBeDeleted);
                em.getTransaction().commit();
            }
        }
    }
}
