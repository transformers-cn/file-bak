package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.dto.LoginDTO;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import org.springframework.web.bind.annotation.*;

/**
 * Login API。
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Result<LoginDTO> searchPost(@RequestBody LoginForm loginForm) {
        return Result.ok(loginService.login(loginForm.getUsername(),loginForm.getPassword()));
    }
}
