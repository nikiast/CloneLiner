package com.clone.liner.controller;

import com.clone.liner.model.user.User;
import com.clone.liner.service.UserService;
import com.clone.liner.util.UserValidator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;
    private User user;

    private static final String REDIRECT_ADMIN_ID = "redirect:/admin/{id}";

    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/admin/userList";
    }

    @PostMapping("username")
    public String filterByUsername(@RequestParam String username, Model model) {
        model.addAttribute("users", userService.getOneOrAll(username));
        return "user/admin/userList";
    }

    @GetMapping("{id}")
    public String userEditForm(@PathVariable("id") User user, Model model) {
        this.user = user;
        model.addAttribute("user", user);
        model.addAttribute("editingUserByAdmin", user);
        if (Boolean.TRUE.equals(user.isActive())) {
            model.addAttribute("Active", true);
        } else {
            model.addAttribute("notActive", true);
        }
        return "user/admin/userEdit";
    }

    @PutMapping("username/{id}")
    public String updateUsername(@ModelAttribute("editingUserByAdmin") @Valid User user, BindingResult bindingResult) {
        userValidator.usernameValidate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return REDIRECT_ADMIN_ID;
        }
        userService.save(user);
        return REDIRECT_ADMIN_ID;
    }

    @PutMapping("password/{id}")
    public String updatePassword(@ModelAttribute("editingUserByAdmin") @Valid User user, BindingResult bindingResult) {
        userValidator.passwordValidate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return REDIRECT_ADMIN_ID;
        }
        userService.encodePassword(user);
        userService.save(user);
        return REDIRECT_ADMIN_ID;
    }

    @PutMapping("role/{id}")
    public String updateRole(@ModelAttribute("editingUserByAdmin") @Valid User user, @RequestParam String role) {
        userService.selectRole(user, role);
        userService.save(user);
        return REDIRECT_ADMIN_ID;
    }

    @GetMapping("lockUser/{id}")
    public String lockUser(@ModelAttribute("editingUserByAdmin") @Valid User user) {
        user.setActive(false);
        userService.save(user);
        return REDIRECT_ADMIN_ID;
    }

    @GetMapping("unlockUser/{id}")
    public String unlockUser(@ModelAttribute("editingUserByAdmin") @Valid User user) {
        user.setActive(true);
        userService.save(user);
        return REDIRECT_ADMIN_ID;
    }

    @ModelAttribute("editingUserByAdmin")
    public User getUserFrom() {
        return user;
    }
}
