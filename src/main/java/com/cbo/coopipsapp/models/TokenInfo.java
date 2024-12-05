package com.cbo.coopipsapp.models;

import lombok.Data;

@Data

public class TokenInfo {

    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private Long refresh_expires_in;

}
