package com.myTwitter.userservice.Controller.rest;

import com.myTwitter.userservice.dto.request.EndRegistrationRequest;
import com.myTwitter.userservice.dto.request.ProcessEmailRequest;
import com.myTwitter.userservice.dto.request.RegistrationRequest;
import com.myTwitter.userservice.dto.response.AuthenticationResponse;
import com.myTwitter.userservice.mapper.RegistrationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static myTwitter.commons.constants.PathConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_AUTH)
public class RegistrationController {

    private final RegistrationMapper registrationMapper;

    @PostMapping(REGISTRATION_CHECK)
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(registrationMapper.registration(request, bindingResult));
    }



    @PostMapping(REGISTRATION_CONFIRM)
    public ResponseEntity<AuthenticationResponse> endRegistration(@Valid @RequestBody EndRegistrationRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(registrationMapper.endRegistration(request, bindingResult));
    }
}
