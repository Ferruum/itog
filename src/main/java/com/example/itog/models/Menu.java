package com.example.itog.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @NotNull
    private String dishName;
    @NotNull
    private double price;

    public Menu(Long menuId, Restaurant restaurant, String dishName, double price) {
        this.menuId = menuId;
        this.restaurant = restaurant;
        this.dishName = dishName;
        this.price = price;
    }

    public Menu() {

    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

