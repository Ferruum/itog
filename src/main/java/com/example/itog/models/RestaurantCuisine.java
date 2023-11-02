package com.example.itog.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "restaurant_cuisines")
public class RestaurantCuisine {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

    public RestaurantCuisine(Long id, Restaurant restaurant, Cuisine cuisine) {
        this.id = id;
        this.restaurant = restaurant;
        this.cuisine = cuisine;
    }

    public RestaurantCuisine() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }
}
