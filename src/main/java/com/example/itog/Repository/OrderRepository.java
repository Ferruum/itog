package com.example.itog.Repository;

import com.example.itog.models.Cuisine;
import com.example.itog.models.Order;
import com.example.itog.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
