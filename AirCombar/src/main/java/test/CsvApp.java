package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvApp {

    public static void main(String[] args) {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]
                { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
        dataLines.add(new String[]
                { "Jane", "Doe, Jr.", "19", "She said \"I'm being quoted\"" });
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    /*public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {
        String strFilePath = "C:/Users/User/IdeaProjects/MAI/air-combat-master/AirCombar/result2.txt";
        File csvOutputFile = new File(strFilePath);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
    }
*/
}
