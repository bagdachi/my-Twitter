package com.myTwitter.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class SearchTermsRequest {
    private List<Long> users;
}
