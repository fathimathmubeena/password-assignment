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
        return userRepository.checkUserExistsById(userId) != 0;
    }

    public final boolean validateUsername(Long mobile) {
        String mobileNumber = mobile.toString();
        if (mobileNumber.length() != 10) {
            throw new InvalidException(ResultInfoConstants.INVALID_MOBILE);
        }
        return userRepository.checkUserExistsByUsername(mobile) != 0;
    }

    public final boolean validatePassword(Long pin) {
        String mpin = pin.toString();
        return mpin.length() == 4;
    }
}
