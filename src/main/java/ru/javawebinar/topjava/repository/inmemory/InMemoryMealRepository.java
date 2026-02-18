package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.filteredDatesInterval;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(10), "Завтрак", 500), 1);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(14), "Обед", 1800), 1);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(20), "Ужин", 500), 1);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(1).plusHours(10), "Завтрак", 1000), 1);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(1).plusHours(14), "Обед", 500), 1);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(1).plusHours(20), "Ужин", 410), 1);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(2).plusHours(10), "Завтрак", 1000), 2);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(2).plusHours(14), "Обед", 500), 2);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(3).plusHours(20), "Ужин", 410), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = mealsMap.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            meal.setId(userId);
            return meal;
        });
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = mealsMap.get(userId);
        return meals != null && meals.remove(id) != null;
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

    @Override
    public List<Meal> getBetweenInclusive(LocalDate start, LocalDate end, int userId) {
        Map<Integer, Meal> meals = mealsMap.get(userId);
        if (meals == null) {
            return Collections.emptyList();
        }
        return meals.values().stream()
                .filter(meal -> filteredDatesInterval(meal.getDate(), start, end))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

