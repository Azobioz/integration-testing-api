package com.azobioz.api.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Data
public class TokenStoreService {

    private String accessToken;

    public void clear() {
        accessToken = null;
    }
}
