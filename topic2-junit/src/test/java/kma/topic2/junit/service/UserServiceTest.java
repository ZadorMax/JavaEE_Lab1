package kma.topic2.junit.service;


import kma.topic2.junit.exceptions.UserNotFoundException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;
    @Autowired
    private UserRepository userRepository;
    @SpyBean
    private UserValidator validator;
    @Captor
    private ArgumentCaptor<Logger> logCaptor;

    // getUserByLogin method tests
    @Test
    void checkLogin() {
        userRepository.saveNewUser(
                NewUser.builder().login("Maxym").password("Abcdef").fullName("Zador").build());

        assertThat(service.getUserByLogin("Max"))
                .returns("Maxym", User::getLogin)
                .returns("Abcdef", User::getPassword)
                .returns("Zador", User::getFullName);
    }

    @Test
    void loginExceptionThrown() {
        assertThatThrownBy(() -> service.getUserByLogin("Maxym"))
                .isInstanceOfSatisfying(
                        UserNotFoundException.class,
                        ex -> assertThat(ex.getMessage()).isEqualTo("Not found"));
    }


}