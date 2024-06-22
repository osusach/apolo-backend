package osusach.apolo.auth.services;

import osusach.apolo.auth.models.AuthResponse;
import osusach.apolo.auth.models.LoginRequest;
import osusach.apolo.auth.models.RegisterRequest;

public interface AuthService {

    public AuthResponse register(RegisterRequest registerRequest);

    public AuthResponse login(LoginRequest loginRequest);

}
