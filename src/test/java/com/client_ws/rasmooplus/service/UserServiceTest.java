package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.dto.UserDto;
import com.client_ws.rasmooplus.exception.BadRequestException;
import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.model.jpa.User;
import com.client_ws.rasmooplus.model.jpa.UserType;
import com.client_ws.rasmooplus.repository.jpa.UserRepository;
import com.client_ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client_ws.rasmooplus.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  UserTypeRepository userTypeRepository;

    private UserDto userDto;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void loadUser(){
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("testmock@teste.com");
        userDto.setCpf("12345678911");
        userDto.setUserTypeId(1L);
    }

    @Test
    void given_create_when_idIsNullAndUserTypeIsFound_then_returnUserCreated() {
        userDto.setId(null);

        UserType userType = getUserType();
        when(userTypeRepository.findById(1L)).thenReturn(Optional.of( userType));

        User user = getUser(userType);

        when(userRepository.save(user)).thenReturn(user);

       Assertions.assertEquals(user,userService.create(userDto));

       verify(userTypeRepository, times(1)).findById(1L);
       verify(userRepository, times(1)).save(user);

    }

    @Test
    void given_create_when_idIsNotNull_then_returnThrowBadRequestException() {

        Assertions.assertThrows(BadRequestException.class,()->userService.create(userDto));

        verify(userTypeRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any());

    }

    @Test
    void given_create_when_idIsNullAndUserTypeIsNotFound_then_returnThrowNotFoundException() {
        userDto.setId(null);

        when(userTypeRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,()->userService.create(userDto));

        verify(userTypeRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any());

    }

    @Test
    void given_uploadPhoto_when_thereIsUserAndFileItIsPNGorJPEG_then_updatePhotoAndReturnUser() throws Exception {
        FileInputStream stream =  new FileInputStream("src/test/resources/static/imgJava.png");
        MockMultipartFile multipartFile = new MockMultipartFile("file","imgJava.png", MediaType.MULTIPART_FORM_DATA_VALUE, stream);

        UserType userType = getUserType();
        User user = getUser(userType);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        User userReturned = userService.uploadPhoto(2L, multipartFile);

        assertNotNull(userReturned);
        assertNotNull(userReturned.getPhoto());
        assertEquals("imgJava.png",userReturned.getPhotoName());

        verify(userRepository, times(1)).findById(2L);

    }

    @Test
    void given_uploadPhoto_when_thereIsUserAndFileItIsNotPNGorJPEG_then_throwBadRequestException() throws Exception {
        FileInputStream stream =  new FileInputStream("src/test/resources/static/imgJava.png");
        MockMultipartFile multipartFile = new MockMultipartFile("file","imgJava.txt", MediaType.MULTIPART_FORM_DATA_VALUE, stream);


        assertThrows(BadRequestException.class, () -> userService.uploadPhoto(2L, multipartFile));

        verify(userRepository, times(0)).findById(any());

    }

    private static UserType getUserType() {
        return new UserType(1L,"Aluno", "Aluno da plataforma");
    }

    private User getUser(UserType userType) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setCpf(userDto.getCpf());
        user.setDtSubscription(userDto.getDtSubscription());
        user.setDtExpiration(userDto.getDtExpiration());
        user.setUserType(userType);
        return user;
    }
}