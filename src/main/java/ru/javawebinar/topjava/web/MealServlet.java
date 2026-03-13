package ru.javawebinar.topjava.web;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServlet;

public class MealServlet extends HttpServlet {
    private ClassPathXmlApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
//       springContext.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.refresh();
        mealController = springContext.getBean(MealRestController.class);
    }
}
