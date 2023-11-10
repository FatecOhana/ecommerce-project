package com.ecommerce.restendpoints;

import com.ecommerce.dto.OperationData;
import com.ecommerce.dto.SaleDTO;
import com.ecommerce.dto.SingleItemPayload;
import com.ecommerce.dto.command.DeleteItemCommand;
import com.ecommerce.dto.command.FindItemByParameterCommand;
import com.ecommerce.dto.command.UpsertItemCommand;
import com.ecommerce.services.SaleService;
import com.ecommerce.utils.UtilsOperation;
import com.ecommerce.utils.UtilsValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Sale", description = "Endpoint to manipulate and manage sales and their data")
public class SaleRestEndpoint {

    private final SaleService saleService;

    public SaleRestEndpoint(SaleService saleService) {
        this.saleService = saleService;
    }

    @Operation(summary = "Create or Update Sales values",
            description = "If you pass the ID and there is a Sales in the corresponding database, it will be updated"
    )
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Sales Created or Updated", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = OperationData.class))))
    @PostMapping(value = "api/v1/sale", produces = "application/json", consumes = "application/json")
    public ResponseEntity<OperationData<?>> upsertSale(
            @RequestBody SingleItemPayload<SaleDTO> salePayload
    ) throws Exception {
        UpsertItemCommand<SaleDTO> command = UpsertItemCommand.<SaleDTO>builder().data(salePayload).build();
        return new ResponseEntity<>(saleService.upsertRegisterV2(command), HttpStatus.OK);
    }

    @Operation(summary = "Delete (Soft-Delete) one Sales", description = "You only need to enter the Sales's ID in the request body")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Sales Deleted", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = OperationData.class))))
    @DeleteMapping(value = "api/v1/sale", produces = "application/json", consumes = "application/json")
    public ResponseEntity<OperationData<?>> softDeleteSale(
            @RequestBody SingleItemPayload<Integer> salePayload
    ) throws Exception {
        DeleteItemCommand command = DeleteItemCommand.builder().id(UtilsValidation.ifNull(salePayload, new SingleItemPayload<Integer>()).getData()).build();
        return new ResponseEntity<>(saleService.softDeleteRegister(command), HttpStatus.OK);
    }

    @Operation(summary = "Get database sale values", description = "You must enter one of the filter values")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Matching  sale values", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = OperationData.class))))
    @GetMapping(value = "api/v1/sale", produces = "application/json")
    public ResponseEntity<OperationData<?>> getSale(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String uniqueName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) boolean isDeleted
    ) throws Exception {
        FindItemByParameterCommand find = FindItemByParameterCommand.builder().id(id).build();
        return new ResponseEntity<>(saleService.findRegister(find), HttpStatus.OK);
    }

    @Operation(summary = "Get all sale values in database", description = "This method is only allowed for debug")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "All Sales Registers", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = OperationData.class))))
    @GetMapping(value = "api/v1/sale/all", produces = "application/json")
    public ResponseEntity<OperationData<?>> getAllSale() throws Exception {
        return new ResponseEntity<>(saleService.findAllRegister(), HttpStatus.OK);
    }

}
