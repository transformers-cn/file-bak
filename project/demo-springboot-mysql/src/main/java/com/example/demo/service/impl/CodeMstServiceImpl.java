package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.CodeMst;
import com.example.demo.entity.User;
import com.example.demo.mapper.CodeMstMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.CodeMstService;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeMstServiceImpl extends ServiceImpl<CodeMstMapper, CodeMst> implements CodeMstService {

    @Autowired
    private CodeMstMapper codeMstMapper;

}
