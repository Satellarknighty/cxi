package de.unibremen.swp.matti.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Category implements Serializable {
    @Id
    @NonNull
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private final List<Flashcard> flashcards = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private final List<Category> subCategories = new ArrayList<>();

    /**
     * Überschreibt die equals Funktion um eine Implementierung für Kategorien zu ermöglichen.
     * @param o Das zu vergleichende Objekt.
     * @return True falls sie gleich sind, false falls nicht.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    /**
     * Überschreibt die hashCode Funktion um eine Implementierung für Kategorien zu ermöglichen.
     * @return Int Wert zur Identifikation.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
