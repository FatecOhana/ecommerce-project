package com.ecommerce.services.interfaces;

import com.ecommerce.dto.OperationData;
import com.ecommerce.dto.command.DeleteItemCommand;
import com.ecommerce.dto.command.FindItemByParameterCommand;
import com.ecommerce.dto.command.UpsertItemCommand;

import java.util.UUID;

public interface UniqueRegisterOperationsTemplateV2<T> {

    /**
     * CREATE one object (if object not exists) or UPDATE the existing object
     */
    OperationData<?> upsertRegister(UpsertItemCommand<T> command) throws Exception;

    /**
     * Sets true in tag isDeleted in specify object of table
     */
    OperationData<UUID> softDeleteRegister(DeleteItemCommand command) throws Exception;

    /**
     * Update one register in Database
     */
    OperationData<?> updateRegister(UpsertItemCommand<T> command) throws Exception;

    /**
     * Create one Register in Database
     */
    OperationData<?> createRegister(UpsertItemCommand<T> command) throws Exception;

    /**
     * Find Matches Register in Database
     */
    OperationData<?> findRegister(FindItemByParameterCommand find) throws Exception;

    /**
     * Find all Register in Database
     */
    OperationData<?> findAllRegister() throws Exception;

}
