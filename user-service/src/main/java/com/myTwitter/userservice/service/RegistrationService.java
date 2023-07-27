package com.myTwitter.userservice.service;

import com.myTwitter.userservice.dto.request.RegistrationRequest;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface RegistrationService {

    String registration(RegistrationRequest request, BindingResult bindingResult);





    Map<String, Object> endRegistration(String email, String password, BindingResult bindingResult);
}
