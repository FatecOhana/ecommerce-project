package com.ecommerce.services.interfaces;

import com.ecommerce.dto.OperationData;
import com.ecommerce.dto.command.DeleteItemsCommand;
import com.ecommerce.dto.command.FindItemsByParametersCommand;
import com.ecommerce.dto.command.UpsertItemsCommand;

import java.util.UUID;

public interface ManyRegisterOperationsTemplateV2<T> extends UniqueRegisterOperationsTemplateV2<T> {

    /**
     * CREATE many object (if object not exists) or UPDATE the existing objects
     */
    OperationData<?> upsertRegisters(UpsertItemsCommand<T> command) throws Exception;

    /**
     * Sets true in tag isDeleted in many object of table
     */
    OperationData<UUID> softDeleteRegisters(DeleteItemsCommand command) throws Exception;

    /**
     * Find Matches Register in Database
     */
    OperationData<?> findManyMatchRegisters(FindItemsByParametersCommand find) throws Exception;

}
