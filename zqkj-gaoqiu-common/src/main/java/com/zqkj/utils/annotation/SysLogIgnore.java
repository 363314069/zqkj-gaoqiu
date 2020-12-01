package com.zqkj.utils.annotation;

import java.lang.annotation.*;

/**
 * 忽略
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogIgnore {

}
