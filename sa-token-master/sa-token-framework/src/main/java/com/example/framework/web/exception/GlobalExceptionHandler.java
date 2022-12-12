package com.example.framework.web.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.example.common.exception.ServiceException;
import com.example.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author jibl
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public R NotLoginException(NotLoginException e) {

        // 打印堆栈，以供调试
        e.printStackTrace();

        // 判断场景值，定制化异常信息
        String message;
        if (e.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        } else if (e.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        } else if (e.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        } else if (e.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        } else if (e.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        } else {
            message = "当前会话未登录";
        }

        /**
         * 可以返回状态码 前台用拦截器拦截提示！ 401
         */
        return R.fail(401, message);
    }


    /**
     * 角色异常
     */
    @ExceptionHandler(NotRoleException.class)
    public String NotRoleException(NotRoleException e) {
        return "角色异常....";
    }

    /**
     * 此权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public String NotPermissionException(NotPermissionException e) {
        return "此权限异常....";
    }


    /**
     * service异常
     */
    @ExceptionHandler(ServiceException.class)
    public R ServiceException(ServiceException e) {
        return R.fail(500, e.getMessage());
    }
}