package com.iam.shoutz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue
    private Long postId;

    @NotNull(message = "Message cannot be null")
    @Size(min = 2, max = 140, message = "Message must be between 2 characters and 140 characters")
    private String message;

    @CreatedDate
    private LocalDate createdOn;

    @LastModifiedDate
    private LocalDate modifiedOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
