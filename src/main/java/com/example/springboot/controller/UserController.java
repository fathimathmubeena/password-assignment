package com.example.springboot.controller;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.controller.response.ResponseWrapper;
import com.example.springboot.entity.JWTResponse;
import com.example.springboot.entity.User;
import com.example.springboot.exception.InvalidException;
import com.example.springboot.service.CustomUserDetailsService;
import com.example.springboot.service.UserService;
import com.example.springboot.utility.JWTUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final CustomUserDetailsService customUserDetailsService;

    private final JWTUtility jwtUtility;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestHeader Long username, @RequestHeader Long password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(

                            username,
                            password
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidException(ResultInfoConstants.BAD_CREDENTIAL);
        }

        final UserDetails userDetails
                = customUserDetailsService.loadUserByUsername(username.toString());
        final String token = jwtUtility.generateToken(userDetails);
        return new JWTResponse(token);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> create(@RequestHeader Long username, @RequestHeader Long password) {
        log.info("Received request to craete account :{},{}", username, password);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.create(username, password));
    }


    @RequestMapping(value = "/otp/{otp}/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> validOtp(@PathVariable @NotNull Long otp, @PathVariable @NotNull Long id) {
        log.info("Received request to enter otp :{}", otp);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.validOtp(otp, id));
    }

    @GetMapping("/generate/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> generateOtp(@PathVariable @NotNull Long userId) {
        log.info("Received request to generate otp for username: {}", userId);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.generateOtp(userId));
    }


    @GetMapping("/forgot_password/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<User> forgotPassword(@PathVariable @NotNull Long userId) {
        log.info("Received request to reset password for username number :{}", userId);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.forgotPassword(userId));
    }

    @PutMapping("/change_password")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public ResponseWrapper<Long> changePassword(@RequestBody @Valid User user) {
        log.info("Received request to change password for username number :{}", user.getUsername());
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.changePassword(user));
    }
}
