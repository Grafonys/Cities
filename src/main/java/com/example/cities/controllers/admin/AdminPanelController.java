package com.example.cities.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPanelController {

    @GetMapping
    public String getAdminPanelPage() {
        return "/admin/admin_panel";
    }
}
