package com.example.itog.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "cuisines")
public class Cuisine {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuisineId;
    @NotBlank(message = "Cuisine type is required")
    @NotNull
    private String cuisineType;

    public Cuisine(Long cuisineId, String cuisineType) {
        this.cuisineId = cuisineId;
        this.cuisineType = cuisineType;
    }

    public Cuisine() {

    }

    public Long getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(Long cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }
}

