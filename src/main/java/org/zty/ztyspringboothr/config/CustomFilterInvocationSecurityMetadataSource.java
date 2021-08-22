<<<<<<< HEAD
package org.zty.ztyspringboothr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.zty.ztyspringboothr.bean.Menu;
import org.zty.ztyspringboothr.bean.Role;
import org.zty.ztyspringboothr.service.MenuService;

import java.util.Collection;
import java.util.List;

/**
 * @author 赵天野
 * @date 2020/1/13 9:25
 *
 * 根据用户传来的地址，分析出请求需要的角色
 */
@Configuration
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getAllMenusWithRole();
        for (Menu menu : menus) {
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles=menu.getRoles();
                String str[]=new String[roles.size()];
                for (int i=0;i<roles.size();i++){
                    str[i]=roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
        return SecurityConfig.createList("Role_Login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
=======
package org.zty.ztyspringboothr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.zty.ztyspringboothr.bean.Menu;
import org.zty.ztyspringboothr.bean.Role;
import org.zty.ztyspringboothr.service.MenuService;

import java.util.Collection;
import java.util.List;

/**
 * @author 赵天野
 * @date 2020/1/13 9:25
 *
 * 根据用户传来的地址，分析出请求需要的角色
 */
@Configuration
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getAllMenusWithRole();
        for (Menu menu : menus) {
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles=menu.getRoles();
                String str[]=new String[roles.size()];
                for (int i=0;i<roles.size();i++){
                    str[i]=roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
        return SecurityConfig.createList("Role_Login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
>>>>>>> d183975e40851db894f2de6f5eb0421bd29ad4e4
