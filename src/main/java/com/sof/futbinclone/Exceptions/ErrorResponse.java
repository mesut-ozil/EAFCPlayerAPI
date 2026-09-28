package com.sof.futbinclone.Exceptions;

import java.time.LocalDateTime;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp; //for when the error occurred

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
