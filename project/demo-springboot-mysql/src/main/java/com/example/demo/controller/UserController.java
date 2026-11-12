package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.dto.UserDeptDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户 CRUD 示例接口，演示 MyBatis-Plus 常用 API。
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** 新增：POST /api/users */
    @PostMapping
    public Result<Boolean> save(@RequestBody User user) {
        return Result.ok(userService.save(user));
    }

    /** 按 id 删除：DELETE /api/users/1 */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.ok(userService.removeById(id));
    }

    /** 更新：PUT /api/users */
    @PutMapping
    public Result<Boolean> update(@RequestBody User user) {
        return Result.ok(userService.updateById(user));
    }

    /** 按 id 查询：GET /api/users/1 */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        return Result.ok(userService.getById(id));
    }

    /** 查询全部：GET /api/users */
    @GetMapping
    public Result<List<User>> list() {
        return Result.ok(userService.list());
    }

    /** 条件查询：GET /api/users/search?name=张&age=20 */
    @GetMapping("/search")
    public Result<List<User>> search(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) Integer age) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.isEmpty(), User::getName, name)
               .eq(age != null, User::getAge, age);
        return Result.ok(userService.list(wrapper));
    }

    /** 分页查询：GET /api/users/page?pageNum=1&pageSize=10 */
    @GetMapping("/page")
    public Result<Page<User>> page(@RequestParam(defaultValue = "1") long pageNum,
                                   @RequestParam(defaultValue = "10") long pageSize) {
        return Result.ok(userService.page(new Page<>(pageNum, pageSize)));
    }

    @PostMapping("/search-post")
    public Result<List<User>> searchPost(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(user != null && user.getAge() != null, User::getAge, user.getAge());
        return Result.ok(userService.list(wrapper));
    }

    @PostMapping("/search-post-user-info")
    public Result<List<UserDeptDTO>> searchPostUserInfo(@RequestBody User user) {
        return Result.ok(userService.listUsers(user));
    }
}
