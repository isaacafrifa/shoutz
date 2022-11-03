package com.iam.shoutz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iam.shoutz.util.ValidAge;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_details")
@EntityListeners(AuditingEntityListener.class) //allow usage of @CreatedDate and @LastModifiedDate
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties("userId")
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 5, message = "Username must be more than 5 letters")
    private String username;

    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, message = "First name must be more than 2 letters")
    private String firstName;

    @NotBlank(message = "Surname cannot be empty")
    @Size(min = 2, message = "Surname must be more than 2 letters")
    private String lastName;

    @NotNull
    @Past(message = "Enter valid date of birth")
    @ValidAge
    private LocalDate dateOfBirth;

    @CreatedDate
    private LocalDate createdOn;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

}
