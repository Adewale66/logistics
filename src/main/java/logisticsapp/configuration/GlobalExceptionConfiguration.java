package logisticsapp.configuration;

import logisticsapp.exceptions.RiderExistsException;
import logisticsapp.exceptions.RiderNotFoundException;
import logisticsapp.exceptions.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionConfiguration {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ProblemDetail notValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail;
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        List<ValidationError> validationErrors = new ArrayList<>();
        String regex = "default message \\[(.*?)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        for (ObjectError error : allErrors) {
            matcher = pattern.matcher(Arrays.toString(error.getArguments()));
            if (matcher.find()) {
                validationErrors.add(
                        new ValidationError(matcher.group(1), error.getDefaultMessage())
                );
            }
        }
        Map<String, List<ValidationError>> response = new HashMap<>(){{
            put("errors", validationErrors);
        }};
        problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(422), "Your request is not valid.");
        problemDetail.setProperty("errors", response);
        return problemDetail;
    }

    @ExceptionHandler(RiderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ProblemDetail riderNotFoundException(RiderNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getLocalizedMessage());
    }

    @ExceptionHandler(RiderExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ProblemDetail riderExistsException(RiderExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409), ex.getLocalizedMessage());
    }
}
