package stocks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomCSVParser {


  public void writeTOCSV(List<String[]> dataLines,String fileName) throws IOException {
    String path = fileName+".csv";
    File csvOutputFile = new File(path);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      dataLines.stream()
              .map(this::convertToCSV)
              .forEach(pw::println);
    }
  }



  public  String convertToCSV(String[] data) {
    return Stream.of(data)
            .collect(Collectors.joining(","));
  }
}
