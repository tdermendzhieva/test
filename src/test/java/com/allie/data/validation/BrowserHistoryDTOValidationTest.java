package com.allie.data.validation;

import com.allie.data.constant.ValidationMessage;
import com.allie.data.dto.BrowserHistoryDTO;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by andrew.larsen on 11/7/2016.
 */
public class BrowserHistoryDTOValidationTest {

    private Validator validator;

    @Before
    public void setup(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testAllieIdCannotBeNull(){
        BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryDTO();
        Set<ConstraintViolation<BrowserHistoryDTO>> constraintViolations =  validator.validate(browserHistoryDTO);
        assert(!constraintViolations.isEmpty());
        for(ConstraintViolation<BrowserHistoryDTO> violation : constraintViolations){
            assert(violation.getMessage().equals(ValidationMessage.ALLIEID_NOT_NULL));
        }
    }

}
