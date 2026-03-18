package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        log.info("meals");
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/mealForm")
    public String mealForm(Model model, @RequestParam(required = false) Integer id) {
        log.info(id == null ? "create new meal" : "edit meal {}", id);
        Meal meal = (id == null) ? new Meal() : get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/meals")
    public String setMeal(HttpServletRequest request) {
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        log.info("getLocalDateTime {}", localDateTime);
        String description = request.getParameter("description");
        log.info("getDescription {}", description);
        int calories = Integer.parseInt(request.getParameter("calories"));
        log.info("getCalories {}", calories);

        String mealIdParam = request.getParameter("id");
        Integer mealId = (mealIdParam == null || mealIdParam.isEmpty()) ? null : Integer.parseInt(mealIdParam);
        log.info("getCalories {}", mealId);

        Meal newMeal = new Meal(mealId, localDateTime, description, calories);

        if (mealId == null) {
            create(newMeal);
        } else {
            update(newMeal, mealId);
        }

        return "redirect:/meals";
    }

    @GetMapping("/meals/delete")
    public String deleteMeal(@RequestParam int id) {
        log.info("deleteMeal");
        delete(id);
        return "redirect:/meals";
    }
}
