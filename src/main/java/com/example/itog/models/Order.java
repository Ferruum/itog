package com.example.itog.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @NotNull
    private int quantity;

    public Order(Long orderId, Reservation reservation, Menu menu, int quantity) {
        this.orderId = orderId;
        this.reservation = reservation;
        this.menu = menu;
        this.quantity = quantity;
    }

    public Order() {

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Other fields and methods

}

