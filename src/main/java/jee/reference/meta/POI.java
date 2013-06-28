package jee.reference.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface POI {
    POITag[] tags() default {};

    String value() default "";
}
