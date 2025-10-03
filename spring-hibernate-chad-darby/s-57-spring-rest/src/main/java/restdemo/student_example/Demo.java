package restdemo.student_example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Demo {

    public static void main(String[] args) {

        try {
            // create object mapper
            ObjectMapper mapper = new ObjectMapper();
            // read JSON file and map to Java POJO
            Student aStudent = mapper.readValue(new File("data/sample-full.json"), Student.class);
            // print name
            System.out.println("Name of the student: " + aStudent.getFirstName() + " " + aStudent.getLastName());
            System.out.println("Street and zip code: " + aStudent.getAddress().getCity() + " " + aStudent.getAddress().getZip());
            for (String language : aStudent.getLanguages()) {
                System.out.println(" -> " + language);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
