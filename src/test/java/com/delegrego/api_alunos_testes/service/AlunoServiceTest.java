package com.delegrego.api_alunos_testes.service;

import com.delegrego.api_alunos_testes.dto.AlunoRequest;
import com.delegrego.api_alunos_testes.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    AlunoService service;

    @Mock
    AlunoRepository repository;

    @Test
    void deveSalvarERetornarAluno(){

        // Arrange
        AlunoRequest request = new AlunoRequest("Eduardo", "eduardo@gmail.com", "12345", LocalDate.of(2005, 7, 5), 9);

        // Stub -> parte
        Mockito.when(repository.existsByEmailIgnoreCase(request.getEmail())).thenReturn(false);

        // Act
        

        // Assert
    }


}
