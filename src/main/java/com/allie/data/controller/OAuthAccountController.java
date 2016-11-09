package com.allie.data.controller;

import com.allie.data.dto.OAuthAccountDTO;
import com.allie.data.service.OAuthAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/allie-data/v1")
@Api(value = "oAuthAccounts", description = "Endpoints for OAuthAccounts")
public class OAuthAccountController {
    private final OAuthAccountService oAuthAccountService;

    public OAuthAccountController(OAuthAccountService oAuthAccountService) {

        this.oAuthAccountService = oAuthAccountService;
    }

    /***
     * Endpoint to persist a oAuthAccount
     * @param account the OAuth account to persist
     * @param requestId id generated by calling service
     * @param correlationId id to correlate calls throughout service layers
     * @return
     */
    @ApiOperation(value = "Persistence service call to store a new OAuth account.",
            notes = "The service will store the OAuth account in backend persistence structure.  If the request is successful, a 201 (created) HttpStatus will " +
                    "be returned, with the persisted OAuth account in the body, an allieId is required to persist a OAuth account, if an allieId is not provided "+
                    "a 422 (un-processable entity) will be returned")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The service successfully persisted the OAuth account"),
            @ApiResponse(code = 409, message = "A OAuth account with the given allieId already exists"),
            @ApiResponse(code = 422, message = "The request was well-formed, however, could not be processed due to semantic errors"),
            @ApiResponse(code = 500, message = "There was an unspecified server error."),
            @ApiResponse(code = 503, message = "There was an issue connecting to a downstream system")
    })
    @RequestMapping(value = "/oAuthAccounts", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OAuthAccountDTO postAccount(@RequestBody OAuthAccountDTO account,
                                       @RequestHeader(value = "x-allie-request-id") String requestId,
                                       @RequestHeader(value = "x-allie-correlation-id") String correlationId) {
        oAuthAccountService.insert(account);
        return account;
    }

    /***
     * Endpoint to get a oAuthAccount by allieId
     * @param allieId the id to find results by
     * @param requestId id generated by calling service
     * @param correlationId id to correlate calls throughout service layers
     * @return
     */
    @ApiOperation(value = "Service call to get OAuth accounts by allieId.",
            notes = "The service will get all OAuth accounts by the supplied allieId.  If the request is successful, a 200 (ok) HttpStatus will " +
                    "be returned, with the found OAuth accounts in the body, an allieId is required, if an allieId is not provided " +
                    "a 422 (un-processable entity) will be returned")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service successfully returned the OAuth accounts"),
            @ApiResponse(code = 422, message = "The request was well-formed, however, could not be processed due to semantic errors"),
            @ApiResponse(code = 500, message = "There was an unspecified server error."),
            @ApiResponse(code = 503, message = "There was an issue connecting to a downstream system")
    })
    @GetMapping(value = "/oAuthAccounts")
    public List<OAuthAccountDTO> getAccount(@RequestParam(value = "allieId") String allieId,
                                            @RequestHeader(value = "x-allie-request-id") String requestId,
                                            @RequestHeader(value = "x-allie-correlation-id") String correlationId) {
        return oAuthAccountService.getByAllieId(allieId);
    }
}
