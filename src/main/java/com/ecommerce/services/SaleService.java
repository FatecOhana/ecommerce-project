package com.ecommerce.services;

import com.ecommerce.database.models.entities.Sale;
import com.ecommerce.database.models.entities.SaleItem;
import com.ecommerce.database.repositories.entities.SaleRepository;
import com.ecommerce.dto.OperationData;
import com.ecommerce.dto.SaleDTO;
import com.ecommerce.dto.command.DeleteItemCommand;
import com.ecommerce.dto.command.FindItemByParameterCommand;
import com.ecommerce.dto.command.UpsertItemCommand;
import com.ecommerce.dto.exceptions.NotFoundException;
import com.ecommerce.services.interfaces.UniqueRegisterOperationsTemplateV2;
import com.ecommerce.utils.UtilsValidation;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SaleService implements UniqueRegisterOperationsTemplateV2<Sale> {

    private static final Logger logger = LoggerFactory.getLogger(SaleService.class);
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public OperationData<?> upsertRegisterV2(UpsertItemCommand<SaleDTO> command) throws Exception {
        logger.info("Upsert Register...");
        if (UtilsValidation.isNull(command) || UtilsValidation.isNull(command.getData())) {
            throw new NotFoundException("SaleItem Payload can't be null");
        }

        final SaleDTO saleDTO = command.getData().getData();

        AtomicReference<Float> totalValue = new AtomicReference<>(0.0F);
        saleDTO.getSaleItems().forEach(saleItem -> {
            float rawValue = saleItem.getUnityPrice() * saleItem.getAmount();
            saleItem.setTotal(rawValue - (rawValue * saleItem.getDiscount()));
            totalValue.updateAndGet(value -> value + saleItem.getTotal());
        });

        logger.info(String.format("total sale: [%s], sum sale items: [%s]", totalValue.get(),
                saleDTO.getSaleItems().stream().mapToDouble(SaleItem::getTotal)));
        saleDTO.setTotal(totalValue.get());

        Sale saleToSave = new Sale();
        BeanUtils.copyProperties(saleDTO, saleToSave);
        Sale newSaleItem = saleRepository.save(saleToSave);

        logger.info("Finished Upsert Register...");
        return new OperationData<>(newSaleItem);
    }

    @Override
    public OperationData<?> upsertRegister(UpsertItemCommand<Sale> command) throws Exception {
        logger.info("Upsert Register...");
        if (UtilsValidation.isNull(command) || UtilsValidation.isNull(command.getData())) {
            throw new NotFoundException("Sale Payload can't be null");
        }

        Sale data = command.getData().getData();
        Sale saleToSave = data;

        // Get the uniqueSale in the database (if exists) and copy its values to the received uniqueSale (value)
        if (!UtilsValidation.isNull(data.getId())) {
            Sale existentSale = saleRepository.findById(data.getId()).orElse(null);
            if (!UtilsValidation.isNull(existentSale)) {
                BeanUtils.copyProperties(data, existentSale);
                saleToSave = existentSale;
            }
        }

        Sale newSale = saleRepository.save(saleToSave);

        logger.info("Finished Upsert Register...");
        return new OperationData<>(newSale);
    }

    @Override
    public OperationData<UUID> softDeleteRegister(DeleteItemCommand command) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public OperationData<?> updateRegister(UpsertItemCommand<Sale> command) throws Exception {
        logger.info("Update Register...");
        return this.upsertRegister(command);
    }

    @Override
    public OperationData<?> createRegister(UpsertItemCommand<Sale> command) throws Exception {
        logger.info("Insert Register...");
        return this.upsertRegister(command);
    }

    @Override
    public OperationData<?> findRegister(FindItemByParameterCommand find) throws Exception {
        logger.info("Get Register...");

        throw new NotImplementedException();

        /*
        logger.info("Finished Get Register...");
        return new OperationData<>(new HashSet<>(), null);
        */
    }

    @Override
    public OperationData<?> findAllRegister() throws Exception {
        logger.info("Get All Register...");
        return new OperationData<>(new HashSet<>(saleRepository.findAll()), null);
    }
}
