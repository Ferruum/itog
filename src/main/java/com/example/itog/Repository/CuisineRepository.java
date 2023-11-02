package com.example.itog.Repository;

import com.example.itog.models.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuisineRepository extends JpaRepository<Cuisine, Integer> {
    List<Cuisine> findAllBycuisineType(String cuisineType);
}
