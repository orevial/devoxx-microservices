package fr.ippon.microservices.controllers;

import javax.servlet.http.HttpServletRequest;

import fr.ippon.microservices.model.JsonError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by olivier.revial on 07/03/2016.
 */
@Controller
public class GatewayErrorController implements ErrorController {

    @Value("${error.path:/error}")
    private String errorPath;

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(value = "${error.path:/error}", produces = "application/json")
    public @ResponseBody ResponseEntity error(HttpServletRequest request) {

        final int status = getErrorStatus(request);
        final String errorMessage = getErrorMessage(request);
        final JsonError error = new JsonError(status, errorMessage);
        return ResponseEntity.status(status).body(error);
    }

    private int getErrorStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        return statusCode != null ? statusCode : HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    private String getErrorMessage(HttpServletRequest request) {
        final Throwable exc = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if(exc != null) {
            return exc.getMessage();
        } else {
            Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
            return statusCode != null ? HttpStatus.valueOf(statusCode).getReasonPhrase() : "There was an unexpected error";
        }
    }
}