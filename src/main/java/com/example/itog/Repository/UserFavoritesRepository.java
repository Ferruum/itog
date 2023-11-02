package com.example.itog.Repository;

import com.example.itog.models.UserFavorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoritesRepository extends JpaRepository<UserFavorites, Integer> {
}
