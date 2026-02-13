package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RealizationMealStorage implements MealStorage {
    ConcurrentHashMap<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private static final AtomicInteger counter = new AtomicInteger(0);

    public RealizationMealStorage() {
        initData();
    }

    private void initData() {
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
    @Override
    public Meal save(Meal meal) {
        meal.setId(counter.incrementAndGet());
        mealsMap.put(meal.getId(), meal);
        return  meal;
    }

    @Override
    public Meal get(int meal) {
        return mealsMap.get(meal);
    }

    @Override
    public void delete(int meal) {
        mealsMap.remove(meal);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealsMap.values();
    }
}
