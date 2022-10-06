package me.snsservice.common.jwt.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;
import me.snsservice.common.jwt.JwtTokenProvider;
import me.snsservice.common.jwt.anotation.LoginMember;
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
public class LoginMemberResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return Optional.ofNullable(request.getHeader(JwtFilter.AUTHORIZATION_HEADER))
                .map(authorization -> authorization.split("Bearer")[1])
                .map(jwtTokenProvider::getAuthentication)
                .map(authentication -> authentication.getName())
                .map(email -> memberRepository.findByEmail(email)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER)))
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));
    }
}
