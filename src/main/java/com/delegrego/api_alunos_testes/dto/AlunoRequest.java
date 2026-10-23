package com.delegrego.api_alunos_testes.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AlunoRequest {

	@NotBlank(message = "Nome não pode ser vazio")
	@Size(max = 50)
	private String nome;

	@NotBlank
	@Email(message = "Email não válido")
	@Size(max = 50, min = 5)
	private String email;

	@NotBlank
	@Size(max = 50)
	private String senha;

	@NotNull
	@PastOrPresent
	private LocalDate dataNascimento;

	// @Min(value = 0)
	@Max(value = 10)
	@PositiveOrZero
	private double media;

	public AlunoRequest(String nome, String email, String senha, LocalDate dataNascimento, double media) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.media = media;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

}
