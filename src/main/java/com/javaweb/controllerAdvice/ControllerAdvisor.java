package com.javaweb.controllerAdvice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.customexception.FieldRequiredException;
import com.javaweb.model.ErrorResonseDTO;
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex, WebRequest request){
		ErrorResonseDTO errorResonseDTO = new ErrorResonseDTO();
		errorResonseDTO.setError(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add("Số Nguyên Làm sao chia được cho 0");
		errorResonseDTO.setDetail(details);
		return new ResponseEntity<>(errorResonseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(FieldRequiredException.class)
	public ResponseEntity<Object> handleFieldRequiredException(
	        FieldRequiredException ex, WebRequest request) {
		ErrorResonseDTO errorResonseDTO = new ErrorResonseDTO();
		errorResonseDTO.setError(ex.getMessage());
		List<String> detail = new ArrayList<>();
		detail.add("Số Nguyên làm sao chia được cho 0");
		errorResonseDTO.setDetail(detail);
	    return new ResponseEntity<>(errorResonseDTO, HttpStatus.BAD_GATEWAY);
	}
}
