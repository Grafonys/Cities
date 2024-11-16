package com.example.cities.controllers;

import com.example.cities.dao.services.RoleService;
import com.example.cities.dao.services.UserService;
import com.example.cities.models.security.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping
public class AuthenticationController implements ErrorController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage(Model model, SecurityContext securityContext) {
        String currentUser = securityContext.getAuthentication().getPrincipal().toString();

        if (!currentUser.equals("anonymousUser")) {
            return "redirect:/";
        }
        model.addAttribute("authType", "Login");
        return "authentication";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("authType", "Registration");
        return "authentication";
    }

    @PostMapping("/registration")
    public String register(HttpServletRequest request, RedirectAttributes model) {
        if (userService.findByUsername(request.getParameter("username")).isPresent()) {
            model.addFlashAttribute("authError", "User with this name already exist!");
            return "redirect:/registration";
        }
        User newUser = new User(
                request.getParameter("username"),
                passwordEncoder.encode(request.getParameter("password")));
        newUser.addRole(roleService.findByName("ROLE_USER").get());
        userService.save(newUser);
        return "redirect:/login";
    }
}
