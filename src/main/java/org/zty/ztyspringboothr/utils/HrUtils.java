package org.zty.ztyspringboothr.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zty.ztyspringboothr.bean.Hr;

/**
 * @author 赵天野
 * @date 2020/2/7 15:37
 */
public class HrUtils {
    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
