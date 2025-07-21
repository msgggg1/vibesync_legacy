package com.vibesync.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 이 어노테이션이 붙은 컨트롤러에만
 * 로그인한 사용자에게 필요한 공통 Advice를 적용합니다.
 */
@Target(ElementType.TYPE) // 클래스, 인터페이스에 붙일 수 있도록 설정
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUserPages {
}
