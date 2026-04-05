package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/profile/meals")
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam(required = false) Integer id,
                               @RequestParam String dateTime,
                               @RequestParam String description,
                               @RequestParam int calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        if (id == null || id.toString().isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        return super.getBetween(
                parseLocalDate(startDate),
                parseLocalTime(startTime),
                parseLocalDate(endDate),
                parseLocalTime(endTime)
        );
    }

    private LocalDate parseLocalDate(String str) {
        return str == null || str.isEmpty() ? null : LocalDate.parse(str);
    }

    private LocalTime parseLocalTime(String str) {
        return str == null || str.isEmpty() ? null : LocalTime.parse(str);
    }
}
