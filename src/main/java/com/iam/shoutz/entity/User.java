package com.iam.shoutz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iam.shoutz.model.ValidAge;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
/*
Let's start with a bare-minimum user entity
 */
@Entity
@Table(name = "user_details")
@EntityListeners(AuditingEntityListener.class) //allow usage of @CreatedDate and @LastModifiedDate
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties("id")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Username must not be null")
    @Size(min = 5, message = "Username must be more than 5 letters")
    private String username;

    @NotNull(message = "First name must not be null")
    @Size(min = 2, message = "First name must be more than 2 letters")
    private String firstName;

    @NotNull(message = "Surname must not be null")
    @Size(min = 2, message = "Surname must be more than 2 letters")
    private String lastName;

    @Past(message = "Enter valid date of birth")
    @ValidAge
    private LocalDate dateOfBirth;
    @CreatedDate
    private LocalDate createdOn;

}
