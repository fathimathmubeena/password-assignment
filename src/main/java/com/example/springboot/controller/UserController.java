package com.example.springboot.controller;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.controller.response.ResponseWrapper;
import com.example.springboot.entity.JWTResponse;
import com.example.springboot.entity.OldUser;
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
    public JWTResponse authenticate(@RequestBody OldUser user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credential", e);
        }

        final UserDetails userDetails
                = customUserDetailsService.loadUserByUsername(user.getUsername().toString());
        final String token = jwtUtility.generateToken(userDetails);
        return new JWTResponse(token);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> create(@RequestBody @Valid OldUser user) {
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

    @GetMapping("/generate/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> generateOtp(@PathVariable @NotNull Long username) {
        log.info("Received request to generate otp for username: {}", username);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.generateOtp(username));
    }

    @PutMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> login(@RequestBody @Valid OldUser user) {
        log.info("Received request to login account :{}", user);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.login(user));
    }

    @PutMapping("/signout/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<String> signout(@PathVariable @NotNull Long username) {
        log.info("Received request to remove the user account of username number: {}", username);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.signout(username));
    }

//    @DeleteMapping ("/delete/{username}")
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResponseWrapper<String> delete(@PathVariable @NotNull Long username) {
//        log.info("Received request to remove the account of username : {}", username);
//        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.delete(username));
//    }


    @GetMapping("/forgot/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> forgotPassword(@PathVariable @NotNull Long username) {
        log.info("Received request to reset password for username number :{}", username);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.forgotPassword(username));
    }

    @PutMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> changePassword(@RequestBody @Valid OldUser user) {
        log.info("Received request to change password for username number :{}", user.getUsername());
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.changePassword(user));
    }
}
