package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.annotation.AuthOnField;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j//로그를 찍기 위해 필요한 어노테이션
@Aspect
public class LogAspect {

    @AuthOnField AuthUser authUser;

    // 포인트컷들...(포인트컷=어드바이스(횡단관심사)를 적용할 구체적인 범위를 선택하는 규칙
    // 내가 적용해야 할 곳(과제)=domain.comment.controller와 domain.user.controller에 있는 각각의 어드민 컨트롤러

    /*@Pointcut("execution(* org.example.expert.domain.user.controller..*(..))")
    private void atCommentAdminController(){}*/
    // 위 방법은 강의에서 나온 패키지 기반 지정 방식. 지금 과제랑은 안 맞는 것 같음...

    // 안 맞는 게 아니고 같은 방식으로 클래스 이름까지 적으면 클래스 기반이 되는 것이었음.
    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    public void commentAdminController() {}

    // 어드바이스들...

    // 메서드 실행 전에 수행되는 로직을 처리할 때 사용
    /*@Before("commentAdminController()")// 위에서 만든 포인트컷 메서드 이름을 넣으면 됨
    public void before() {}*/

    // 메서드의 정상수행, 예외발생에 관계없이 항상 실행
    @After("commentAdminController()")
    public void afterMethod(){
        // 로그 기록 요구사항
        // 요청한 사용자의 id
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest =attributes.getRequest();
        String asdf = httpServletRequest.getHeader("Authorization");
        String = JwtUtil.substringToken(asdf);

        log.info("요청한 사용자 id : ", );
        // api 요청 시각
        // api 요청 url
    }
}
