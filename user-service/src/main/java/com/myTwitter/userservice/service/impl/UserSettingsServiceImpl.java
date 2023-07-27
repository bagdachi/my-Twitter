package com.myTwitter.userservice.service.impl;


import com.myTwitter.userservice.repository.UserRepository;
import com.myTwitter.userservice.repository.UserSettingsRepository;
import com.myTwitter.userservice.repository.projection.AuthUserProjection;
import com.myTwitter.userservice.service.AuthenticationService;
import com.myTwitter.userservice.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.exception.ApiRequestException;
import myTwitter.commons.security.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static myTwitter.commons.constants.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public String updateUsername(String username) {
        if (username.length() == 0 || username.length() > 50) {
            throw new ApiRequestException(INCORRECT_USERNAME_LENGTH, HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateUsername(username, authUserId);
        return username;
    }

    @Override
    @Transactional
    public Map<String, Object> updateEmail(String email) {
        if (!userSettingsRepository.isEmailExist(email)) {
            Long authUserId = authenticationService.getAuthenticatedUserId();
            userSettingsRepository.updateEmail(email, authUserId);
            String token = jwtProvider.createToken(email, "USER");
            AuthUserProjection user = userRepository.getUserById(authUserId, AuthUserProjection.class).get();
            return Map.of("user", user, "token", token);
        }
        throw new ApiRequestException(EMAIL_HAS_ALREADY_BEEN_TAKEN, HttpStatus.FORBIDDEN);
    }

    @Override
    @Transactional
    public Map<String, Object> updatePhone(String countryCode, Long phone) {
        int phoneLength = String.valueOf(phone).length();

        if (phoneLength < 6 || phoneLength > 10) {
            throw new ApiRequestException(INVALID_PHONE_NUMBER, HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updatePhone(countryCode, phone, authUserId);
        return Map.of("countryCode", countryCode, "phone", phone);
    }

    @Override
    @Transactional
    public String updateCountry(String country) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateCountry(country, authUserId);
        return country;
    }

    @Override
    @Transactional
    public String updateGender(String gender) {
        if (gender.length() == 0 || gender.length() > 30) {
            throw new ApiRequestException(INVALID_GENDER_LENGTH, HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateGender(gender, authUserId);
        return gender;
    }

    @Override
    @Transactional
    public String updateLanguage(String language) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateLanguage(language, authUserId);
        return language;
    }

    @Override
    @Transactional
    public boolean updateDirectMessageRequests(boolean mutedDirectMessages) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateDirectMessageRequests(mutedDirectMessages, authUserId);
        return mutedDirectMessages;
    }

    @Override
    @Transactional
    public boolean updatePrivateProfile(boolean privateProfile) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updatePrivateProfile(privateProfile, authUserId);
        return privateProfile;
    }


}
