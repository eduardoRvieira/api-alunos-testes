package com.delegrego.api_alunos_testes.service;

import com.delegrego.api_alunos_testes.dto.AlunoRequest;
import com.delegrego.api_alunos_testes.dto.AlunoResponse;
import com.delegrego.api_alunos_testes.exception.AlunoNaoEncontradoException;
import com.delegrego.api_alunos_testes.exception.EmailCadastradoException;
import com.delegrego.api_alunos_testes.model.Aluno;
import com.delegrego.api_alunos_testes.repository.AlunoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        Aluno aluno = new Aluno(1L, request.getNome(), request.getEmail(), request.getSenha(), request.getDataNascimento(), request.getMedia());

        Mockito.when(repository.save(Mockito.any(Aluno.class))).thenReturn(aluno);

        // Act
        AlunoResponse alunoSalvo = service.cadastrarAluno(request);

        // Assert
        Assertions.assertThat(alunoSalvo).isNotNull();
        Assertions.assertThat(alunoSalvo.getId()).isEqualTo(1L);
        Assertions.assertThat(alunoSalvo.getNome()).isEqualTo("Eduardo");
    }

    @Test
    void deveRetornarListaDeAlunos(){

        //Arrange
        Aluno aluno1 = new Aluno(1L, "Eduardo", "eduardo@gmail.com", "12345", LocalDate.of(2005, 7, 5), 9);
        Aluno aluno2 = new Aluno(2L, "Luiz", "luiz@gmail.com", "12345", LocalDate.of(2004, 4, 12), 4);

        // Stub do FindAll
        Mockito.when(repository.findAll()).thenReturn(List.of(aluno1, aluno2));

        // Act
        List<AlunoResponse> listaAlunos = service.listarAlunos();

        // Assert
        Assertions.assertThat(listaAlunos).isNotNull();
        Assertions.assertThat(listaAlunos).hasSize(2);
        Assertions.assertThat(listaAlunos.get(0).getId()).isEqualTo(1L);
        Assertions.assertThat(listaAlunos.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void deveAtualizarAlunoComSucesso() {
        // Arrange
        Long id = 1L;
        AlunoRequest request = new AlunoRequest("Eduardo Atualizado", "eduardo.novo@gmail.com", "54321", LocalDate.of(2005, 7, 5), 9.5);

        Aluno alunoExistente = new Aluno(id, "Eduardo", "eduardo@gmail.com", "12345", LocalDate.of(2005, 7, 5), 9);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));
        Mockito.when(repository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)).thenReturn(false);

        Aluno alunoAtualizado = new Aluno(id, request.getNome(), request.getEmail(), request.getSenha(), request.getDataNascimento(), request.getMedia());
        Mockito.when(repository.save(Mockito.any(Aluno.class))).thenReturn(alunoAtualizado);

        // Act
        AlunoResponse response = service.atualizarAluno(id, request);

        // Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(id);
        Assertions.assertThat(response.getNome()).isEqualTo("Eduardo Atualizado");
        Assertions.assertThat(response.getEmail()).isEqualTo("eduardo.novo@gmail.com");
        Assertions.assertThat(response.getMedia()).isEqualTo(9.5);
    }

    @Test
    void naoDeveAtualizarAlunoQuandoEmailJaCadastradoEmOutroRegistro() {
        // Arrange
        Long id = 1L;
        AlunoRequest request = new AlunoRequest("Eduardo", "outro@gmail.com", "12345", LocalDate.of(2005, 7, 5), 9);

        Aluno alunoExistente = new Aluno(id, "Eduardo", "eduardo@gmail.com", "12345", LocalDate.of(2005, 7, 5), 9);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(alunoExistente));
        Mockito.when(repository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)).thenReturn(true);

        // Act & Assert
        Assertions.assertThatThrownBy(() -> service.atualizarAluno(id, request))
                .isInstanceOf(EmailCadastradoException.class)
                .hasMessage("Email já cadastrado");

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Aluno.class));
    }

    @Test
    void naoDeveAtualizarAlunoQuandoIdNaoEncontrado() {
        // Arrange
        Long id = 99L;
        AlunoRequest request = new AlunoRequest("Eduardo", "eduardo@gmail.com", "12345", LocalDate.of(2005, 7, 5), 9);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> service.atualizarAluno(id, request))
                .isInstanceOf(AlunoNaoEncontradoException.class)
                .hasMessage("Aluno não encontrado");

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Aluno.class));
    }

    @Test
    void deveExcluirAlunoComSucesso() {
        // Arrange
        Long id = 1L;

        // O método deleteById do JpaRepository retorna void, então usamos doNothing ou apenas rodamos a chamada
        Mockito.doNothing().when(repository).deleteById(id);

        // Act
        service.excluirAluno(id);

        // Assert
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }
}