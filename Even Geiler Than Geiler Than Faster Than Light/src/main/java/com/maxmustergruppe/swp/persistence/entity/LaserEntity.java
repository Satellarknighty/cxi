package com.maxmustergruppe.swp.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the weapon Laser in the database.
 *
 * @author Hai Trinh
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Laser")
public class LaserEntity extends WeaponEntity {
}
