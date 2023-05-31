package com.memeoven.memeoven.exceptions;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "An error occurred";
        String statusType = "";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
            errorMessage = "Error " + statusCode;
            statusType = httpStatus.getReasonPhrase();
        }

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.addObject("statusType", statusType);
        return modelAndView;
    }


}