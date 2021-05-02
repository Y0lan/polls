package fr.esgi.polls.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/*
    @AuthenticationPrincipal : allow access to the currently authenticated user in the controller

    CurrentUser :  a meta-annotation so that we don’t get too much tied up of with
                   Spring Security related annotations everywhere in our project
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
