package com.example.springboot.entity;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.exception.InvalidException;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final UserRepository userRepository;

    public final boolean validateId(Long userId) {
        int count = userRepository.checkUserExistsById(userId);
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public final boolean validateMobile(Long mobile) {
        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        int count = userRepository.checkUserExistsByUsername(mobile);
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public final boolean validateMpin(Long pin) {
        String mpin = pin.toString();
        if (mpin.length() != 4) {
            return false;
        } else {
            return true;
        }
    }
}
