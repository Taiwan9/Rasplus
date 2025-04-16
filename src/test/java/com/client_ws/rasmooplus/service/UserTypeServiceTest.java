package com.client_ws.rasmooplus.service;

import com.client_ws.rasmooplus.model.jpa.UserType;
import com.client_ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client_ws.rasmooplus.service.impl.UserTypeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeService;

    /*
    * given_metodo_when_cenario_then_retornoEsperado
    * */

    @Test
    void given_findAll_when_thereAreDataInDatabase_then_returnAllData(){
        List<UserType> userTypeList = new ArrayList<>();
        UserType userType1 = new UserType(1L,"professor", "professor da plataorma");
        UserType userType2 = new UserType(2L,"Aluno", "Aluno da plataorma");
        UserType userType3 = new UserType(3L,"admin", "administrador da plataforma");
        userTypeList.add(userType1);
        userTypeList.add(userType2);
        userTypeList.add(userType3);

        Mockito.when(userTypeRepository.findAll()).thenReturn(userTypeList);
        var result = userTypeService.findAll();

       Assertions.assertThat(result).isNotEmpty();
       Assertions.assertThat(result).hasSize(3);
    }
}
