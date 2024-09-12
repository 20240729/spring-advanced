package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.annotation.AuthOnField;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
        boolean hasAuthOnFieldAnnotation = parameter.getParameterAnnotation(AuthOnField.class) != null;
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        // 아무것도 안 달렸을 때 예외 발생
        if (!hasAuthAnnotation == isAuthUserType && !hasAuthOnFieldAnnotation == isAuthUserType) {
            throw new AuthException("@Auth 또는 @AuthOnField 어노테이션은 AuthUser 타입에만 사용 가능합니다.");
        }

        //이하 주석은 원본 코드
        // @Auth 어노테이션과 AuthUser 타입이 함께 사용되지 않은 경우 예외 발생
        /*if (hasAuthAnnotation != isAuthUserType) {
            throw new AuthException("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
        }*/

        return hasAuthAnnotation || hasAuthOnFieldAnnotation;
    }
    // 이하 주석은 참고용으로 사용한 chatGPT 코드.
    //@Override
    //public boolean supportsParameter(MethodParameter parameter) {
    //    boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
    //    boolean hasAuthOnFieldAnnotation = parameter.getParameterAnnotation(AuthOnField.class) != null;
    //    boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);
    //
    //    // @Auth 또는 @AuthOnField 애노테이션이 적용된 필드가 AuthUser 타입일 때 예외 발생
    //    if ((hasAuthAnnotation || hasAuthOnFieldAnnotation) && !isAuthUserType) {
    //        throw new AuthException("@Auth 또는 @AuthOnField 는 AuthUser 타입에서만 사용될 수 있습니다.");
    //    }
    //
    //    return hasAuthAnnotation || hasAuthOnFieldAnnotation;
    //}

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // JwtFilter 에서 set 한 userId, email, userRole 값을 가져옴
        Long userId = (Long) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        UserRole userRole = UserRole.of((String) request.getAttribute("userRole"));

        return new AuthUser(userId, email, userRole);
    }
}
