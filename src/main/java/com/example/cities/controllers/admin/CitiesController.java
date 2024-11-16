package com.example.cities.controllers.admin;

import com.example.cities.dao.services.CityService;
import com.example.cities.models.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class CitiesController {

    private final CityService cityService;

    @PostMapping("/cities/save")
    public String saveCity(City city, RedirectAttributes model) {
        if (isValid(city)) {
            cityService.save(city);
        } else {
            model.addFlashAttribute("error",
                    "City is not valid!");
        }
        return "redirect:/cities";
    }

    @PostMapping("/cities/delete")
    public String deleteCity(Integer id, RedirectAttributes model) {
        if (cityService.findById(id).isPresent()) {
            cityService.deleteById(id);
        } else {
            model.addFlashAttribute("error",
                    "City being deleted does not exist!");
        }
        return "redirect:/cities";
    }

    private boolean isValid(City city) {
        List<City> existingCities = cityService.findByNameOrCoordinates(city.getName(), city.getCoordinates());

        return !(city.getName().trim().isEmpty()
                || city.getBriefHistory().trim().isEmpty()
                || city.getCoordinates().trim().isEmpty())
                &&
                ((existingCities.isEmpty())
                || (existingCities.size() == 1 && city.getId() == existingCities.get(0).getId()));
    }

}
