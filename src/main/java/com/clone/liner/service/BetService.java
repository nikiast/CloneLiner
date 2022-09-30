package com.clone.liner.service;

import com.clone.liner.model.product.Bet;
import com.clone.liner.model.product.Product;
import com.clone.liner.model.user.User;
import com.clone.liner.repository.BetRepository;
import com.clone.liner.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BetService {
    private final BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public void save(Bet bet) {
        betRepository.save(bet);
    }

    @Transactional
    public void save(User userFromSession, Product product, Integer price) {
        Bet bet = new Bet(userFromSession, product, price, UserUtil.formatDateTimeNow());
        betRepository.save(bet);
    }

    public void create(Bet bet, User user, Product product) {
        bet.setUser(user);
        bet.setProduct(product);
        bet.setCreatedTime(product.getCreatedTime());
    }

    public List<Bet> findByProduct(Product product) {
        return betRepository.findByProduct(product);
    }

    public Map<Product, Integer> getProductPriceMap() {
        Map<Product, Integer> priceProductMap = new HashMap<>();
        betRepository.findAll().forEach(bet ->
            priceProductMap.merge(bet.getProduct(), bet.getPrice(), (oldVal, newVal) -> {
                if (!priceProductMap.containsKey(bet.getProduct())) return newVal;
                else return oldVal > newVal ? newVal : oldVal;
            }));
        return priceProductMap;
    }
}
