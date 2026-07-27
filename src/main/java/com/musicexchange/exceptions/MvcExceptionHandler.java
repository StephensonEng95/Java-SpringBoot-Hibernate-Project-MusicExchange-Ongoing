package com.musicexchange.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MvcExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public String handleDuplicateResourceNotFound(DuplicateResourceException duplicateResourceException, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", duplicateResourceException.getMessage());
        return "signup";

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException resourceNotFoundException, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", resourceNotFoundException.getMessage());
        return "login";
    }
}
