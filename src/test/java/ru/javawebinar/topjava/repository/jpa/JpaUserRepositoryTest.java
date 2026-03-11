package ru.javawebinar.topjava.repository.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;

@ActiveProfiles(REPOSITORY_IMPLEMENTATION)
public class JpaUserRepositoryTest extends UserServiceTest {

}