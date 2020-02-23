package org.zty.ztyspringboothr.bean;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/2/13 14:23
 */
public class RespPageBean {
    //页数
    private Long total;

    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
