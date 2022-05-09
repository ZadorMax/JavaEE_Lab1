package kma.topic2.junit.validation;

import static org.junit.jupiter.api.Assertions.*;
import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserValidator service;

    @org.junit.jupiter.api.Test
    void validateNewUserS() {
        service.validateNewUser(
                NewUser.builder().login("Maxym").password("Abcdef").fullName("Zador").build());

        verify(userRepository).isLoginExists("Maxym");
    }

    @org.junit.jupiter.api.Test
    void userAlreadyExists() {
        when(userRepository.isLoginExists("Maxym")).thenReturn(true);
        assertThatThrownBy(() -> service.validateNewUser(
                NewUser.builder().login("Maxym").password("Abcdef").fullName("Zador").build()))
                .isInstanceOf(LoginExistsException.class);
    }

//    @ParameterizedTest
//    @MethodSource("invalidSizePasswordTestData")
//    void wrongPassword(String password, List<String> errors) {
//        assertThatThrownBy(() -> service.validateNewUser(
//                NewUser.builder().login("Maxym").password(password).fullName("Zador").build()))
//                .isInstanceOfSatisfying(
//                        ConstraintViolationException.class,
//                        ex -> assertThat(ex.getErrors()).isEqualTo(errors));
//    }
}