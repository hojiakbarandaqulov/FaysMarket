package org.example.exp.handle;


import org.example.dto.ApiResponse;
import org.example.exp.AppBadException;
import org.example.exp.AppForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleController {

//    @ExceptionHandler({IllegalArgumentException.class})
//    public ResponseEntity<?> handle(IllegalArgumentException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
    @ExceptionHandler({IllegalArgumentException.class, AppBadException.class, AppForbiddenException.class})
    public ResponseEntity<ApiResponse> handle(AppBadException e) {
        return ResponseEntity.ok(ApiResponse.bad(e.getMessage()));
    }

  /*  @ExceptionHandler(AppForbiddenException.class)
    public ResponseEntity<String> handler(AppForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }*/
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handler(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
