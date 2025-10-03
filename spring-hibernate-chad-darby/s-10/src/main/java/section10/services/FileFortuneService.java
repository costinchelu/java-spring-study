package section10.services;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Component
public class FileFortuneService implements FortuneService {

    private List<String> fortunesFromFile = new ArrayList<>();

    public FileFortuneService() {
        try {
            File myFile = new File("src\\main\\resources\\fortunes.txt");
            Scanner myReader = new Scanner(myFile);
            while(myReader.hasNextLine()) {
                fortunesFromFile.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFortune() {
        Random random = new Random();
        return fortunesFromFile.get(random.nextInt(fortunesFromFile.size()));
    }
}
