package br.com.rafaelleoni.specifications.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.parameter")
public class ParameterException extends RuntimeException {

	private static final long serialVersionUID = 4627089149355905161L;

}
