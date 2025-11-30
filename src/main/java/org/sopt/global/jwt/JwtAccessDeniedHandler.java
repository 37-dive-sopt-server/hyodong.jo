package org.sopt.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.global.exception.errorcode.ErrorCode;
import org.sopt.global.exception.errorcode.GlobalErrorCode;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 403 에러
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    // 자바 <-> JSON 객체
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        setResponse(response);
    }

    private void setResponse(HttpServletResponse response) throws IOException {

        ErrorCode errorCode = GlobalErrorCode.FORBIDDEN_ACCESS;

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