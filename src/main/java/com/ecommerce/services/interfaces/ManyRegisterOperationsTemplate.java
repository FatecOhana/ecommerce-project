package com.ecommerce.services.interfaces;

import com.ecommerce.dto.OperationData;

import java.util.Set;
import java.util.UUID;

public interface ManyRegisterOperationsTemplate<T> extends UniqueRegisterOperationsTemplate<T> {

    /**
     * CREATE many object (if object not exists) or UPDATE the existing objects
     */
    OperationData<?> upsertRegisters(Set<T> value) throws Exception;

    /**
     * Sets true in tag isDeleted in many object of table
     */
    OperationData<UUID> softDeleteRegisters(Set<UUID> value) throws Exception;

    /**
     * Find Matches Register in Database
     */
    OperationData<?> findManyMatchRegisters(Set<UUID> id, Set<String> name, Set<String> uniqueKey, Object type,
                                            Boolean isDeleted) throws Exception;

}
