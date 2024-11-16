package com.example.cities.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public String error(HttpServletRequest request, Model model) {
        int statusCode = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorInfo", getErrorInfo(statusCode));
        return "error";
    }

    private String getErrorInfo(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Your request contains bad syntax and can not be fulfilled";
            case 401 -> "You do not have permission to access this page";
            case 403 -> "Access to this resource is denied";
            case 404 -> "Page not found";
            case 405 -> "Method not allowed";
            case 500 -> "Server error";
            case 502 -> "Bad gateway";
            case 503 -> "Service unavailable";
            default -> "Unknown error";
        };
    }

}
