package com.green.onezo.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutrientRepository extends JpaRepository<Nutrient,Long> {
     List<Nutrient> findAll();

     Optional<Nutrient> findById(Long id);

}