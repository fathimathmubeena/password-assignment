package com.example.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class OneTimePassword {
    private Long password;
    private Long mobile;
}
