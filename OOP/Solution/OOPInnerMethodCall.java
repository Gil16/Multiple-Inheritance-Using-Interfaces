package OOP.Solution;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface OOPInnerMethodCall {
    Class<?> caller();
    Class<?> callee();
    String methodName();
    Class<?>[] argTypes() default {};
}