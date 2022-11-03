package stocks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class CustomCSVParser {

  public List<List<String>> readFromCSV(String portfolioName)  {
    String path = "userPortfolios/"+portfolioName+"_output.csv";
    List<List<String>> records = null;
    try {
      records = readFromFileHelper(path);
    }  catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return records;
  }
  
  private List<List<String>> readFromFileHelper(String path) throws IOException {
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = br.readLine();
      String[] headings =line.split(",");
      records.add(Arrays.asList(headings));
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
      }
    }
    return records;
  }
  
  public List<List<String>> readFromPathProvidedByUser(String path){
    try{
    return readFromFileHelper(path);
    } catch (IOException e) {
      throw new RuntimeException(e);

    }
  }

  public void writeTOCSV(List<String[]> recordLines,String fileName) {
    //String[] splitPath = fileName.split(".",2);
    String[] splitPath = fileName.split("\\.(?=[^\\.]+$)");
    String[] splitFileName = splitPath[0].split("/");
    String path = "userPortfolios/"+splitFileName[splitFileName.length-1]+"_output.csv";
    File csvOutputFile = new File(path);
    try {

        FileWriter fileWriter = new FileWriter(csvOutputFile);
        for (String[] record : recordLines) {
          String s = convertToCSV(record);

          fileWriter.write(s);
          fileWriter.write(System.getProperty("line.separator"));
        }
        fileWriter.flush();
        fileWriter.close();

    } catch(IOException e){
      System.out.println(e.getMessage());
    }
  }

  public  String convertToCSV(String[] data) {
    return Stream.of(data)
            .collect(Collectors.joining(","));
  }
}
