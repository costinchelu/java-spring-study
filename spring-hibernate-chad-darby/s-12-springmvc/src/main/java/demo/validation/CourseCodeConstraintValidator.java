package demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {

    private String coursePrefix;

    @Override
    public void initialize(CourseCode constraintAnnotation) {
        coursePrefix = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String userEnteredValue, ConstraintValidatorContext context) {

        // userEnteredValue = the actual userEnteredValue that the user entered in the html form
        // context = additional parameter which we can use to place additional error messages (if we needed)
        // business rules:
        // we need that user will enter a code that starts with "LUV"
        // (which is actually our default value (from the interface))

        if (userEnteredValue != null) {
            return userEnteredValue.startsWith(coursePrefix);
        } else {
            return false;
        }
    }
}
