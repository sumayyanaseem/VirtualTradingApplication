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

  public List<List<String>> readFromCSV(String portfolioName)  {
    String path = portfolioName+".csv";
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = br.readLine();
      String[] headings =line.split(",");
      records.add(Arrays.asList(headings));
     /* for(int i=0;i<headings.length;i++) {
        System.out.print(headings[i] + " ");
      }
      System.out.print("\n");*/
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
      }
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

   /* for(int i=0;i<records.size();i++){

      for(int j=0;j<records.get(i).size();j++){
        System.out.print(records.get(i).get(j));
        int len = records.get(0).get(j).length();
        for(int k=0;k<len;k++) {
          System.out.print(" ");
        }
      }
      System.out.println("");
    }*/

    return records;
  }
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
