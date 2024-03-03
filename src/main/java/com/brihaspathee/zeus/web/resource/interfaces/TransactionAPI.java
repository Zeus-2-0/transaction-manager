package com.brihaspathee.zeus.web.resource.interfaces;

import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.exception.ApiExceptionList;
import com.brihaspathee.zeus.web.model.DataTransformationDto;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 02, November 2022
 * Time: 3:36 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.resource.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("/api/v1/zeus/transaction")
@Validated
public interface TransactionAPI {

    /**
     * Create a new account
     * @param dataTransformationDto
     * @return
     */
    @Operation(
            operationId = "Create a new transaction",
            method = "POST",
            description = "Create a new transaction",
            tags = {"transaction"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully created the transaction",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = DataTransformationDto.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = ApiExceptionList.class))
                    }),
            @ApiResponse(responseCode = "409",
                    description = "Conflict",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = ApiExceptionList.class))
                    })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ZeusApiResponse<DataTransformationDto>> createTransaction(
            @RequestBody @Valid DataTransformationDto dataTransformationDto) throws JsonProcessingException;

    /**
     * Get the transaction related to the zeus transaction control number that is passed in as input
     * @param ztcn
     * @return
     */
    @Operation(
            operationId = "Get Transaction by ZTCN",
            method = "GET",
            description = "Get the details of the transaction by its ZTCN",
            tags = {"transaction"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the details of the transaction",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionDto.class))
                            }
                    )
            }
    )
    @GetMapping("/{ztcn}")
    ResponseEntity<ZeusApiResponse<TransactionDto>> getTransactionByZtcn(@PathVariable("ztcn") String ztcn);

    /**
     * Delete transaction by ZTCN
     * @param ztcn
     * @return
     */
    @DeleteMapping("/delete/{ztcn}")
    ResponseEntity<ZeusApiResponse<String>> cleanUp(@PathVariable("ztcn") String ztcn);

    /**
     * Clean up the entire db
     * @return
     */
    @Operation(
            operationId = "Delete all data",
            method = "DELETE",
            description = "Delete all data",
            tags = {"account"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Data deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = ZeusApiResponse.class))
                    })
    })
    @DeleteMapping("/delete")
    ResponseEntity<ZeusApiResponse<String>> cleanUp();
}
