package org.zty.ztyspringboothr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zty.ztyspringboothr.bean.Nation;
import org.zty.ztyspringboothr.mapper.NationMapper;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/2/17 13:10
 */
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
