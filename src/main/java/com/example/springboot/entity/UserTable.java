package com.example.springboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;


@Entity
@Table(name = "user_tbl")
@Data
@NoArgsConstructor
public class UserTable {
    @Id
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

//    public User toUser() {
//        return new User(this.mobile,this.mpin);
//    }
}
