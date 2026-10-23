package com.delegrego.api_alunos_testes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delegrego.api_alunos_testes.dto.AlunoRequest;
import com.delegrego.api_alunos_testes.dto.AlunoResponse;
import com.delegrego.api_alunos_testes.service.AlunoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	private final AlunoService service;

	public AlunoController(AlunoService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<AlunoResponse>> listarAlunos() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listarAlunos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AlunoResponse> obterAlunoPorId(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.obterAlunoPorId(id));
	}

	@PostMapping
	public ResponseEntity<AlunoResponse> cadastrarAluno(@Valid @RequestBody AlunoRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarAluno(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AlunoResponse> atualizarAluno(@PathVariable Long id,
			@Valid @RequestBody AlunoRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(service.atualizarAluno(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirAluno(@PathVariable Long id) {
		service.excluirAluno(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
