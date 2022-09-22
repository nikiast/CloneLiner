package com.clone.liner.controller;

import com.clone.liner.model.user.User;
import com.clone.liner.service.UserService;
import com.clone.liner.util.UserValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    private static final String REDIRECT_USER_PROFILE = "redirect:/user/profile";

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/profile")
    public String userSettings(@ModelAttribute("userFromSession") User userFromSession, Model model) {
        model.addAttribute("user", userFromSession);
        return "user/profile";
    }

    @PostMapping("updateUserUsername")
    public String updateUsername(@ModelAttribute("userFromSession") @Valid User userFromSession,
                                 BindingResult bindingResult) {
        userValidator.usernameValidate(userFromSession, bindingResult);
        if (bindingResult.hasErrors()) {
            return REDIRECT_USER_PROFILE;
        }

        userService.save(userFromSession);
        SecurityContextHolder.clearContext();
        return REDIRECT_USER_PROFILE;
    }

    @PostMapping("updateUserEmail")
    public String updateEmail(@ModelAttribute("userFromSession") @Valid User userFromSession,
                              BindingResult bindingResult) {
        userValidator.emailValidate(userFromSession, bindingResult);
        if (bindingResult.hasErrors()) {
            return REDIRECT_USER_PROFILE;
        }
        userService.save(userFromSession);
        SecurityContextHolder.clearContext();
        return REDIRECT_USER_PROFILE;
    }

    @PostMapping("updateUserPassword")
    public String updatePassword(@ModelAttribute("userFromSession") @Valid User userFromSession,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return REDIRECT_USER_PROFILE;
        }
        userService.encodePassword(userFromSession);
        userService.save(userFromSession);
        SecurityContextHolder.clearContext();
        return REDIRECT_USER_PROFILE;
    }

    @GetMapping("deleteUser")
    public String userEditForm(@ModelAttribute("userFromSession") @Valid User userFromSession) {
        userFromSession.setActive(false);
        userService.save(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    @ModelAttribute("userFromSession")
    private User getUserFromSession() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
