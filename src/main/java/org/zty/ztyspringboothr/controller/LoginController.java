package org.zty.ztyspringboothr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zty.ztyspringboothr.bean.RespBean;

/**
 * @author 赵天野
 * @date 2019/12/30 10:53
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登陆请登录!");
    }
}
