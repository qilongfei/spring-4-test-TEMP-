package spring4Template.ws;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring4Template.ws.system.ValidationErrorsException;
import spring4Template.ws.system.schema.ExceptionResponse;
import spring4Template.ws.system.schema.ValidationErrorsResponse;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice(annotations = RestController.class)
public class BaseRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse exception(Throwable exception) {
        return new ExceptionResponse("There is an error while processing the action. " + exception.getMessage());
    }


    @ExceptionHandler(ValidationErrorsException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ValidationErrorsResponse exception(ValidationErrorsException exception) {
        Errors errors = exception.getErrors();
        Map<String, String> fieldErrors = errors.getFieldErrors().stream()
                .collect(toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
        List<String> globalErrors = errors.getGlobalErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(toList());
        return new ValidationErrorsResponse(fieldErrors, globalErrors);
    }
}