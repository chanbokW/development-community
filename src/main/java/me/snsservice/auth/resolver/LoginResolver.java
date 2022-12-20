package me.snsservice.auth.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.auth.domain.CustomUserDetails;
import me.snsservice.auth.controller.Login;
import me.snsservice.auth.controller.LoginMember;
import me.snsservice.auth.service.JwtTokenProvider;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.common.exception.ErrorCode;
import me.snsservice.config.security.JwtFilter;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    // TODO Exception 처리
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return Optional.ofNullable(request.getHeader(JwtFilter.AUTHORIZATION_HEADER))
                .map(authorization -> authorization.split("Bearer")[1])
                .map(it -> jwtTokenProvider.getAuthentication(it))
                .map(authentication -> {
                    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
                    return new LoginMember(principal.getId(), principal.getEmail());
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));
    }
}
