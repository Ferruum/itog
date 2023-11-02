package com.example.itog.Repository;

import com.example.itog.models.Cuisine;
import com.example.itog.models.Restaurant;
import com.example.itog.models.RestaurantCuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantCuisineRepository extends JpaRepository<RestaurantCuisine, Integer> {

}
