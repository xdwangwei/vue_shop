package com.vivi.vue.shop.exception;

import com.vivi.vue.shop.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangwei
 * 2021/2/8 21:58
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public R BizExceptionHandler(BizException e) {
        log.error("BizException: ", e);
        return R.error(e.getErrCode(), e.getErrMsg());
    }



    /**
     * 参数不满足校验规则
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public R bindExceptionHandler(Exception e) {
        log.warn("参数校验失败：", e);
        List<FieldError> fieldErrors;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        } else {
            fieldErrors = ((BindException)e).getFieldErrors();
        }
        String message = fieldErrors.stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.joining(";"));
        return R.error(BizCodeEnum.BAD_REQUEST.getErrCode(), message);
    }

    /**
     * 所传参数类型与接收类型不一致导致转换封装失败
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException: ", e);
        return R.error(BizCodeEnum.BAD_REQUEST.getErrCode(), "参数不合法");
    }

    /**
     * 404
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R notFoundHandler(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException: ", e);
        return R.error(BizCodeEnum.NOT_FOUND.getErrCode(), e.getRequestURL());
    }

    /**
     * 运行期异常，内部异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: ", e);
        if (e instanceof HttpMessageNotReadableException) {
            // 以@RequestBody接收json形式请求体，请求却未传请求体
           return R.create(BizCodeEnum.BAD_REQUEST.getErrCode(), "请求体不能为空");
        }

        return R.error(BizCodeEnum.INTERNAL_SERVER_ERROR.getErrCode(), BizCodeEnum.INTERNAL_SERVER_ERROR.getErrMsg());
    }


    /**
     * 其他异常，比如必要请求参数未传
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        log.error("Exception: ", e);
        return R.error(BizCodeEnum.BAD_REQUEST.getErrCode(), e.getMessage());
    }

}
