package com.iam.shoutz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
    private String username;
    private String firstName;
    private String lastName;
//    @CreatedDate
    private LocalDate createdOn;

}
