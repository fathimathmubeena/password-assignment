package com.example.springboot.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "otp_tbl")
@Data
@RequiredArgsConstructor
public class OneTimePasswordTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long password;
    @Column(name = "user_id")
    private Long userId;
    ;

    public OneTimePasswordTable(Long userId) {
        this.userId = userId;
    }
}
