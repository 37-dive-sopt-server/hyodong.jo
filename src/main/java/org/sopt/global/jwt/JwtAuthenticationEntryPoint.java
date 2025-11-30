package org.sopt.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.global.exception.errorcode.ErrorCode;
import org.sopt.global.exception.errorcode.GlobalErrorCode;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 401에러
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // ErrorCode 가져오기
        Object exceptionAttribute = request.getAttribute("exception");
        ErrorCode errorCode;

        if (exceptionAttribute instanceof ErrorCode) {
            errorCode = (ErrorCode) exceptionAttribute;
        } else {
            // 기본 에러 코드
            errorCode = GlobalErrorCode.INVALID_TOKEN;
        }

        setResponse(response, errorCode);
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());

        ApiResponse<Void> apiResponse = ApiResponse.fail(
                errorCode.getCode(),
                errorCode.getMessage()
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}