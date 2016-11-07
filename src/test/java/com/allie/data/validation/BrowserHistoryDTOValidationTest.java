package com.allie.data.validation;

import com.allie.data.constant.ValidationMessage;
import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.util.StringTestUtil;
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
    private String urlStringMax;

    @Before
    public void setup(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        urlStringMax = StringTestUtil.getStringOfLength(500);

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
    @Test
    public void testAllieIdIsValid(){
        BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryDTO();
        browserHistoryDTO.setAllieId("TEST");
        Set<ConstraintViolation<BrowserHistoryDTO>> constraintViolations =  validator.validate(browserHistoryDTO);

        assert(constraintViolations.isEmpty());
    }
    @Test
    public void tesUrlCanBeMaxSize(){
        BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryDTO();
        browserHistoryDTO.setAllieId("TEST");
        assert(urlStringMax.length()==500);
        browserHistoryDTO.setUrl(urlStringMax);
        Set<ConstraintViolation<BrowserHistoryDTO>> constraintViolations =  validator.validate(browserHistoryDTO);

        assert(constraintViolations.isEmpty());
    }
    @Test
    public void tesUrlCannotExceedMaxSize(){
        BrowserHistoryDTO browserHistoryDTO = new BrowserHistoryDTO();
        browserHistoryDTO.setAllieId("TEST");
        //add one char to 501
        String urlStringExceedMax = urlStringMax + "1";
        assert(urlStringExceedMax.length() == 501);
        //set url
        browserHistoryDTO.setUrl(urlStringExceedMax);
        Set<ConstraintViolation<BrowserHistoryDTO>> constraintViolations =  validator.validate(browserHistoryDTO);
        assert(!constraintViolations.isEmpty());
        for(ConstraintViolation<BrowserHistoryDTO> violation : constraintViolations){
            assert(violation.getMessage().equals(ValidationMessage.URL_MAX_SIZE));
        }
    }
}
