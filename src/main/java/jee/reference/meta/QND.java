package jee.reference.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Quick and dirty
@Retention(RetentionPolicy.SOURCE)
public @interface QND {
    String value() default "";
}
