package com.lapin.di.annotation;


import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ClassMeta {
    String name();

    String description() default "description is empty";
}
