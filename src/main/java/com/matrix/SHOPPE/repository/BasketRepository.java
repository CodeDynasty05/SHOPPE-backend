package com.matrix.SHOPPE.repository;

import com.matrix.SHOPPE.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findByUserId(Integer userId);
}
