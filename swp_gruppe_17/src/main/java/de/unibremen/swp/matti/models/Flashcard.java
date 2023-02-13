package de.unibremen.swp.matti.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Flashcard implements Serializable {
    @Id
    @NonNull
    private String name;
    /**
     * Die Vorderseite.
     */
    @NonNull
    @Setter
    private String front;
    /**
     * Die Rückseite.
     */
    @NonNull
    @Setter
    private String back;
    /**
     * Die Liste aller Karteikarten, die zu dieser Karteikarte verlinkt sind.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private final List<Flashcard> linked = new ArrayList<>();
    @Setter
    private String keywords = "";

    /**
     * Erzeugt eine neue Karteikarte anhand ihrer Parameter.
     * @param name Der Name der Karteikarte, darf nicht null sein.
     * @param front Der Text der Karteikartenvorderseite, darf nicht null sein.
     * @param back Der Text der Karteikartenrückseite, darf nicht null sein.
     */
    public Flashcard(@NonNull String name, @NonNull String front, @NonNull String back) {
        this.name = name;
        this.front = front;
        this.back = back;
    }

    /**
     * Verlinkt eine Karteikarte zu dieser Karteikarte.
     * @param linker Die zu verlinkende Karteikarte.
     */
    public void link(final Flashcard linker){
        linked.add(linker);
    }

    /**
     * Überschreibt die equals Funktion um eine Implementierung für Karteikarten zu ermöglichen.
     * @param o Das zu vergleichende Objekt.
     * @return True falls sie gleich sind, false wenn nicht.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(name, flashcard.name);
    }

    /**
     * Überschriebt die hashCode Funktion zur ermöglichten Implementation.
     * @return Int Wert zur Identifikation.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
