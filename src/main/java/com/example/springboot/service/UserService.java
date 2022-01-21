package com.example.springboot.service;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.entity.OneTimePassword;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserTable;
import com.example.springboot.exception.DuplicateKeyException;
import com.example.springboot.exception.InvalidNumberException;
import com.example.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OneTimePassword oneTimePassword;

    public Long create(User user) {
        Long mobileNumber = user.getMobile();
        Long mPin = user.getMpin();
        String mobile = mobileNumber.toString();
        String mpin = mPin.toString();
        if (mobile.length() != 10) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (allMobiles.contains(mobileNumber)) {
            throw new DuplicateKeyException(ResultInfoConstants.DUPLICATE_NUMBER);
        }
        if (mpin.length() != 4) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MPIN);
        }
        Long otp = generateOtp(mobileNumber);
        userRepository.save(user.toUserTable()).getMobile();
        return otp;
    }

    public Long generateOtp(Long mobileNumber) {
        Long otp = ThreadLocalRandom.current().nextLong(1000, 1000000);
        log.info("The Otp generated is: {}", otp);
        oneTimePassword.setPassword(otp);
        oneTimePassword.setMobile(mobileNumber);
        return otp;
    }

    public Long validOtp(Long otp) {
        Long generatedOtp = oneTimePassword.getPassword();
        Long enteredOtp = otp;
        if (!generatedOtp.equals(enteredOtp)) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_OTP);
        }
        return oneTimePassword.getMobile();
    }


    public Long login(User user) {
        Long mobileNumber = user.getMobile();
        Long mPin = user.getMpin();
        String mobile = mobileNumber.toString();
        if (mobile.length() != 10) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobileNumber)) {
            throw new InvalidNumberException(ResultInfoConstants.NUMBER_DOES_NOT_EXIST);
        }
        Long mpin = userRepository.getMpin(mobileNumber);
        if (!mpin.equals(mPin)) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MPIN);
        }
        return mobileNumber;
    }

    public Long signout(Long mobile) {
        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobile)) {
            throw new InvalidNumberException(ResultInfoConstants.USER_NOT_FOUND);
        }
        userRepository.deleteById(mobile);
        return mobile;
    }

    public Long forgotPassword(Long mobile) {
        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobile)) {
            throw new InvalidNumberException(ResultInfoConstants.USER_NOT_FOUND);
        }
        Long otp = generateOtp(mobile);
        return otp;
    }

    public Long changePassword(User user) {
        Long mobile = user.getMobile();
        Long newPin = user.getMpin();

        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobile)) {
            throw new InvalidNumberException(ResultInfoConstants.USER_NOT_FOUND);
        }
        String mpin = newPin.toString();
        if (mpin.length() != 4) {
            throw new InvalidNumberException(ResultInfoConstants.INVALID_MPIN);
        }
        UserTable oldUserTable = userRepository.getUser(mobile);

        oldUserTable.setMpin(newPin);
        userRepository.save(oldUserTable);
        return mobile;
    }
}
