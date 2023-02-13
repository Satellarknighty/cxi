package de.unibremen.swp.matti.persistence;

import de.unibremen.swp.matti.models.Category;
import de.unibremen.swp.matti.models.Flashcard;

import java.util.List;
import java.util.Optional;

public class CategoryRepository {
    /**
     * Diese Kategorie in den Persistenzkontext einfügen.
     *
     * @param category  Die eingefügte Kategorie.
     */
    public void save(final Category category){
        try (final var em = PersistenceManager.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        }
    }
    /**
     * Diese Kategorie in den Persistenzkontext finden.
     *
     * @param name  Die eingefügte Kategorie.
     *
     * @return Die Kategorie im Form von Optional. Gibt ein leeren
     * Optional zurück, wenn das Objekt nicht gefunden wird.
     */
    public Optional<Category> find(final String name){
        try (final var em = PersistenceManager.getEntityManager()) {
            return Optional.ofNullable(em.find(Category.class, name));
        }
    }
    /**
     * Gibt alle Kategorien in den Persistenzkontext zurück.
     *
     * @return  alle Kategorien
     */
    @SuppressWarnings("unchecked")
    public List<Category> findAll(){
        try (final var em = PersistenceManager.getEntityManager()) {
            return (List<Category>) em.createQuery("SELECT categories FROM Category categories").getResultList();
        }
    }
    /**
     * Diese Kategorie in den Persistenzkontext aktualisieren.
     *
     * @param category  Die aktualisierte Kategorie.
     */
    public void update(Category category){
        try (final var em = PersistenceManager.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(category);
            em.getTransaction().commit();
        }
    }
    /**
     * Diese Kategorie aus dem Persistenzkontext entfernen.
     *
     * @param name  Der Name der Kategorie.
     */
    public void deleteByName(String name) {
        try (final var em = PersistenceManager.getEntityManager()){
            Category toBeDeleted = em.find(Category.class, name);
            if (toBeDeleted != null){
                em.getTransaction().begin();
                em.remove(toBeDeleted);
                em.getTransaction().commit();
            }
        }
    }
}
