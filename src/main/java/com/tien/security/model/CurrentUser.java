package com.tien.security.model;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
//@AuthenticationPrincipal là annotation của Spring Security để lấy User đang login.
//@CurrentUser chỉ là 1 "wrapper" giúp bạn inject tự động UserPrincipal vào Controller
public @interface CurrentUser {
}
