package com.sof.futbinclone.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice//class containing logic that handles exceptions centrally
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleArgumentErrors(HandlerMethodValidationException ex) { //if annotation with @valid fails this is called
        ErrorResponse error = new ErrorResponse(400, "Method argument not valid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); //returns a 400 bad request error with appropriate message
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlayerNotFoundErrors(PlayerNotFoundException ex) { //if player not found exception is thrown this is called
        ErrorResponse error = new ErrorResponse(404, "Player not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); //returns 404
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) { //if arguments in api call have a different type this is thrown
        ErrorResponse error = new ErrorResponse(400, "Method argument(s) are of the wrong type");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex) { //if there is a missing argument this is thrown
        ErrorResponse error = new ErrorResponse(400, "Missing request parameter");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectRequestBody(HttpMessageNotReadableException ex) { //if there is an incorrect json this is thrown
        ErrorResponse error = new ErrorResponse(400, "Unreadable request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidFilterParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFilter(InvalidFilterParameterException ex) {
        ErrorResponse error = new ErrorResponse(400, "Invalid filter entered");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgument(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse(400, "Invalid method argument");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DuplicatePlayerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePlayer(DuplicatePlayerException ex) {
        ErrorResponse error = new ErrorResponse(409, "Player already exists"); //returns 409 conflict instead
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleNormalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(500, "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error); //in case of any other errors
    }
}
