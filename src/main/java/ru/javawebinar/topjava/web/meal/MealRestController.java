package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        int userId = SecurityUtil.authUserId();
        checkIsNew(meal);
        return service.create(meal, userId);
    }

    public Meal get(int id) {
        log.info("get");
        int userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        List<Meal> meals = service.getAll(userId);
        return getTos(meals, SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getBetween dates({} - {}) time({} - {})", startDate, endDate, startTime, endTime);
        int userId = SecurityUtil.authUserId();

        List<Meal> meals = service.getBetweenDates(startDate, endDate, userId);

        return getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}