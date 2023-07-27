package com.myTwitter.userservice.service;


import com.myTwitter.userservice.Model.User;
import com.myTwitter.userservice.dto.request.AuthenticationRequest;
import com.myTwitter.userservice.repository.projection.AuthUserProjection;
import com.myTwitter.userservice.repository.projection.UserPrincipalProjection;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface AuthenticationService {

    Long getAuthenticatedUserId();

    User getAuthenticatedUser();

    UserPrincipalProjection getUserPrincipalByEmail(String email);

    Map<String, Object> login(AuthenticationRequest request, BindingResult bindingResult);

    Map<String, Object> getUserByToken();

    String getExistingEmail(String email, BindingResult bindingResult);

    String sendPasswordResetCode(String email, BindingResult bindingResult);

    AuthUserProjection getUserByPasswordResetCode(String code);

    String passwordReset(String email, String password, String password2, BindingResult bindingResult);

    String currentPasswordReset(String currentPassword, String password, String password2, BindingResult bindingResult);
}
