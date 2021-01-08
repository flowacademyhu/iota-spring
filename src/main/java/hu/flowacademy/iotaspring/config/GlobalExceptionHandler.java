package hu.flowacademy.iotaspring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<String> handleGlobalException(Exception e) {
        log.error("got global exception {}", e.getMessage());
        log.debug("bug bug bug", e);

        return ResponseEntity.badRequest().body("something went wrong");
    }

}
