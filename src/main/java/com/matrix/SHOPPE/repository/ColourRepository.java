package com.matrix.SHOPPE.repository;

import com.matrix.SHOPPE.model.entity.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Integer> {
    Optional<Colour> findByColourName(String colourName);
}
