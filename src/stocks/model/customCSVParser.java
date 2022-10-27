package stocks.model;

import org.xml.sax.SAXException;

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

public class customCSVParser {

  public void readFromCSV(String path)  {
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = br.readLine();
      String[] headings =line.split(",");
      System.out.println(headings[0]+" "+headings[1]);
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
      }
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for(int i=0;i<records.size();i++){

      for(int j=0;j<records.get(i).size();j++){
        System.out.print(records.get(i).get(j));
        System.out.print(" ");
      }
      System.out.println(" ");
    }
  }

  public void writeTOCSV(List<List<String>> records) throws IOException {
    List<String[]> dataLines = new ArrayList<>();
    dataLines.add(new String[]{"CountryName","Quantity"});
    for(int i=0;i<records.size();i++)
      dataLines.add(new String[]{records.get(i).toString()});
    File csvOutputFile = new File("p1.csv");
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
