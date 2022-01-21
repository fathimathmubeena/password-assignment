package com.example.springboot.controller;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.controller.response.ResponseWrapper;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> create(@RequestBody @Valid User user) {
        log.info("Received request to craete account :{}", user);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.create(user));
    }

    @GetMapping("/otp/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> validOtp(@PathVariable @NotNull Long id) {
        log.info("Received request to enter otp :{}", id);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.validOtp(id));
    }

    @GetMapping("/generate/{mobile}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> generateOtp(@PathVariable @NotNull Long mobile) {
        log.info("Received request to generate otp for mobile: {}", mobile);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.generateOtp(mobile));
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> login(@RequestBody @Valid User user) {
        log.info("Received request to login account :{}", user);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.login(user));
    }

    @DeleteMapping("/signout/{mobile}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<String> signout(@PathVariable @NotNull Long mobile) {
        log.info("Received request to remove the user account of mobile number: {}", mobile);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.signout(mobile));
    }


    @GetMapping("/forgot/{mobile}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> forgotPassword(@PathVariable @NotNull Long mobile) {
        log.info("Received request to reset password for mobile number :{}", mobile);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.forgotPassword(mobile));
    }

    @PutMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> changePassword(@RequestBody @Valid User user) {
        log.info("Received request to change password for mobile number :{}", user.getMobile());
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.changePassword(user));
    }
}
