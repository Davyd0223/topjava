package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            mealsMap.put(meal.getId(), meal);
            return meal;
        }
        Meal result = mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if (oldMeal.getUserId() != userId) {
                return oldMeal;
            }
            meal.setUserId(userId);
            return meal;
        });
        // handle case: update, but not present in storage
        if (result == meal) {
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = mealsMap.get(id);
        if (meal == null || meal.getUserId() != userId) {
            return false;
        }
        return mealsMap.remove(id, meal);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = mealsMap.get(id);
        if (meal != null && meal.getUserId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return mealsMap.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

