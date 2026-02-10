package ru.javawebinar.topjava.web;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_PER_DAY = 2_000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2024, 4, 25, 12, 30), "Бургер", 850),
                new Meal(LocalDateTime.of(2024, 4, 25, 18, 0), "Пицца", 950),
                new Meal(LocalDateTime.of(2024, 4, 26, 8, 0), "Овсянка", 350)
        );

        List<MealTo> mealToList = meals.stream()
                        .map(meal -> new MealTo(meal.getDateTime(),meal.getDescription(), meal.getCalories(),
                                meal.getCalories() > CALORIES_PER_DAY))
                                .collect(Collectors.toList());

        request.setAttribute("meals", mealToList);
        request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);

        //response.sendRedirect("meals.jsp");
    }
}





















