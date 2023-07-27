package com.myTwitter.userservice.service.impl;


import com.myTwitter.userservice.Model.User;
import com.myTwitter.userservice.amqp.AmqpProducer;
import com.myTwitter.userservice.dto.request.RegistrationRequest;
import com.myTwitter.userservice.repository.UserRepository;
import com.myTwitter.userservice.repository.projection.AuthUserProjection;
import com.myTwitter.userservice.repository.projection.UserCommonProjection;
import com.myTwitter.userservice.service.RegistrationService;
import com.myTwitter.userservice.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.exception.ApiRequestException;
import myTwitter.commons.security.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static myTwitter.commons.constants.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AmqpProducer amqpProducer;

    @Override
    @Transactional
    public String registration(RegistrationRequest request, BindingResult bindingResult) {
        userServiceHelper.processInputErrors(bindingResult);
        Optional<User> existingUser = userRepository.getUserByEmail(request.getEmail(), User.class);

        if (existingUser.isEmpty()) {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setFullName(request.getUsername());
            user.setBirthday(request.getBirthday());
            userRepository.save(user);
            return "User data checked.";
        }
        if (!existingUser.get().isActive()) {
            existingUser.get().setUsername(request.getUsername());
            existingUser.get().setFullName(request.getUsername());
            existingUser.get().setBirthday(request.getBirthday());
            userRepository.save(existingUser.get());
            return "User data checked.";
        }
        throw new ApiRequestException(EMAIL_HAS_ALREADY_BEEN_TAKEN, HttpStatus.FORBIDDEN);
    }





    @Override
    @Transactional
    public Map<String, Object> endRegistration(String email, String password, BindingResult bindingResult) {
        userServiceHelper.processInputErrors(bindingResult);
        if (password.length() < 8) {
            throw new ApiRequestException(PASSWORD_LENGTH_ERROR, HttpStatus.BAD_REQUEST);
        }
        AuthUserProjection user = userRepository.getUserByEmail(email, AuthUserProjection.class)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        userRepository.updatePassword(passwordEncoder.encode(password), user.getId());
        userRepository.updateActiveUserProfile(user.getId());
        String token = jwtProvider.createToken(email, "USER");
        return Map.of("user", user, "token", token);
    }
}
