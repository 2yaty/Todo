package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
   @ExceptionHandler(value = {ApiRequestException.class})
   public ResponseEntity<Object> handleApiRequestException(ApiRequestException e, WebRequest webRequest)
   {
     HttpStatus badRequest = HttpStatus.BAD_REQUEST;
     ApiException apiException = new ApiException(
       e.getMessage(),
       badRequest,
        ZonedDateTime.now(ZoneId.of("Z"))
     );

     return new ResponseEntity<>(apiException, badRequest);
   }
  //
   @ExceptionHandler
   public ResponseEntity<Object> handleApiRequestException(NoHandlerFoundException e)
   {
     HttpStatus badRequest = HttpStatus.BAD_REQUEST;
     ApiException apiException = new ApiException(
       e.getMessage(),
       badRequest,
       ZonedDateTime.now(ZoneId.of("Z"))
     );

     return new ResponseEntity<>(apiException, badRequest);
   }
  //
   @ExceptionHandler
   public ResponseEntity<Object> handleApiRequestException(HttpMessageNotReadableException e)
   {
     HttpStatus badRequest = HttpStatus.BAD_REQUEST;
     ApiException apiException = new ApiException(
       e.getMessage(),
       badRequest,
       ZonedDateTime.now(ZoneId.of("Z"))
     );

     return new ResponseEntity<>(apiException, badRequest);
   }
  //
   @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<Object> handleApiRequestException(RuntimeException  e)
   {
     HttpStatus badRequest = HttpStatus.BAD_REQUEST;
     ApiException apiException = new ApiException(
       e.getMessage(),
       badRequest,
       ZonedDateTime.now(ZoneId.of("Z"))
     );

     return new ResponseEntity<>(apiException, badRequest);
   }
  //
   @ExceptionHandler
   public ResponseEntity<Object> handleApiRequestException(MethodArgumentNotValidException e)
   {
     HttpStatus badRequest = HttpStatus.BAD_REQUEST;
     ApiException apiException = new ApiException(
       e.getMessage(),
       badRequest,
       ZonedDateTime.now(ZoneId.of("Z"))
     );

     return new ResponseEntity<>(apiException, badRequest);
   }


  //
  // @
}
