package de.unibremen.swp.matti.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class CardBox implements Serializable {
    /**
     * Definiert eine int als 1 für den Startwert der Tageszählung.
     */
    public static final int FIRST_DAY = 1;
    /**
     * Der Name des Karteikastens.
     */
    @NonNull
    @Id
    private String name;
    @Setter
    private LearnSystem system;
    @ManyToMany(fetch = FetchType.EAGER)
    private final List<Category> categories = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private final List<CardBeingLearned> toBeLearned = new ArrayList<>();
    @Setter
    private int dayLearned = FIRST_DAY;

    /**
     * Erhöht die Zählung der gelernten Tage um 1.
     */
    public void incrementDayLearned(){
        dayLearned++;
    }

    /**
     * Überschreibt die equals Funktion um die Implementierung für Karteikästen zu ermöglichen.
     * @param o Das zu vergleichende Objekt
     * @return True wenn sie gleich sind, false falls nicht.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CardBox cardBox = (CardBox) o;
        return Objects.equals(name, cardBox.name);
    }

    /**
     * Überschreibt die hashCode Funktion um eine Implementierung für Karteikästen zu ermöglichen.
     * @return int Wert zur Identifikation.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
