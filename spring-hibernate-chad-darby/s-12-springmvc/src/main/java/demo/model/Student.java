package demo.model;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Scanner;


public class Student {

    private String firstName;
    private String lastName;
    private String country;
    private LinkedHashMap<String, String> countryOptions;
    private String favouriteLanguage;
    private String[] operatingSystems;

    public Student() {
        countryOptions = new LinkedHashMap<>();

        try {
            URL location = this.getClass().getClassLoader().getResource("text\\countries.csv");
            assert location != null;
            File countriesFile = new File(location.toURI());
            Scanner myReader = new Scanner(countriesFile);
            while(myReader.hasNextLine()) {
                String[] keyValue = myReader.nextLine().split(";");
                countryOptions.put(keyValue[0], keyValue[1]);
            }
            myReader.close();
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LinkedHashMap<String, String> getCountryOptions() {
        return countryOptions;
    }

    public String getFavouriteLanguage() {
        return favouriteLanguage;
    }

    public void setFavouriteLanguage(String favouriteLanguage) {
        this.favouriteLanguage = favouriteLanguage;
    }

    public String[] getOperatingSystems() {
        return operatingSystems;
    }

    public void setOperatingSystems(String[] operatingSystems) {
        this.operatingSystems = operatingSystems;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
