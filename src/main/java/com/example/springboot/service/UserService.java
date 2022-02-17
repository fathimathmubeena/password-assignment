package com.example.springboot.service;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.entity.OneTimePasswordTable;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserTable;
import com.example.springboot.entity.Validator;
import com.example.springboot.exception.InvalidException;
import com.example.springboot.exception.NotFoundException;
import com.example.springboot.repository.OtpRepository;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    private final Validator validator;

    private final OtpRepository otpRepository;

    public Long create(Long username, Long password) {


        if (validator.validateUsername(username)) {
            throw new InvalidException(ResultInfoConstants.DUPLICATE_NUMBER);

        }
        if (!validator.validatePassword(password)) {
            throw new InvalidException(ResultInfoConstants.INVALID_MPIN);
        }
        UserTable userTable = new UserTable(username, password);

        userRepository.save(userTable).getUsername();
        Long userId = userTable.getId();
        Long otp = generateOtp(userId);
        return otp;
    }

    public long getOtp(Long userId) {
        return ThreadLocalRandom.current().nextLong(1000, 1000000);
    }

    public Long generateOtp(Long userId) {
        if (!validator.validateId(userId)) {
            throw new NotFoundException(ResultInfoConstants.USER_NOT_FOUND);
        }
        Long otp = getOtp(userId);
        log.info("The Otp generated is: {}", otp);
        Optional<OneTimePasswordTable> optionalOneTimePasswordTable = otpRepository.findByUserId(userId);
        if (!optionalOneTimePasswordTable.isPresent()) {
            OneTimePasswordTable oneTimePasswordTable = new OneTimePasswordTable(userId);
            oneTimePasswordTable.setPassword(otp);
            otpRepository.save(oneTimePasswordTable);
            return otp;
        }
        OneTimePasswordTable oneTimePasswordTable = optionalOneTimePasswordTable.get();
        oneTimePasswordTable.setPassword(otp);
        otpRepository.save(oneTimePasswordTable);
        return otp;
    }

    public Long validOtp(Long otp, Long userId) {
        Optional<OneTimePasswordTable> optionalOneTimePasswordTable = otpRepository.findByUserId(userId);
        Long generatedOtp = optionalOneTimePasswordTable.get().getPassword();
        Long enteredOtp = otp;
        if (!generatedOtp.equals(enteredOtp)) {
            throw new InvalidException(ResultInfoConstants.INVALID_OTP);
        }
        return userId;
    }


    public User forgotPassword(Long userId) {
        if (!validator.validateId(userId)) {
            throw new NotFoundException(ResultInfoConstants.USER_NOT_FOUND);
        }
        UserTable userTable = userRepository.getUserById(userId);
        Long id = userTable.getId();
        Long otp = generateOtp(id);

        return userTable.toUser();
    }

    public Long changePassword(User user) {
        Long mobile = user.getUsername();
        Long newPin = user.getPassword();

        Long userId = user.getId();

        if (!validator.validatePassword(newPin)) {
            throw new InvalidException(ResultInfoConstants.INVALID_MPIN);
        }
        Long id = userRepository.getIdByUsername(mobile);
        userRepository.changePasswordById(newPin, id);


        return id;
    }
}
