package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealStorage {
    Meal save(Meal meal);

    Meal get(int meal);

    void delete(int meal);

    Collection<Meal> getAll();
}
