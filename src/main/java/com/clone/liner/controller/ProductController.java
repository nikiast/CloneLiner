package com.clone.liner.controller;

import com.clone.liner.model.product.Bet;
import com.clone.liner.model.product.Product;
import com.clone.liner.model.user.User;
import com.clone.liner.service.BetService;
import com.clone.liner.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final BetService betService;

    public ProductController(ProductService productService, BetService betService) {
        this.productService = productService;
        this.betService = betService;
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("product") Product product) {
        return "product/addProduct";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal User user,
                         @ModelAttribute("product") Product product,
                         @ModelAttribute("bet") Bet bet,
                         @RequestParam(name = "role") String role,
                         @RequestParam("file") MultipartFile file) {
        productService.create(product, role, file);
        betService.create(bet, user, product);
        productService.product(product);
        betService.save(bet);
        return "redirect:/";
    }

    @GetMapping("{id}")
    public String priceForm(@PathVariable("id") Product product,
                            Model model) {
        model.addAttribute("betList", betService.findByProduct(product));
        model.addAttribute("product", product);
        return "product/priceForm";
    }

    @PostMapping("{id}/price")
    public String addNewPriceForm(@AuthenticationPrincipal User userFromSession,
                                  @PathVariable("id") Product product,
                                  @RequestParam Integer price) {
        betService.save(userFromSession, product, price);
        return "redirect:/product/{id}";
    }
}
