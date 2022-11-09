package stocks.model;

import java.util.List;

public interface CustomParserInterface {
  List<List<String>> readFromFile(String portfolioName);
  List<List<String>> readFromPathProvidedByUser(String path);
  void writeTOFile(List<String[]> recordLines, String fileName);
  String convertToFile(String[] data);
}
