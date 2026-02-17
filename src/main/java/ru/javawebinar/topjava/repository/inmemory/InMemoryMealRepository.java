package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "ланч", 510), 1);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "ужин", 1500), 2);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "ланч", 510), 2);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "ужин", 1500), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = mealsMap.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        Meal result = meals.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            meal.setUserId(userId);
            return meal;
        });
        return (result == meal) ? meal : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = mealsMap.get(userId);
        if (meals == null) {
            return false;
        }
        if(meals.remove(id) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = mealsMap.get(userId);
        if (meals == null) {
            return null;
        }
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = mealsMap.get(userId);

        if (meals == null) {
            return Collections.emptyList();
        }
        return meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

