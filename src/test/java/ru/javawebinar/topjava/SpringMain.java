package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;
import static ru.javawebinar.topjava.Profiles.getActiveDbProfile;

public class SpringMain {
    public static void main(String[] args) {

        try (GenericXmlApplicationContext context = new GenericXmlApplicationContext()) {
            context.getEnvironment().setActiveProfiles(REPOSITORY_IMPLEMENTATION, getActiveDbProfile());
            context.load("spring/spring-app.xml");
            context.load("spring/spring-db.xml");
            context.refresh();
            AdminRestController adminUserController = context.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            System.out.println();

            MealRestController mealController = context.getBean(MealRestController.class);
            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println();
            System.out.println(mealController.getBetween(null, null, null, null));
        }
    }
}
