package com.clone.liner.model.product;

import com.clone.liner.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Bet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;

    private Integer price;

    private String createdTime;

    protected Bet() {
    }

    public Bet(User userId, Product productId, Integer price, String createdTime) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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
                && Objects.equals(userId, bet.userId)
                && Objects.equals(productId, bet.productId)
                && Objects.equals(price, bet.price)
                && Objects.equals(createdTime, bet.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, price, createdTime);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", price=" + price +
                ", timeCreated=" + createdTime +
                '}';
    }
}
