package org.omidbiz.core.axon.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CustomSerializer {
    
    /**
     * must be type of axonserailizer interface
     * @return
     */
    Class<?> interceptor() default void.class;
    
}
