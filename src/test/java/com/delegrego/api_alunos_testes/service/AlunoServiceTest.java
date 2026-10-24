    package com.delegrego.api_alunos_testes.service;

    import com.delegrego.api_alunos_testes.dto.AlunoRequest;
    import com.delegrego.api_alunos_testes.dto.AlunoResponse;
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


    }
