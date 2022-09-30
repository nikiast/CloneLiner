package com.clone.liner.model.product;

import com.clone.liner.model.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bet{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer price;

    private String createdTime;

    protected Bet() {
    }

    public Bet(User user, Product product, Integer price, String createdTime) {
        this.user = user;
        this.product = product;
        this.price = price;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String timeCreated) {
        this.createdTime = timeCreated;
    }

    public int compareTo(Bet o) {
        return this.getPrice().compareTo(o.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(id, bet.id)
                && Objects.equals(user, bet.user)
                && Objects.equals(product, bet.product)
                && Objects.equals(price, bet.price)
                && Objects.equals(createdTime, bet.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, product, price, createdTime);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                ", price=" + price +
                ", timeCreated=" + createdTime +
                '}';
    }
}
