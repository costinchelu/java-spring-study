package demo.model;

import demo.validation.CourseCode;

import javax.validation.constraints.*;

public class Customer {

    // fields with validators (conditions and error messages)

    @NotNull(message = "Required field")
    @Size(min = 1, message = "Require at least a character")
    private String firstName;

    @NotNull(message = "Required field")
    @Size(min = 1, message = "Require at least a character")
    private String lastName;

    // we use a wrapper class to benefit from the WebDataBinder in controller which will only work with classes
    @NotNull(message = "Required field")
    @Min(value = 0, message = "Must be 0 or maximum 10")
    @Max(value = 10, message = "Must be 0 or maximum 10")
    private Integer freePasses;

    //@NotNull(message = "Please enter your postal code")
    @Pattern(regexp = "^[a-zA-Z0-9]{6}", message = "Postal code is made of 6 literal or numeric characters")
    private String postalCode;

    // using the default values for our custom annotation
    @CourseCode(value = "CCAR", message = "Code must start with CCAR")
    @Pattern(regexp = "^[a-zA-Z0-9]{6}", message = "Code must have 6 characters")
    private String courseCode;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getFreePasses() {
        return freePasses;
    }

    public void setFreePasses(Integer freePasses) {
        this.freePasses = freePasses;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

