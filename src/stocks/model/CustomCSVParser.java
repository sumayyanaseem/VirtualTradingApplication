package stocks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomCSVParser {

  public List<List<String>> readFromCSV(String portfolioName)  {
    String path = portfolioName+".csv";
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = br.readLine();
      String[] headings =line.split(",");
      records.add(Arrays.asList(headings));
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
      }
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return records;
  }

  public void writeTOCSV(List<String[]> recordLines,String fileName) {
    String path = fileName+".csv";
    File csvOutputFile = new File(path);
    try {
      if (csvOutputFile.createNewFile()) {
        FileWriter fileWriter = new FileWriter(csvOutputFile);
        for (String[] record : recordLines) {
          String s = convertToCSV(record);

          fileWriter.write(s);
          fileWriter.write(System.getProperty("line.separator"));
        }
        fileWriter.flush();
        fileWriter.close();
      }
    } catch(IOException e){
       throw new IllegalArgumentException("");
    }
  }


  public  String convertToCSV(String[] data) {
    return Stream.of(data)
            .collect(Collectors.joining(","));
  }
}
