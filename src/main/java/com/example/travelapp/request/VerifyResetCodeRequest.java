package com.example.travelapp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyResetCodeRequest {
    private String resetCode;
    private String phoneNumber;
}
