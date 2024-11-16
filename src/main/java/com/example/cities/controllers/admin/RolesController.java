package com.example.cities.controllers.admin;

import com.example.cities.dao.services.RoleService;
import com.example.cities.models.security.Role;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/admin/roles")
public class RolesController {

    private final RoleService roleService;

    @GetMapping
    public String getRolesPage(@RequestParam(required = false) Integer id, Model model) {
        Role role = roleService.findById(id).orElse(new Role());
        List<Role> roles = roleService.findAll();
        model.addAttribute("newRole", role);
        model.addAttribute("roles", roles);
        return "/admin/roles";
    }

    @PostMapping("/save")
    public String saveRole(Role role, RedirectAttributes model) {
        if (isValid(role)) {
            role.setName(role.getName().toUpperCase());
            roleService.save(role);
        } else {
            model.addFlashAttribute("error", "Role is not valid!");
        }
        return "redirect:/admin/roles";
    }

    @PostMapping("/delete")
    public String deleteRole(@RequestParam Integer id, RedirectAttributes model) {
        if (roleService.findById(id).isPresent()) {
            roleService.deleteById(id);
        } else {
            model.addFlashAttribute("error",
                    "Role being deleted does not exist!");
        }
        return "redirect:/admin/roles";
    }

    private boolean isValid(Role role) {
        return roleService.findByName(role.getName()).isEmpty()
                && role.getName().startsWith("ROLE_")
                && !role.getName().trim().equals("ROLE_");
    }
}
