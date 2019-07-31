package YIDA.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Measurement {
    /**
     * 每组实验要进行的次数
     * @return
     */
    int iterations();

    /**
     * 进行实验次数的组数
     * @return
     */
    int group();
}
