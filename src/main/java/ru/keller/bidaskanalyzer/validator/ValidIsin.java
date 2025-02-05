package ru.keller.bidaskanalyzer.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsinValidator.class) // Указываем класс-валидатор
@Target({ ElementType.FIELD }) // Применимо к полям класса
@Retention(RetentionPolicy.RUNTIME) // Аннотация доступна во время выполнения
public @interface ValidIsin {
    String message() default "Некорректный ISIN. Должно быть 12 символов, только буквы и цифры";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
