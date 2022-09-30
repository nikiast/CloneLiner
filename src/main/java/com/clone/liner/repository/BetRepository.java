package com.clone.liner.repository;

import com.clone.liner.model.product.Bet;
import com.clone.liner.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByProduct(Product product);
}
