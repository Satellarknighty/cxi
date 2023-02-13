package de.unibremen.swp.matti.persistence;

import de.unibremen.swp.matti.models.CardBeingLearned;
import de.unibremen.swp.matti.models.CardBox;

import java.util.UUID;

/**
 * Repository für CardBeingLearned
 */
public class CardBeingLearnedRepository {
    /**
     * Diese CardBeingLearned in den Persistenzkontext einfügen.
     *
     * @param card  Die eingefügte Karte.
     */
    public void save(final CardBeingLearned card){
        try (final var em = PersistenceManager.getEntityManager()){
            em.getTransaction().begin();
            em.persist(card);
            em.getTransaction().commit();
        }
    }

    /**
     * Diese CardBeingLearned in den Persistenzkontext aktualisieren.
     *
     * @param card  Die aktualisierte Karte.
     */
    public void update(CardBeingLearned card){
        try (final var em = PersistenceManager.getEntityManager()){
            em.getTransaction().begin();
            em.merge(card);
            em.getTransaction().commit();
        }
    }

    /**
     * Die Karte aus einem Karteikasten löschen und die Änderungen im Persistenzkontext
     * speichern.
     *
     * @param cardId    Die UUID der Karte.
     * @param boxName   Der Name des Karteikastens.
     */
    public void deleteLearningResources(UUID cardId, String boxName) {
        try (var em = PersistenceManager.getEntityManager()) {
            CardBeingLearned toBeDeleted = em.find(CardBeingLearned.class, cardId);
            if (toBeDeleted != null){
                em.getTransaction().begin();
                CardBox toBeUpdated = em.find(CardBox.class, boxName);
                em.remove(toBeUpdated);
                em.remove(toBeDeleted);
                toBeUpdated.getToBeLearned().remove(toBeDeleted);
                toBeUpdated.setSystem(null);
                toBeUpdated.setDayLearned(CardBox.FIRST_DAY);
                em.persist(toBeUpdated);
                em.getTransaction().commit();
            }
        }
    }
}
