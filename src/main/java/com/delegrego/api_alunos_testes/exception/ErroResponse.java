package com.delegrego.api_alunos_testes.exception;


import java.time.Instant;

public record ErroResponse(
		int status,
		String mensagem,
		Instant timestamp
) {}
