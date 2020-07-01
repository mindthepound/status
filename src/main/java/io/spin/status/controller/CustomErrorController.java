package io.spin.status.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller("error")
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";
    private static final String VIEW_PATH = "errors/";

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String statusCode = String.valueOf(status);
        if (statusCode.equalsIgnoreCase(HttpStatus.BAD_REQUEST.toString())) {
            return VIEW_PATH + "400";
        } else if (statusCode.equalsIgnoreCase(HttpStatus.NOT_FOUND.toString())) {
            return VIEW_PATH + "404";
        } else if (statusCode.equalsIgnoreCase(HttpStatus.METHOD_NOT_ALLOWED.toString())) {
            return VIEW_PATH + "405";
        } else if (statusCode.equalsIgnoreCase(HttpStatus.INTERNAL_SERVER_ERROR.toString())) {
            return VIEW_PATH + "500";
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
