package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFound(repository.save(meal, userId), meal.getId());
    }

    public Meal get(int id, int userId) {
        return checkNotFound(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFound(repository.delete(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.getBetweenInclusive(startDate, endDate, userId);
    }
}