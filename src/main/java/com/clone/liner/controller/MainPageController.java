package com.clone.liner.controller;

import com.clone.liner.service.BetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    private final BetService betService;

    public MainPageController(BetService betService) {
        this.betService = betService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("priceProductMap", betService.getProductPriceMap());
        return "main";
    }
}
