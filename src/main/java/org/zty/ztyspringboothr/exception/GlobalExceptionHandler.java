//package org.zty.ztyspringboothr.exception;
//
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.zty.ztyspringboothr.bean.RespBean;
//
//import java.sql.SQLException;
//import java.sql.SQLIntegrityConstraintViolationException;
//
///**
// * @author 赵天野
// * @date 2020/1/20 9:20
// */
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(SQLException.class)
//    public RespBean sqlException(SQLException e){
//        if(e instanceof SQLIntegrityConstraintViolationException){
//            return RespBean.error("该数据不可删除，因为它有关联数据");
//        }
//        return RespBean.error("数据库异常，操作失败");
//    }
//}
