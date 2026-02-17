package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;
    public MealService(@Qualifier("inMemoryMealRepository") MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal,int authUserId) {
        return repository.save(meal, authUserId);
    }

    public void update(Meal meal, int authUserId) {
        checkNotFound(repository.save(meal, authUserId),meal.getId());
    }

    public Meal get(int id,int authUserId) {
        return checkNotFound(repository.get(id, authUserId), id);
    }

    public void delete(int id,int authUserId) {
        checkNotFound(repository.delete(id, authUserId), id);
    }

    public List<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }
}