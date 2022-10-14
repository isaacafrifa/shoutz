package com.iam.shoutz.entity;

import com.iam.shoutz.model.ValidAge;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
/*
Let's start with a bare-minimum user entity
 */
//@Entity
//@Table
//@EntityListeners(AuditingEntityListener.class) //allow usage of @CreatedDate and @LastModifiedDate
@Setter
@Getter
@AllArgsConstructor
@ToString
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 5, message = "Username must be more than 5 letters")
    private String username;
    @Size(min = 2, message = "First name must be more than 2 letters")
    private String firstName;
    @Size(min = 2, message = "Surname must be more than 2 letters")
    private String lastName;
    @Past(message = "Enter valid date of birth")
    @ValidAge
    private LocalDate dateOfBirth;
//    @CreatedDate
    private LocalDate createdOn;

}
