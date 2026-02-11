package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_PER_DAY = 2_000;

    private List<Meal> meals;

    public void init() throws ServletException {
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2024, 4, 25, 12, 30), "Бургер", 850),
                new Meal(LocalDateTime.of(2024, 4, 25, 18, 0), "Пицца", 1950),
                new Meal(LocalDateTime.of(2024, 4, 26, 8, 0), "Овсянка", 350)
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<MealTo> mealToList = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59, 59), CALORIES_PER_DAY);

        request.setAttribute("meals", mealToList);
        request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);
    }
}





















