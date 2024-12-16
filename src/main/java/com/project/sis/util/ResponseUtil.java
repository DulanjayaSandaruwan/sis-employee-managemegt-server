package com.project.sis.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> created(T body, String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.CREATED.value(), body, message);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T body, String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK.value(), body, message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponse<String>> deleted(String message) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), null, message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponse<String>> badRequest(String message) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), null, message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ApiResponse<String>> internalServerError(String message) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, message);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiResponse<String>> notFound(String message) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null, message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
