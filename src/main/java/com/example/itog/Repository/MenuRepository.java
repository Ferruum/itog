package com.example.itog.Repository;

import com.example.itog.models.Cuisine;
import com.example.itog.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findAllBydishName(String dishName);
}
