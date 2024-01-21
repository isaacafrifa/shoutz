package com.iam.shoutz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String profilePictureUrl;

    @Max(value = 99, message = "Age should not be greater than 99")
    @Min(value = 18, message = "Age should be greater than 18")
    private int age;

    @CreatedDate
    private LocalDate createdOn;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

}
