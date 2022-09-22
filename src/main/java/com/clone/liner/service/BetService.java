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

    @Transactional
    public void save(Bet bet) {
        betRepository.save(bet);
    }

    @Transactional
    public void save(User userFromSession, Product product, Integer price) {
        Bet bet = new Bet(userFromSession, product, price, UserUtil.formatDateTimeNow());
        betRepository.save(bet);
    }

    public void create(Bet bet, User user, Product product) {
        bet.setUserId(user);
        bet.setProductId(product);
        bet.setCreatedTime(product.getCreatedTime());
    }

    public List<Bet> findByProductId(Product product) {
        return betRepository.findByProductId(product);
    }

    public Map<Product, Integer> getProductPriceMap() {
        List<Bet> betList = betRepository.findAll();
        Map<Product, Integer> priceProductMap = new HashMap<>();
        Product productFromBetList;
        for (Bet bet : betList) {
            productFromBetList = bet.getProductId();

            if (!priceProductMap.containsKey(productFromBetList)) {
                priceProductMap.put(productFromBetList, bet.getPrice());
            } else {
                Integer currentPrice = priceProductMap.get(productFromBetList);
                if (currentPrice > bet.getPrice()) {
                    priceProductMap.put(bet.getProductId(), bet.getPrice());
                }
            }
        }
        return priceProductMap;
    }


}
