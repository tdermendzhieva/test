package com.allie.data.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Created by andrew.larsen on 11/7/2016.
 */
@Service
public class ValidationService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;
    /**
     * Method to return validation errors as string
     * @param bindingResult
     * @return
     */
    public String getValidationErrors(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;

                if(first) {
                    stringBuilder.append(messageSource.getMessage(fieldError, null));
                    first = false;
                } else {
                    stringBuilder.append(", ");
                    stringBuilder.append(messageSource.getMessage(fieldError, null));
                }
            }

        }
        return stringBuilder.toString();
    }
}
