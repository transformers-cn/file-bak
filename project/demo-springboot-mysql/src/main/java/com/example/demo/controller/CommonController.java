package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.CodeMst;
import com.example.demo.service.CodeMstService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Common API。
 */
@RestController
@RequestMapping("/api/common")
public class CommonController {

    private final CodeMstService codeMstService;

    public CommonController(CodeMstService codeMstService) {
        this.codeMstService = codeMstService;
    }

    @GetMapping ("/codes/intake-time")
    public Result<List<CodeMst>> searchCodeMstAll() {
        LambdaQueryWrapper<CodeMst> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CodeMst::getCodeTypeId, "001");

        return Result.ok(codeMstService.list(queryWrapper));
    }

    @GetMapping ("/codes/search-by-Id")
    public Result<List<CodeMst>> searchCodeMstById(String codeTypeId) {
        LambdaQueryWrapper<CodeMst> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CodeMst::getCodeTypeId, codeTypeId);
        return Result.ok(codeMstService.list(queryWrapper));
    }
}
