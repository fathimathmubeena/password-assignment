package com.example.springboot.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "user_tbl")
@Data
@RequiredArgsConstructor
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long username;
    private Long password;
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public UserTable(Long username, Long password) {
        this.username = username;
        this.password = password;
    }

    public User toUser() {
        return new User(this.id, this.username, this.password);
    }

}
