package com.ecommerce.services;

import com.ecommerce.database.models.complement.Skill;
import com.ecommerce.database.repositories.complement.SkillRepository;
import com.ecommerce.dto.OperationData;
import com.ecommerce.dto.command.DeleteItemCommand;
import com.ecommerce.dto.command.FindItemByParameterCommand;
import com.ecommerce.dto.command.UpsertItemCommand;
import com.ecommerce.dto.exceptions.NotFoundException;
import com.ecommerce.services.interfaces.UniqueRegisterOperationsTemplateV2;
import com.ecommerce.utils.UtilsValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class SkillServiceV2 implements UniqueRegisterOperationsTemplateV2<Skill> {

    private static final Logger logger = LoggerFactory.getLogger(SkillServiceV2.class);
    private final SkillRepository skillRepository;

    public SkillServiceV2(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }


    @Override
    public OperationData<?> upsertRegister(UpsertItemCommand<Skill> command) throws Exception {
        logger.info("Upsert Register...");
        if (UtilsValidation.isNull(command) || UtilsValidation.isNull(command.getData())) {
            throw new NotFoundException("Skill Payload can't be null");
        }

        Skill data = command.getData().getData();
        Skill skillToSave = data;

        // Get the Skill in the database (if exists) and copy its values to the received uniqueSkill (value)
        if (!UtilsValidation.isNull(data.getId())) {
            Skill existentSkill = skillRepository.findById(data.getId()).orElse(null);
            if (!UtilsValidation.isNull(existentSkill)) {
                BeanUtils.copyProperties(data, existentSkill);
                skillToSave = existentSkill;
            }
        }

        Skill newSkill = skillRepository.save(skillToSave);

        logger.info("Finished Upsert Register...");
        return new OperationData<>(newSkill);
    }

    @Override
    public OperationData<UUID> softDeleteRegister(DeleteItemCommand command) throws Exception {
        logger.info("Soft Delete Register...");
        if (UtilsValidation.isNull(command) || UtilsValidation.isNull(command.getId())) {
            throw new NotFoundException("Skill's id can't be null");
        }

        Skill skill = skillRepository.findByIdAndIsDeletedIs(command.getId(), Boolean.FALSE).orElse(null);
        if (UtilsValidation.isNull(skill)) {
            throw new NotFoundException(String.format("not found Skill with id=[%s] and isDeleted=[%s]", command.getId(), false));
        }

        skill.setIsDeleted(Boolean.TRUE);
        skillRepository.save(skill);

        if (skillRepository.findByIdAndIsDeletedIsFalse(skill.getId()).isPresent()) {
            throw new NotFoundException(String.format(
                    "Skill: id=[%s], uniqueKey=[%s] not configured as delete in database",
                    skill.getId(), skill.getIdentifierName())
            );
        }

        logger.info("Finished Soft Delete Register...");
        return new OperationData<>(skill.getId());
    }

    @Override
    public OperationData<?> updateRegister(UpsertItemCommand<Skill> command) throws Exception {
        logger.info("Update Register...");
        return this.upsertRegister(command);
    }

    @Override
    public OperationData<?> createRegister(UpsertItemCommand<Skill> command) throws Exception {
        logger.info("Insert Register...");
        return this.upsertRegister(command);
    }

    @Override
    public OperationData<?> findRegister(FindItemByParameterCommand find) throws Exception {
        logger.info("Get Register...");

        Boolean isDeleted = find.getIsDeleted();
        UUID id = find.getId();

        Skill skillModel = null;
        if (!UtilsValidation.isNull(id)) {
            skillModel = skillRepository.findByIdAndIsDeletedIs(id, isDeleted).orElseThrow(() -> new NotFoundException(
                    String.format("not found skill with id=[%s] and isDeleted=[%s]", id, isDeleted)
            ));
        } else if (!UtilsValidation.isNull(find.getUniqueKey())) {
            skillModel = skillRepository.findByIdentifierNameAndIsDeleted(find.getUniqueKey(), isDeleted)
                    .orElseThrow(() -> new NotFoundException(String.format(
                            "not found skill with identifierName=[%s] and isDeleted=[%s]", find.getUniqueKey(), isDeleted)
            ));
        }

        List<Skill> values = new ArrayList<>();
        if (!UtilsValidation.isNull(find.getName())) {
            values = skillRepository.findByTitleAndIsDeleted(find.getName(), isDeleted);
        }

        if (!UtilsValidation.isNull(skillModel)) {
            values.add(skillModel);
        } else if (UtilsValidation.isNullOrEmpty(values)) {
            throw new NotFoundException(String.format(
                    "not found values in database to combination id=[%s], name=[%s], identifierName=[%s], isDeleted=[%s]",
                    id, find.getName(), find.getUniqueKey(), isDeleted
            ));
        }

        logger.info("Finished Get Register...");
        return new OperationData<>(new HashSet<>(values), null);
    }

    @Override
    public OperationData<?> findAllRegister() throws Exception {
        return new OperationData<>(new HashSet<>(skillRepository.findAll()), null);
    }
}
