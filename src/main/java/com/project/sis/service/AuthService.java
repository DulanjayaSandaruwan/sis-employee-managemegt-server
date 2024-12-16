package com.project.sis.service;

import com.project.sis.dto.AuthRequest;
import com.project.sis.dto.AuthResponse;
import com.project.sis.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
}
