package com.example.cities.controllers.admin;

import com.example.cities.dao.services.RoleService;
import com.example.cities.dao.services.UserService;
import com.example.cities.models.security.Role;
import com.example.cities.models.security.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String getUsersPage(@RequestParam(required = false) Integer id, Model model) {
        User user = userService.findById(id).orElse(new User());
        List<User> users = userService.findAll();
        List<Role> roles = roleService.findAll();
        model.addAttribute("newUser", user);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "/admin/users";
    }

    @PostMapping("/save")
    public String saveUser(User user, HttpServletRequest request, RedirectAttributes model) {
        String[] roleIds = request.getParameterValues("role_ids");

        if (isValid(user, roleIds)) {
            for (String roleId : roleIds) {
                Role role = roleService.findById(Integer.parseInt(roleId)).get();
                user.addRole(role);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
        } else {
            model.addFlashAttribute("error", "User is not valid!");
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Integer id, RedirectAttributes model) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
        } else {
            model.addFlashAttribute("error", "User being deleted does not exist!");
        }
        return "redirect:/admin/users";
    }

    private boolean isValid(User user, String[] roleIds) {
        return !(user.getUsername().trim().isEmpty()
                || user.getPassword().trim().isEmpty())
                && userService.findByUsername(user.getUsername()).isEmpty()
                && roleIds != null;
    }
}
