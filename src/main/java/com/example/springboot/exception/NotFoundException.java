package com.example.springboot.exception;

import com.example.springboot.controller.response.ResultInfo;
import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private final ResultInfo result;
}
