package org.example.model.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.entity.User;
import org.example.model.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User getValidUser() {
        User user = new User();
        user.setEmail("Amirhossein@NestedGenuis.com");
        user.setPassword("Aa123456");
        user.setAddress("Tehran, No.1");
        return user;
    }

    private User getInvalidFormatEmailUser() {
        User user = new User();
        user.setEmail("Amirhossein@NestedGenuis.com");
        user.setPassword("Aa123456");
        user.setAddress("Tehran, No.1");
        return user;
    }

    @Test
    @DisplayName("test save() when email format is incorrect")
    void saveUser_invalidEmail_throwsIllegalArgumentException() {
        User user = getInvalidFormatEmailUser();
        assertThrows(IllegalArgumentException.class
                , () -> userService.save(user));
        verify(userRepository, times(0)).save(user);
    }


    @Test
    @DisplayName("testing success scenario")
    void getById_existUser_Success() {
        User user = getValidUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getById(1);
        verify(userRepository, times(1)).findById(1L);
        assertEquals(user, result);
    }

    @Test
    void getAll_validUser_success() {
        User user1 = getValidUser();
        User user2 = getInvalidFormatEmailUser();
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> resultUsers = userService.getAll();
        verify(userRepository.findAll(), times(1));
        assertEquals(expectedUsers, resultUsers);
    }

    @Test
    @DisplayName("invalid property name")
    void updateUser_invalidProperty_throwsIllegalArgumentException() {
        User user = getValidUser();
        Map<String, Object> updates = Map.of("username", "Peter");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(IllegalArgumentException.class, () -> userService.update(1L, updates));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void delete() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getOrders_InvalidUserId_throwsEntityNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> userService.getOrders(1L));
        verify(userRepository, times(1)).findById(1L);
    }

}