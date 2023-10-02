package com.ecommerce.database.models.entities;

import com.ecommerce.database.models.complement.Address;
import com.ecommerce.database.models.complement.Skill;
import com.ecommerce.dto.exceptions.NotFoundException;
import com.ecommerce.dto.types.ContractType;
import com.ecommerce.dto.types.DayPeriod;
import com.ecommerce.dto.types.WorkModel;
import com.ecommerce.utils.UtilsValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TB_JOB")
public class Job {

    // Unique Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Schema(description = "defines if a job is deleted. This tag allows you to retrieve possible excluded cases", defaultValue = "false")
    private Boolean isDeleted = Boolean.FALSE;


    // Job Description
    @Schema(description = "title of job", example = "Decola 2022 -> Dev Frontend React Junior")
    @Column(nullable = false)
    private String title;

    @Schema(description = "resumed name of job", example = "NEST-FRONTEND_JR_2022")
    @Column(nullable = false, unique = true)
    private String identifierName;

    @Schema(description = "general description to company, job and chalanges",
            example = "a company that develops innovation projects, focusing on the vision of a competitive job market")
    @Column(nullable = false)
    private String description;

    @Schema(description = "number of peoples to be hired", defaultValue = "1", example = "5")
    @Column(nullable = false)
    private Integer peopleToHired = 1;

    // Work Specifications
    @Schema(description = "period of working", example = "TARDE")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayPeriod periodJob;

    @Schema(description = "model of working", example = "HOME OFFICE")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkModel workModel;

    @Schema(description = "type of job contract", example = "CLT")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractType contractType;


    @Schema(description = "weekly working hours", example = "40.0")
    @Column(nullable = false)
    private Double weekHours;

    @Schema(description = "number of working days", example = "5")
    @Column(nullable = false)
    private Double daysInWeek;


    // Job offers
    @Schema(description = "sets the minimum wage", example = "1250.00")
    @Column(precision = 2)
    private Double startSalaryRange;

    @Schema(description = "sets the max wage", example = "3000.00")
    @Column(nullable = false)
    private Double limitSalaryRange;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User enterprise;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, targetEntity = Address.class)
    @JoinColumn(name = "ADDRESS_ID", nullable = false, referencedColumnName = "ID")
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    private Set<User> candidatesToJob = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    private Set<User> chosenStudents = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Skill.class)
    private Set<Skill> requiredSkills = new HashSet<>();

//    private Set<Benefits> benefits;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Job that = (Job) obj;
        return id.equals(that.id);
    }

    public void addCandidate(User user) {
        if (UtilsValidation.isNull(user)) return;

        Set<User> candidatesSet = this.getCandidatesToJob();
        if (!UtilsValidation.isNullOrEmpty(candidatesSet)) {
            candidatesSet.add(user);
        } else {
            candidatesSet = new HashSet<>(List.of(user));
        }

        this.setCandidatesToJob(candidatesSet);
    }

    public void addChosenStudent(User user) throws NotFoundException {
        if (UtilsValidation.isNull(user)) return;

        boolean userIsCandidated = UtilsValidation.ifNullOrEmpty(this.getCandidatesToJob(), new HashSet<>())
                .stream().anyMatch(v -> Objects.equals(v.getId(), user.getId()));

        if (!userIsCandidated) {
            throw new NotFoundException(String.format(
                    "the user isn't a candidate in Job. Try to apply for it and then choose it." +
                            "\nid=[%s], name=[%s], username=[%s], isDeleted=[%s]", user.getId(), user.getName(),
                    user.getIdentifierName(), user.getIsDeleted()));
        }

        Set<User> students = this.getChosenStudents();
        if (!UtilsValidation.isNullOrEmpty(students)) {
            students.add(user);
        } else {
            students = new HashSet<>(List.of(user));
        }

        this.setChosenStudents(students);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
