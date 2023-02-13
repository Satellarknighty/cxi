package de.unibremen.swp.matti.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class CardBeingLearned implements Serializable {
    @Id
    private final UUID id = UUID.randomUUID();
    @ManyToOne
    private Flashcard card;
    @Setter
    private Compartment currentCompartment;

    /**
     * Definiert eine Karteikarte als eine zu lernende Karte.
     * @param card Die zu lernende Karte.
     */
    public CardBeingLearned(Flashcard card) {
        this.card = card;
    }

    /**
     * Überschreibt die equals Funktion um Karteikarten miteinander zu vergleichen.
     * @param o Das zu vergleichende Objekt.
     * @return false wenn die Objekte ungleich sind, true falls sie gleich sind.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CardBeingLearned that = (CardBeingLearned) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Überschreibt die hashCode Funktionn um implementierung zu ermöglichen.
     * @return int zur Identifikation.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
