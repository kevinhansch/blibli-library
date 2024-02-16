package com.gdn.blibli.library.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.gdn.blibli.library.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blibli.oss.backend.command.exception.CommandValidationException;
import com.blibli.oss.backend.common.model.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public Response<Object> badRequest(ConstraintViolationException ex) {
    return Response.builder().code(HttpStatus.BAD_REQUEST.value())
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .errors(getErrorMap(ex.getConstraintViolations())).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CommandValidationException.class)
  public Response<Object> badRequest(CommandValidationException ex) {
    return Response.builder().code(HttpStatus.BAD_REQUEST.value())
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .errors(getErrorMap(ex.getConstraintViolations())).build();
  }

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(BusinessException.class)
  public Response<Object> businessException(BusinessException ex) {
    return Response.builder().code(ex.getCode()).status(ex.getErrorCode()).errors(ex.getErrors())
        .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  public Response<Object> internalServerError(Throwable throwable) {
    log.error("internalServerError with error = {}", throwable);
    return Response.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .status(throwable.getMessage()).build();
  }

  private Map<String, List<String>> getErrorMap(Set<ConstraintViolation<?>> errors) {
    return errors.stream().collect(HashMap::new, (map, v) -> {
      String variable = v.getPropertyPath().toString();
      List<String> listError = map.get(variable);
      if (listError == null) {
        listError = new ArrayList<>();
        listError.add(v.getMessage());
        map.put(variable, listError);
      } else {
        listError.add(v.getMessage());
      }
    }, HashMap::putAll);
  }
}
