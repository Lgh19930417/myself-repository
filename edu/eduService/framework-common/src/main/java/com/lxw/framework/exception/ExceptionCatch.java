package com.lxw.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.lxw.framework.model.response.CommonCode;
import com.lxw.framework.model.response.ResponseResult;
import com.lxw.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

//@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    private static ImmutableMap<Class<? extends Throwable>, ResultCode> exceptionMap;
    private static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder= ImmutableMap.builder();

    static {
      builder.put(IOException.class,CommonCode.ILLARGS);
    }
    //捕获自定义异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult catchCustomException(CustomException e){
        log.error(e.getMessage());
        return  new ResponseResult(e.getResultCode());
    }

    //捕获已知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult catchKnownException(Exception e){
        log.error(e.getMessage());
        if(exceptionMap==null){
           exceptionMap=builder.build();
        }
        ResultCode resultCode = exceptionMap.get(e.getClass());
         if(resultCode!=null){
             log.error(e.getMessage());
             return new ResponseResult(resultCode);
         }
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}
