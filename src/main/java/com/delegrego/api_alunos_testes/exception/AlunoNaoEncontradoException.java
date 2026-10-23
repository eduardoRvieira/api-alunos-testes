package com.delegrego.api_alunos_testes.exception;


public class AlunoNaoEncontradoException extends RuntimeException {

	public AlunoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
