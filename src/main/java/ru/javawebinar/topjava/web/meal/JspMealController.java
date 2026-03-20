package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getAll(Model model) {
        log.info("getAll");
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Integer id) {
        log.info(id == null ? "create new meal" : "edit meal {}", id);
        Meal meal = (id == null) ? new Meal() : super.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete")
    public String doDelete(@RequestParam int id) {
        log.info("delete {}", id);
        super.delete(id);
        return "redirect:/meals";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        log.info("save meal {}", request.getAttribute("meal"));
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
            super.create(newMeal);
        } else {
            super.update(newMeal, mealId);
        }
        return "redirect:/meals";
    }
}
