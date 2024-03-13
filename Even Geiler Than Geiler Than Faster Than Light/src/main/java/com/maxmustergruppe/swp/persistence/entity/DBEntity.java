package com.maxmustergruppe.swp.persistence.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * An abstract class for each and every model. It only serves to give objects of said model an id, which will be
 * auto-generated by H2.
 *
 * @author Hai Trinh
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DBEntity dbEntity = (DBEntity) o;
        return id != null && Objects.equals(id, dbEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
