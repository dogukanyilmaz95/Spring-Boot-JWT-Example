package com.dogukanyilmaz.authenticationauthorization.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dogukanyilmaz
 */
@RestController
@RequestMapping("api")
public class MockController {

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok().body("HELLO ADMIN!!");
    }

    @GetMapping("/jwt")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<?> helloJwt() {
        return ResponseEntity.ok().body("HELLO USER!!");
    }


}
