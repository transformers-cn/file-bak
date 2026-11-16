package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;
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
    public Result<LoginDTO> searchPost(@RequestBody User user) {
        return Result.ok(loginService.login(user.getLogin_id(),user.getLogin_password()));
    }
}
