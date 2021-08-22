package org.zty.ztyspringboothr.controller.system.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zty.ztyspringboothr.bean.Position;
import org.zty.ztyspringboothr.bean.RespBean;
import org.zty.ztyspringboothr.service.PositionService;
import java.util.List;


/**
 * @author 赵天野
 * @date 2020/1/14 14:35
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    PositionService positionService;
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if(positionService.addPosition(position)==1){
            return RespBean.ok("添加成功");
        }
            return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updatePositions(@RequestBody Position position){
        if(positionService.updatePositions(position)==1){
            return RespBean.ok("更新成功");
        }
            return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id){
        if(positionService.deletePositionById(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids){
        if(positionService.deletePositionByIds(ids)==ids.length){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
