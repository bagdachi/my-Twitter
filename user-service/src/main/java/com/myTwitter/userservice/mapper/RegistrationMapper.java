package com.myTwitter.userservice.mapper;


import com.myTwitter.userservice.dto.request.EndRegistrationRequest;
import com.myTwitter.userservice.dto.request.RegistrationRequest;
import com.myTwitter.userservice.dto.response.AuthenticationResponse;
import com.myTwitter.userservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class RegistrationMapper {

    private final RegistrationService registrationService;
    private final AuthenticationMapper authenticationMapper;

    public String registration(RegistrationRequest request, BindingResult bindingResult) {
        return registrationService.registration(request, bindingResult);
    }



    public AuthenticationResponse endRegistration(EndRegistrationRequest request, BindingResult bindingResult) {
        return authenticationMapper.getAuthenticationResponse(
                registrationService.endRegistration(request.getEmail(), request.getPassword(), bindingResult));
    }
}
