package com.example.cities.controllers;

import com.example.cities.dao.services.CityService;
import com.example.cities.dao.services.RoleService;
import com.example.cities.dao.services.UserService;
import com.example.cities.models.City;
import com.example.cities.models.security.Role;
import com.example.cities.models.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final CityService cityService;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String getHomepage() {
        return "homepage";
    }

    @GetMapping("/cities")
    public String getCitiesPage(@RequestParam(required = false) Integer id, Model model) {
        City city = cityService.findById(id).orElse(new City());
        model.addAttribute("newCity", city);

        if (model.getAttribute("cities") == null) {
            List<City> cities = cityService.findAll();
            model.addAttribute("cities", cities);
        }
        return "cities";
    }

    @GetMapping("/filtered_cities")
    public String getFilteredCities(@RequestParam(defaultValue = "") String search_input,
                                    @RequestParam(defaultValue = "-1") Integer min_population,
                                    @RequestParam(defaultValue = "-1") Integer max_population,
                                    RedirectAttributes model) {
        try {
            List<City> filteredCities = filter(search_input, min_population, max_population);
            model.addFlashAttribute("cities", filteredCities);
        } catch (IllegalArgumentException exc) {
            model.addFlashAttribute("search_error", exc.getMessage());
        }
        return "redirect:/cities";
    }

    @GetMapping("/init")
    public String init() {
        if (roleService.findAll().isEmpty()) {
            cityService.save(new City("New York City", 19_770_000,
                    "New York City has been called the city that never sleeps, the Big Apple, and the city so nice they named it twice.  It is the world center for finance, the location of the United Nations, and a center for fashion and entertainment.",
                    "40°42′46″N 74°0′22″W"));
            cityService.save(new City("Miami", 6_140_000,
                    "Miami is a major center and leader in finance, commerce, culture, arts, and international trade. Miami's metropolitan area is by far the largest urban economy in Florida",
                    "25.78°N 80.21°W"));
            cityService.save(new City("Louisiana", 4_600_000,
                    "The first Europeans who appeared here were the Spaniards. ATin particular, in 1539 these lands were discovered by an expedition led by Hernando de Soto. At that time, they were inhabited by numerous tribes of Indians. Active colonization of the region began in the seventeenth century, after the Mississippi River basin in 1682 proclaimed the property of France Cavalier de la Salle. This man was later appointed local governor. A few years later, the eastern part ended up under British ownership for a while. After the independence of the United States, in 1803, these lands, as part of a larger territory, were purchased from the French ruler Napoleon for the amount of fifteen million dollars. Nine years later, a separate administrative unit was formed - the state of Louisiana (USA), the capital of which the government chose the city of Baton Rouge.",
                    "28°56′N 33°01′N"));
            cityService.save(new City("Los Angeles", 18_300_000,
                    "The great city of Los Angeles was founded in 1781. It was called El Pueblo de Nuestra Senora la Reina de Los Angeles de Porciuncula. (Our Lady the Queen of the angels of Porcincula). At first, there was only a handful of families but by 1800 it had a population of 315. The Plaza Catholic Church was built in 1822. The settlement of Los Angeles grew steadily. Then in 1847 US forces captured Los Angeles.",
                    "34°03′N 118°15′W"));
            cityService.save(new City("Las Vegas", 2_227_053,
                    "Before Las Vegas was the land of mega hotels, widespread gambling, and overindulgence, its arid lands were inhabited by Southern Nevada Native Americans. The area that Las Vegas occupies was once an abundant wet marshland filled with rich vegetation. But as the marsh receded and waters disappeared from the landscape, the region evolved into an arid desert land. Miraculously, water that was trapped underground sporadically rose to the surface to water the vigorous plants that survived, forming an oasis.",
                    "36°10′30″N 115°08′11″W"));

            Role roleAdmin = roleService.save(new Role("ROLE_ADMIN"));
            Role roleUser = roleService.save(new Role("ROLE_USER"));

            User admin = new User("admin", passwordEncoder.encode("admin"));
            admin.addRole(roleAdmin);
            userService.save(admin);
            User user = new User("user", passwordEncoder.encode("user"));
            user.addRole(roleUser);
            userService.save(user);
        }
        return "redirect:/";
    }

    private List<City> filter(String search_input, Integer min_population, Integer max_population) {
        if (search_input.isEmpty() && min_population == -1 && max_population == -1) {
            throw new IllegalArgumentException("Search fields can not be empty!");
        } else if (!search_input.isEmpty()) {
            if (min_population != -1 && max_population != -1) {
                return cityService.findByStrokeAndPopulationRange(search_input,
                                                                min_population,
                                                                max_population);
            } else if (min_population != -1) {
                return cityService.findByStrokeAndMinPopulation(search_input, min_population);
            } else if (max_population != -1) {
                return cityService.findByStrokeAndMaxPopulation(search_input, max_population);
            } else {
                return cityService.findByStroke(search_input);
            }
        } else if (min_population != -1 && max_population != -1) {
            return cityService.findByPopulationRange(min_population, max_population);
        } else if (min_population != -1) {
            return cityService.findByMinPopulation(min_population);
        } else {
            return cityService.findByMaxPopulation(max_population);
        }
    }
}
