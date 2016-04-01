package com.minitwit.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * controller parameter not empty valid
 * 
 * @author chenchao
 * @date 2016年3月23日 上午11:27:22
 * @version 1.0
 * @parameter
 * @since
 * @return
 * @throws
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RPNotEmpty {

}
