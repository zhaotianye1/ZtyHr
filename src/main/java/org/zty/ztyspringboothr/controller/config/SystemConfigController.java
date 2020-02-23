package org.zty.ztyspringboothr.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zty.ztyspringboothr.bean.Menu;
import org.zty.ztyspringboothr.service.MenuService;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/1/4 10:00
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenuByHrId(){
        return menuService.getMenuByHrId();
    }
}
