package com.example.itog.Repository;

import com.example.itog.models.Cuisine;
import com.example.itog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByfirstName(String firstName);
}
