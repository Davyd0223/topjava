package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;

@ActiveProfiles(REPOSITORY_IMPLEMENTATION)
public class JdbcUserRepositoryTest extends UserServiceTest {

}