package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.request.LoginRequest;
import org.sopt.auth.dto.response.TokenResponse;
import org.sopt.auth.service.AuthService;
import org.sopt.global.annotation.BusinessExceptionDescription;
import org.sopt.global.config.swagger.SwaggerResponseDescription;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "로그인" , description = "로그인하는 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary="로그인", description = "로그인하여 토큰을 받아옵니다.")
    @PostMapping("/login")
    @BusinessExceptionDescription(SwaggerResponseDescription.REQUEST_LOGIN)
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

}
