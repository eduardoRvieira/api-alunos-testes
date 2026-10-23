package com.delegrego.api_alunos_testes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delegrego.api_alunos_testes.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

}
