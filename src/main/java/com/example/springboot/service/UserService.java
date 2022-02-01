package com.example.springboot.service;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.entity.OldUser;
import com.example.springboot.entity.OneTimePassword;
import com.example.springboot.entity.UserTable;
import com.example.springboot.exception.DuplicateKeyException;
import com.example.springboot.exception.InvalidException;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final OneTimePassword oneTimePassword;


    public Long create(@Valid OldUser user) {
        Long mobileNumber = user.getUsername();
        Long mPin = user.getPassword();
        String mobile = mobileNumber.toString();
        String mpin = mPin.toString();
        if (mobile.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (allMobiles.contains(mobileNumber)) {
            throw new DuplicateKeyException(ResultInfoConstants.DUPLICATE_NUMBER);
        }
        if (mpin.length() != 4) {
            throw new InvalidException(ResultInfoConstants.INVALID_MPIN);
        }
        Long otp = generateOtp(mobileNumber);
        userRepository.save(user.toUserTable()).getUsername();
        return otp;
    }

    public Long generateOtp(Long mobileNumber) {
        String mobile = mobileNumber.toString();
        if (mobile.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobileNumber)) {
            throw new InvalidException(ResultInfoConstants.NUMBER_DOES_NOT_EXIST);
        }
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
            throw new InvalidException(ResultInfoConstants.INVALID_OTP);
        }
        return oneTimePassword.getMobile();
    }


    public Long login(@Valid OldUser user) {
        Long mobileNumber = user.getUsername();
        Long mPin = user.getPassword();
        String mobile = mobileNumber.toString();
        if (mobile.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobileNumber)) {
            throw new InvalidException(ResultInfoConstants.NUMBER_DOES_NOT_EXIST);
        }
        Long mpin = userRepository.getMpin(mobileNumber);
        if (!mpin.equals(mPin)) {
            throw new InvalidException(ResultInfoConstants.INVALID_MPIN);
        }
        UserTable userTable = userRepository.getUser(mobileNumber);
        userRepository.save(userTable);
        return mobileNumber;
    }

    public Long signout(Long mobile) {
        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobile)) {
            throw new InvalidException(ResultInfoConstants.USER_NOT_FOUND);
        }
        UserTable userTable = userRepository.getUser(mobile);
        userRepository.save(userTable);
        return mobile;
    }

//    public Long delete(Long mobile) {
//        String mobileNumber = mobile.toString();
//        if (mobileNumber.length() != 10) {
//            throw new InvalidNumberException(ResultInfoConstants.INVALID_MOBILE);
//        }
//        List<Long> allMobiles = userRepository.getAllMobileNumbers();
//        if (!allMobiles.contains(mobile)) {
//            throw new InvalidNumberException(ResultInfoConstants.USER_NOT_FOUND);
//        }
//       userRepository.deleteAccount(mobile);
//        return mobile;
//    }

    public Long forgotPassword(Long mobile) {
        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobile)) {
            throw new InvalidException(ResultInfoConstants.USER_NOT_FOUND);
        }
        Long otp = generateOtp(mobile);
        UserTable userTable = userRepository.getUser(mobile);
        userRepository.save(userTable);
        return otp;
    }

    public Long changePassword(OldUser user) {
        Long mobile = user.getUsername();
        Long newPin = user.getPassword();

        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        List<Long> allMobiles = userRepository.getAllMobileNumbers();
        if (!allMobiles.contains(mobile)) {
            throw new InvalidException(ResultInfoConstants.USER_NOT_FOUND);
        }
        String mpin = newPin.toString();
        if (mpin.length() != 4) {
            throw new InvalidException(ResultInfoConstants.INVALID_MPIN);
        }
        UserTable oldUserTable = userRepository.getUser(mobile);

        oldUserTable.setPassword(newPin);
        userRepository.save(oldUserTable);
        return mobile;
    }
}
